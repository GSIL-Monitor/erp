package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 判定为无效订单
 * @auther wangqian
 * @date 2017-11-30
 */
@Component
public class CheckInvalidAfter extends IFsmHandler<Orders> {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(Orders orders, EventModel event) {
       orderService.publishOrderStateMessage(orders, OrderStateEnum.fromName(event.getDstState()));
        logger.info("订单[id = {}]审核成【无效订单】", orders.getId());
    }
}
