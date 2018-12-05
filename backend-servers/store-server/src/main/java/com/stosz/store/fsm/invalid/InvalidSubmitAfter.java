package com.stosz.store.fsm.invalid;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransitHandleEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.InvalidService;
import com.stosz.store.service.StockServiceImpl;
import com.stosz.store.service.TransitStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class InvalidSubmitAfter extends IFsmHandler<Invalid> {

    @Autowired
    private InvalidService invalidService;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private TransitStockService transitStockService;

    @Resource
    private FsmProxyService<TransitStock> transitFsmProxyService;

    @Override
    public void execute(Invalid invalid, EventModel event) {

        logger.debug("提交报废单[{}]", invalid);

        //1.锁库sku级别
        this.changeStockQty(invalid);
        //2.修改包裹状态
        this.changeTransitState(invalid);
    }

    /**
     * 修改包裹状态
     *
     * @param invalid
     */
    private void changeTransitState(Invalid invalid) {
        List<TransitStock> stockList = invalidService.getTransitListByInvalidId(invalid.getId());
        for (TransitStock stock : stockList) {
            stock.setHistoryState(stock.getState());
            transitStockService.update(stock);
            transitFsmProxyService.processEvent(stock, TransitStockEventEnum.invalid, "报废中");
        }


    }

    private void changeStockQty(Invalid invalid) {
        List<TransitItem> itemList = invalidService.getTransitItemListByInvalidId(invalid.getId());

        List<StockChangeReq> reqList = new ArrayList<>();
        for (TransitItem item : itemList) {
            StockChangeReq req = new StockChangeReq();
            req.setWmsId(invalid.getWmsId());
            req.setDeptId(item.getDeptId());
            req.setDeptName(item.getDeptName());
            req.setSpu(item.getSpu());
            req.setSku(item.getSku());
            req.setChangeQty(item.getQty());
            req.setVoucherNo(invalid.getId().toString());
            req.setAmount(new BigDecimal(0));
            req.setChangeAt(invalid.getCreateAt());
            req.setType(TransitHandleEnum.create_discard.name());
            reqList.add(req);
        }
        stockService.discardChangeStockQty(reqList);
    }
}