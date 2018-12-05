package com.stosz.order.service.outsystem.logistics.service;

import com.stosz.order.ext.mq.MatchLogisticsModel;

import java.util.Map;

/**
 * 
 * 订单推送到物流接口
 * @author liusl
 */
public interface ILogisticsService {
    /**
     * 
     * 推送消息到订单系统
     * @date 2017年11月22日
     * @author liusl
     */
    boolean notifyOrderToLogistics(MatchLogisticsModel matchLogisticsModel);
    /**
     * 
     * 修改订单状态
     * @date 2017年11月23日
     * @author liusl
     */
    boolean updateOrderStatus(Map param);
}
