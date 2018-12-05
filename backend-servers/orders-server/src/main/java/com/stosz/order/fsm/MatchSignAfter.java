package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * 物流系统通知订单已经签收，修改为签收状态之后， 首先通知建站系统，状态发生了变化，然后，进入财务系统；
 *
 */
@Component
public class MatchSignAfter extends IFsmHandler<Orders> {

    private static final Logger logger = LoggerFactory.getLogger(MatchSignAfter.class);

    @Autowired
    private OrderService orderService;


    @Override
    public void execute(Orders orders, EventModel event) {




        logger.info("订单修改为签收状态,{}",orders.getId());

        //通知到建站系统
        orderService.publishOrderStateMessage(orders, OrderStateEnum.fromName(event.getDstState()));


        //待规划，财务系统生成付款流水，对账单等  todo


    }
}
