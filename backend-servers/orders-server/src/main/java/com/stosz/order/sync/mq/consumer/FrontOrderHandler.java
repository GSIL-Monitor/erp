package com.stosz.order.sync.mq.consumer;

import com.stosz.order.ext.dto.FrontOrderDTO;
import com.stosz.order.service.FrontOrderService;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 从建站导入订单到订单系统，通过mq消费者的方式进行；
 */

@Component
public class FrontOrderHandler extends AbstractHandler<FrontOrderDTO> {

    public static final Logger logger = LoggerFactory.getLogger(FrontOrderHandler.class);

    @Autowired
    private FrontOrderService frontOrderService;

    @Override
    public boolean handleMessage(String method, FrontOrderDTO dataItem) {
        try{
            frontOrderService.save(dataItem);
        }catch (RuntimeException e){
            logger.error("从建站导入订单出错:"+e.getMessage());
        }
        return true;
    }

    @Override
    public Class<FrontOrderDTO> getTClass() {
        return FrontOrderDTO.class;
    }
}
