package com.stosz.order.sync.mq.consumer;

import com.stosz.order.ext.mq.MatchLogisticsModel;
import com.stosz.order.service.outsystem.logistics.service.ILogisticsService;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 订单推送消息到物流系统
 * @author liusl
 */
@Component
public class OrderToLogisticsHandler extends AbstractHandler<MatchLogisticsModel> {

    @Autowired
    private ILogisticsService logisticsService;

    @Override
    public boolean handleMessage(String method, MatchLogisticsModel matchLogisticsModel) {
        return logisticsService.notifyOrderToLogistics(matchLogisticsModel);
    }
    
    @Override
    public Class<MatchLogisticsModel> getTClass() {
        return MatchLogisticsModel.class;
    }


    
    
}
