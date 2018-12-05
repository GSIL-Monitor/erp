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
 * 物流告知状态为拒收，则通知到建站系统，并在售后中心生成拒收单；
 *
 */
@Component
public class MatchNotSignAfter extends IFsmHandler<Orders> {

    private static final Logger logger = LoggerFactory.getLogger(MatchSignAfter.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(Orders orders, EventModel event) {

        logger.info("订单修改为签收状态,{}",orders.getId());

        //通知到建站系统
        orderService.publishOrderStateMessage(orders, OrderStateEnum.fromName(event.getDstState()));
        //todo 生成Rma的拒收单
//        generateRejectBill();

    }
}
