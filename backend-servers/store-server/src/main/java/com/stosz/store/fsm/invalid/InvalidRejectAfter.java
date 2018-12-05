package com.stosz.store.fsm.invalid;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.TransitHandleEnum;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.InvalidService;
import com.stosz.store.service.TransitStockService;
import com.stosz.store.service.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/10]
 */
@Component
public class InvalidRejectAfter extends IFsmHandler<Invalid> {

    @Autowired
    private InvalidService invalidService;

    @Autowired
    private StockServiceImpl stockService;

    @Resource
    private TransitStockService transitStockService;

    @Override
    public void execute(Invalid invalid, EventModel event) {

        //2.库存解锁
        this.changeStockQty(invalid);

        //包裹回退状态
        this.changeTransitStateBack(invalid);
    }

    private void changeTransitStateBack(Invalid invalid) {
        List<TransitStock> stockList = invalidService.getTransitListByInvalidId(invalid.getId());
        for (TransitStock stock : stockList) {
            stock.setState(stock.getHistoryState());
            stock.setHistoryState(null);
            transitStockService.update(stock);
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
            req.setType(TransitHandleEnum.discard_apply_fail.name());
            reqList.add(req);
        }
        stockService.discardChangeStockQty(reqList);
    }
}
