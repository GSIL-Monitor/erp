package com.stosz.purchase.sync.mq;

import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import com.stosz.purchase.ext.service.IOrderRequiredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderRequiredHandler extends AbstractHandler<OrderNotifyRequired> {

    @Resource
    private IOrderRequiredService orderRequiredService;

    @Override
    public boolean handleMessage(String method, OrderNotifyRequired dataItem) {
        orderRequiredService.notifyOrderRequired(dataItem);
        return true;
    }

    @Override
    public Class<OrderNotifyRequired> getTClass() {
        return OrderNotifyRequired.class;
    }

}
