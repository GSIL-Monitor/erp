package com.stosz.order.fsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.mq.MatchLogisticsModel;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther carter
 * create time    2017-11-15
 *
 * 锁定库存成功之后，发送一条消息到队列中，进入匹配物流的状态；
 *
 */
@Component
public class LockStockSuccessAfter extends IFsmHandler<Orders> {

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;



    @Override
    public void execute(Orders orders, EventModel event) {

        MatchLogisticsModel matchLogisticsModel = new MatchLogisticsModel();
        matchLogisticsModel.setOrderId(orders.getId());

//        OrderWarehouse orderWarehouse =  orderWarehouseService.findByOrderId(orders.getId());
        matchLogisticsModel.setWarehouseId(orders.getWarehouseId());
        matchLogisticsModel.setWarehouseTypeEnum(orders.getWarehouseTypeEnum());

        rabbitMQPublisher.saveMessage(MatchLogisticsModel.MESSAGE_TYPE, RabbitMQConfig.method_insert,matchLogisticsModel);
    }



}
