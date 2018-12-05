package com.stosz.store.fsm.takeStock;

import com.stosz.store.ext.model.TakeStock;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.TakeStockService;
import com.stosz.store.service.TransitStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description:通用公告方法
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Component
public class TakeStockCommonUtils {


    @Autowired
    private TakeStockService takeStockService;

    @Resource
    private TransitStockService transitStockService;

    /**
     * //包裹回退状态
     *
     * @param takeStock
     */
    public void changeTransitStateBack(TakeStock takeStock) {
        List<TransitStock> stockList = takeStockService.getTransitListById(takeStock.getId());
        for (TransitStock stock : stockList) {
            stock.setState(stock.getHistoryState());
            stock.setHistoryState(null);
            transitStockService.update(stock);
        }
    }


}
