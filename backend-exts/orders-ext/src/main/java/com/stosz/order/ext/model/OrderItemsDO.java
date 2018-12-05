package com.stosz.order.ext.model;

import java.math.BigDecimal;

/**
 * @auther tangtao
 * create time  2017/12/19
 */
public class OrderItemsDO {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 产品id
     */
    private Long productId;
    /**
     * 产品sku
     */
    private String sku;

    /**
     * 产品标题
     */
    private String productTitle;

    /**
     * 属性值数组，用于展示
     */
    private String attrTitleArray;

    /**
     * 数量
     */
    private Integer qty;
    /**
     * 单价
     */
    private java.math.BigDecimal singleAmount;
    /**
     * 总价
     */
    private java.math.BigDecimal totalAmount;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getAttrTitleArray() {
        return attrTitleArray;
    }

    public void setAttrTitleArray(String attrTitleArray) {
        this.attrTitleArray = attrTitleArray;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(BigDecimal singleAmount) {
        this.singleAmount = singleAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
