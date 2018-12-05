package com.stosz.store.fsm.transitStock;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.store.ext.model.TransitStock;
import org.springframework.stereotype.Component;

/**
 * @Author:ChenShifeng
 * @Description: 转寄出库
 * @Created Time:
 * @Update Time:2017/12/13 18:27
 */
@Component
public class TransitOutStockAfter extends IFsmHandler<TransitStock> {

    @Override
    public void execute(TransitStock stock, EventModel event) {

        logger.info("调拨库一个包裹[{}]", stock);

    }

}