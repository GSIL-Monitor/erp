package com.stosz.store.fsm.takeStock;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransitHandleEnum;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.TakeStockService;
import com.stosz.store.service.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class TakeStockPassFinanceAfter extends IFsmHandler<TakeStock> {

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private TakeStockService takeStockService;

    @Autowired
    private IOrderService orderService;

    @Resource
    private FsmProxyService<TransitStock> transitFsmProxyService;

    @Override

    public void execute(TakeStock takeStock, EventModel event) {

        logger.debug("盘点单财务复审[{}]", takeStock);
        UserDto userDto = ThreadLocalUtils.getUser();
        takeStock.setApprover(userDto.getLastName());
        takeStock.setUpdateAt(LocalDateTime.now());
        //1.更新记录
        takeStockService.update(takeStock);

        //2.去库存
        this.changeStockQty(takeStock);

        //3.1.如果包裹状态为“转寄已匹配”、“转寄拣货中”则订单状态要回退为“已审核”，重新配货
        this.changeOrders(takeStock);
    }

    /**
     * 订单状态修改
     *
     * @param takeStock
     */
    private void changeOrders(TakeStock takeStock) {
        List<TransitStock> stockList = takeStockService.getTransitListById(takeStock.getId());
        for (TransitStock stock : stockList) {
            if (TransitStateEnum.matched == TransitStateEnum.fromName(stock.getHistoryState())
                    || TransitStateEnum.picking == TransitStateEnum.fromName(stock.getHistoryState())) {
                try {
                    orderService.transferCancel(stock.getOrderIdOld(), OrderEventEnum.transferCancel);
                } catch (Exception e) {
                    logger.error("盘点单[{}]通过，未知订单[{}]修改结果，请到订单系统核查", takeStock.getId(), stock.getOrderIdOld());
                }

            }

            transitFsmProxyService.processEvent(stock, TransitStockEventEnum.takeStockPass, "盘点审核通过");
        }
    }

    /**
     * 事件	在库数	   有用数	在单数（锁定数）	在途数
     * 3	盘亏（审核通过）-1	0	   -1               	0
     */
    private void changeStockQty(TakeStock takeStock) {
        List<TransitItem> itemList = takeStockService.getTransitItemListByTakeStockId(takeStock.getId());

        List<StockChangeReq> reqList = new ArrayList<>();
        for (TransitItem item : itemList) {
            StockChangeReq req = new StockChangeReq();
            req.setWmsId(takeStock.getWmsId());
            req.setDeptId(item.getDeptId());
            req.setDeptName(item.getDeptName());
            req.setSpu(item.getSpu());
            req.setSku(item.getSku());
            req.setChangeQty(item.getQty());
            req.setVoucherNo(takeStock.getId().toString());
            req.setAmount(BigDecimal.ZERO);
            req.setChangeAt(takeStock.getCreateAt());
            req.setType(TransitHandleEnum.takeStock_apply_pass.name());
            reqList.add(req);
        }
        stockService.takeStockChangeStockQty(reqList);
    }
}