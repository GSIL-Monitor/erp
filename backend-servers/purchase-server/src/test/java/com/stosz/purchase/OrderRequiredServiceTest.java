package com.stosz.purchase;

import com.stosz.BaseTest;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import org.junit.Test;

import javax.annotation.Resource;

public class OrderRequiredServiceTest extends BaseTest {

    @Resource
    RabbitMQPublisher rabbitMQPublisher;

    @Test
    public void testOrderRequiredQueue() {
        for(int i=1;i<4;i++){
            RabbitMQMessage rabbitMQMessage = new RabbitMQMessage();
            OrderNotifyRequired orderNotifyRequired = new OrderNotifyRequired();
            orderNotifyRequired.setDeptId(12);
            if(i<10){
                orderNotifyRequired.setSku("D00876880000"+i);
            }else{
                orderNotifyRequired.setSku("D0087688000"+i);
            }
            orderNotifyRequired.setSpu("D0087688");
            orderNotifyRequired.setType(1);

            /**
             *  41  1062980  ST50090
             43  1062982  ST50090
             45  1062996  ST50092
             50  1005190  ST50092
             51  1062998  ST50092
             */
            rabbitMQMessage.setData(orderNotifyRequired);
            rabbitMQMessage.setMessageType(orderNotifyRequired.MESSAGE_TYPE);
            rabbitMQMessage.setMethod(RabbitMQConfig.method_insert);
            logger.info(JsonUtil.toJson(rabbitMQMessage));
            rabbitMQPublisher.saveMessage(rabbitMQMessage);
        }

    }
}
