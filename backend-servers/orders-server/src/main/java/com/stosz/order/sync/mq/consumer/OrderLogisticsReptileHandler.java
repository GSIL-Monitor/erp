package com.stosz.order.sync.mq.consumer;

import com.stosz.order.ext.mq.MatchLogisticsStatusModel;
import com.stosz.order.service.outsystem.logistics.service.LogisticsServiceImpl;
import com.stosz.plat.rabbitmq.consumer.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 监听订单的物流状态  并修改
 * @author liusl
 */
@Component
public class OrderLogisticsReptileHandler extends AbstractHandler<MatchLogisticsStatusModel> {

    @Autowired
    private LogisticsServiceImpl logisticsServiceImpl;

    @Override
    public boolean handleMessage(String method, MatchLogisticsStatusModel matchLogisticsStatusModel) {
        return logisticsServiceImpl.updateOrderLogisticsStatus(matchLogisticsStatusModel);
    }
    
    @Override
    public Class<MatchLogisticsStatusModel> getTClass() {
        return MatchLogisticsStatusModel.class;
    }

    
}
