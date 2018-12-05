package com.stosz.order.service.outsystem.logistics.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 订单推送到物流系统的实体
 * @author liusl
 */
public class OrderNotifyLogistics implements  Serializable {
    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = -3678721982172432837L;
    /**
     * 秘钥 必填
     */
    private String key;
    /**
     * 订单信息 必填
     */
    private OrderInfo order_info;
    /**
     * 客户信息 必填
     */
    private CustomerInfo customer_info;
    /**
     * 产品信息 必填
     */
    private List<ProductInfo> product_info;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public OrderInfo getOrder_info() {
        return order_info;
    }
    
    public void setOrder_info(OrderInfo order_info) {
        this.order_info = order_info;
    }
    
    public CustomerInfo getCustomer_info() {
        return customer_info;
    }
    
    public void setCustomer_info(CustomerInfo customer_info) {
        this.customer_info = customer_info;
    }

    
    public List<ProductInfo> getProduct_info() {
        return product_info;
    }

    
    public void setProduct_info(List<ProductInfo> product_info) {
        this.product_info = product_info;
    }
    
    
}
