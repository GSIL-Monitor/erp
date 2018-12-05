package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.order.service.OrderService;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @auther carter
 * create time    2017-11-15
 * 设置订单为待采购状态，然后发送一条消息到采购需求队列中；
 */
@Component
public class LockStockFailAfter extends IFsmHandler<Orders> {

    private static final Logger logger = LoggerFactory.getLogger(LockStockFailAfter.class);

    @Autowired
    private OrderService orderService;


    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Override
    public void execute(Orders orders, EventModel event) {

        List<OrdersItem> orderItemsList = orderService.findOrdersItemByOrderId(orders.getId());

        if(CollectionUtils.isNullOrEmpty(orderItemsList))
        {
            logger.warn("产生采购需求消息警告：订单没有对应的订单项目{}",orders);
        }


        Optional.ofNullable(orderItemsList)
                .ifPresent(orderItems ->
                        orderItems.stream().forEach(item ->
                                {
                                    String sku = item.getSku();
                                    String spu = item.getSpu();
                                    Integer dept = orders.getSeoDepartmentId();
                                    publishBuyRequestMessage(sku, spu, dept);
                                }
                        )
                );


    }


    /**
     * 部门的某个sku缺货消息发送u
     *
     * @param sku
     * @param dept
     */
    private boolean publishBuyRequestMessage(String sku, String spu, Integer dept) {
        logger.info("部门{},的某个sku,spu缺货消息发送{},{}", dept, sku, spu);

        OrderNotifyRequired orderNotifyRequired = new OrderNotifyRequired();
        orderNotifyRequired.setDeptId(dept);
        orderNotifyRequired.setSku(sku);
        orderNotifyRequired.setSpu(spu);
        orderNotifyRequired.setType(1);

        RabbitMQMessage rabbitMQMessage = new RabbitMQMessage().setMessageType(OrderNotifyRequired.MESSAGE_TYPE).setMethod(RabbitMQConfig.method_insert).setData(orderNotifyRequired);

        rabbitMQPublisher.saveMessage(rabbitMQMessage);

        return true;
    }


}
