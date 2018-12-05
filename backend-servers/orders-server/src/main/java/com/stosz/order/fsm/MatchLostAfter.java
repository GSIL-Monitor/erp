package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.Orders;
import org.springframework.stereotype.Component;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * 这里处理注意 订单是否已经配货，如果配货，需要发wms，否则不做处理
 *
 */
@Component
public class MatchLostAfter extends IFsmHandler<Orders> {

    @Override
    public void execute(Orders orders, EventModel event) {

    }
}
