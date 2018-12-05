package com.stosz.store.fsm.takeStock;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransitHandleEnum;
import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.service.TakeStockService;
import com.stosz.store.service.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/10]
 */
@Component
public class TakeStockRejectFirstAfter extends IFsmHandler<TakeStock> {

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private TakeStockService takeStockService;



    @Autowired
    private TakeStockCommonUtils takeStockCommonUtils;

    @Override
    public void execute(TakeStock takeStock, EventModel event) {

        logger.debug("盘点单主管初审驳回[{}]", takeStock);
        UserDto userDto = ThreadLocalUtils.getUser();
        takeStock.setApprover(userDto.getLastName());
        takeStock.setUpdateAt(LocalDateTime.now());
        //1.更新记录
        takeStockService.update(takeStock);

        //2.解锁库存
        /**
         * 	事件	在库数	   有用数	在单数（锁定数）	在途数
         2	盘亏（审核不通过）0	   1	-1	            0

         */
        this.changeStockQty(takeStock);

        //包裹回退状态
        takeStockCommonUtils.changeTransitStateBack(takeStock);
    }

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
            req.setAmount(new BigDecimal(0));
            req.setChangeAt(takeStock.getCreateAt());
            req.setType(TransitHandleEnum.takeStock_apply_fail.name());
            reqList.add(req);
        }
        stockService.takeStockChangeStockQty(reqList);
    }
}
