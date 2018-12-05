package com.stosz.store.fsm.takeStock;

import com.stosz.fsm.FsmProxyService;
import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransitHandleEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.*;
import com.stosz.store.service.*;
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
public class TakeStockCreateAfter extends IFsmHandler<TakeStock> {


    @Resource
    private TakeStockService takeStockService;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private TransitStockService transitStockService;

    @Resource
    private FsmProxyService<TransitStock> transitFsmProxyService;

    @Override
    public void execute(TakeStock takeStock, EventModel event) {

        logger.debug("新建盘点单[{}]", takeStock);

        //2.锁库
        /**
         * 	事件	在库数	有用数	在单数（锁定数）	在途数
         盘亏（初审）0	-1	    1	                 0
         */
        this.changeStockQty(takeStock);
        //2.修改包裹状态
        this.changeTransitState(takeStock);

    }

    /**
     * 修改包裹状态
     *
     * @param takeStock
     */
    private void changeTransitState(TakeStock takeStock) {
        List<TransitStock> stockList = takeStockService.getTransitListById(takeStock.getId());
        for (TransitStock stock : stockList) {
            stock.setHistoryState(stock.getState());
            transitStockService.update(stock);
            transitFsmProxyService.processEvent(stock, TransitStockEventEnum.takeStock, TransitStockEventEnum.takeStock.display());
        }

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
            req.setType(TransitHandleEnum.create_takeStock.name());
            reqList.add(req);
        }
        stockService.takeStockChangeStockQty(reqList);
    }
}