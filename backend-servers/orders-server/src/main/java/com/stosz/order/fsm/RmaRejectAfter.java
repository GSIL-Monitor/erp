package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.OrdersRmaBill;
import com.stosz.order.service.OrdersQuestionService;
import com.stosz.order.service.OrdersRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 新增拒收单的时候更新问题件中的状态为拒收
 */
@Component
public class RmaRejectAfter extends IFsmHandler<OrdersRmaBill> {

    @Autowired
    OrdersRefundService ordersRefundService;

    @Autowired
    OrdersQuestionService ordersQuestionService;


    @Override
    public void execute(OrdersRmaBill ordersRmaBill, EventModel event) {
        //TODO 同时更新订单状态，物流发货单状态为拒收。

//        OrdersQuestion ordersQuestion = ordersQuestionService.findByOrdersId(ordersRmaBill.getOrdersId().intValue());


    }

}
