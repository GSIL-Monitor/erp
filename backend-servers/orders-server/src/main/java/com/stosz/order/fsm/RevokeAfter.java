package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单撤回
 *
 * @auther wangqian
 * @date 2017-12-11
 */
@Component
public class RevokeAfter extends IFsmHandler<Orders> {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(Orders orders, EventModel event) {
        orderService.publishOrderStateMessage(orders, OrderStateEnum.fromName(event.getDstState()));
        logger.info("订单[id = {}]撤回成功", orders.getId());
    }
}
