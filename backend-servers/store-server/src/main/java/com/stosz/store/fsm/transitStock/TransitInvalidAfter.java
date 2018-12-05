package com.stosz.store.fsm.transitStock;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.TransitStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:ChenShifeng
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class TransitInvalidAfter extends IFsmHandler<TransitStock> {

    @Override
    public void execute(TransitStock stock, EventModel event) {

        logger.debug("报废一个包裹[{}]", stock);


    }

}