package com.stosz.store.mq;

import com.stosz.BaseTest;
import com.stosz.order.ext.mq.OccupyStockModel;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.rabbitmq.consumer.RabbitMQListener;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/15 15:13
 */
public class RabbitMQTest extends BaseTest {

    @Autowired
    private RabbitMQListener rabbitMQListener;

    @Autowired
    RabbitMQPublisher rabbitMQPublisher;

    @Test
    public void saveMessage() throws Exception {

        rabbitMQPublisher.saveMessage("test1", "update", "yangqinghui");
    }


    @Test
    public void ordersOccupyStock() throws Exception {
        OrderNotifyRequired orderNotifyRequired = new OrderNotifyRequired();
        orderNotifyRequired.setDeptId(1);
        orderNotifyRequired.setSku("1231");
        orderNotifyRequired.setSpu("231534");
        orderNotifyRequired.setType(1);
        rabbitMQPublisher.saveMessage(orderNotifyRequired.MESSAGE_TYPE, orderNotifyRequired);

        logger.info("mq---------");
        logger.info("success");
    }


}
