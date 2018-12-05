package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther carter
 * create time    2017-11-15
 * 需要检查联系方式，后处理事件只通知到建站，状态的变化；
 */
@Component
public class CheckLinkInfoAfter extends IFsmHandler<Orders> {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(Orders orders, EventModel event) {
        orderService.publishOrderStateMessage(orders, OrderStateEnum.fromName(event.getDstState()));
        logger.info("订单[id = {}]审核成【待联系】", orders.getId());
    }


}
