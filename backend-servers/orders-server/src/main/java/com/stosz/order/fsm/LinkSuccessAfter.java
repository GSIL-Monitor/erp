package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @auther carter
 * create time    2017-11-15
 * 从MQ中取出订单信息
 */
@Deprecated
@Component
public class LinkSuccessAfter extends IFsmHandler<Orders> {

    @Autowired
    private OrderService orderService;

    @Override
    public void execute(Orders orders, EventModel event) {
        // TODO: 2017/12/8  审核通过推送到MQ
        if(StringUtils.hasLength(orders.getMemo())){
            orderService.updateOrderMemo(orders.getId(),orders.getMemo());
        }
        logger.info("订单[id = {}]审核【联系成功】", orders.getId());
    }
}
