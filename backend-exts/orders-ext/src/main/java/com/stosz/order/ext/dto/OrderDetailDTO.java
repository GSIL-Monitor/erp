package com.stosz.order.ext.dto;

import com.stosz.fsm.model.IFsmHistory;
import com.stosz.order.ext.model.OrdersItem;

import java.io.Serializable;
import java.util.List;

/**
 * 订单摘要DTO
 */
public class OrderDetailDTO implements Serializable {

    /**
     * 客户联系信息
     */
    private OrderLinkDTO orderLink;


    /**
     * 订单信息
     */
    private OrderInfoDTO orderInfo;


    /**
     * 订单项
     */
    private List<OrdersItem> orderItems;

    public OrderLinkDTO getOrderLink() {
        return orderLink;
    }

    
    public void setOrderLink(OrderLinkDTO orderLink) {
        this.orderLink = orderLink;
    }

    
    
    public OrderInfoDTO getOrderInfo() {
        return orderInfo;
    }


    
    public void setOrderInfo(OrderInfoDTO orderInfo) {
        this.orderInfo = orderInfo;
    }


    public List<OrdersItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrdersItem> orderItems) {
        this.orderItems = orderItems;
    }
}
