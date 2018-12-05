package com.stosz.order.ext.mq;

import java.io.Serializable;

/**
 * 
 * 物流匹配状态实体（爬虫）
 * @author liusl
 */
public class MatchLogisticsStatusModel implements Serializable {

    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = 5692470174912939659L;
    private Integer orderId = 0;
    
    private Integer trackingStatus;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    
    public Integer getTrackingStatus() {
        return trackingStatus;
    }

    
    public void setTrackingStatus(Integer trackingStatus) {
        this.trackingStatus = trackingStatus;
    }
    
}
