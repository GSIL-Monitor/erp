package com.stosz.order.ext.dto;

import com.stosz.order.ext.model.OrdersLink;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.ext.model.OrdersItem;

import java.util.List;

/**
 * 订单摘要DTO
 */
public class OrderAbstractDTO {

    /**
     * 客户联系信息
     */
    private OrdersLink ordersLink;


    /**
     * 订单信息
     */
    private Orders orders;


    /**
     * 订单项
     */
    private List<OrdersItem> orderItems;


    /**
     * 备注状态机历史?
     *
     */
    // TODO: 2017/11/25
    private List<String> history;


    public OrdersLink getOrdersLink() {
        return ordersLink;
    }

    public void setOrdersLink(OrdersLink ordersLink) {
        this.ordersLink = ordersLink;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public List<OrdersItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrdersItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
}
