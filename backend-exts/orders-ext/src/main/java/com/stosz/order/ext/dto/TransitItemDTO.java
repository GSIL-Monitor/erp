package com.stosz.order.ext.dto;

import com.stosz.plat.model.DBColumn;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author:ChenShifeng
 * @Description: 运单关联sku表（冗余订单系统）
 * @Created Time:2017/12/5 18:16
 * @Update Time:
 */
public class TransitItemDTO implements  Serializable {
    /**
     * 订单id
     */
    @DBColumn
    private Long orderIdOld = new Long(0);
    /**
     * 运单号
     */
    @DBColumn
    private String trackingNoOld;
    @DBColumn
    private Integer deptId;
    /**
     * 产品spu
     */
    @DBColumn
    private String spu;
    /**
     * 产品sku
     */
    @DBColumn
    private String sku;
    /**
     * 数量
     */
    @DBColumn
    private Integer qty;
    /**
     * 内部名称
     */
    @DBColumn
    private String innerTitle;
    /**
     * 单价
     */
    @DBColumn
    private java.math.BigDecimal singleAmount = new java.math.BigDecimal(0);
    /**
     * 总价
     */
    @DBColumn
    private java.math.BigDecimal totalAmount = new java.math.BigDecimal(0);
    /**
     * 产品标题
     */
    @DBColumn
    private String productTitle;
    /**
     * 属性值数组，用于展示
     */
    @DBColumn
    private String attrTitleArray;
    /**
     * 外文名称
     */
    @DBColumn
    private String foreignTitle;
    /**
     * 带外文的总价
     */
    @DBColumn
    private String foreignTotalAmount;

    public String getInnerTitle() {
        return innerTitle;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public Long getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(Long orderIdOld) {
        this.orderIdOld = orderIdOld;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public String getForeignTitle() {
        return foreignTitle;
    }

    public void setForeignTitle(String foreignTitle) {
        this.foreignTitle = foreignTitle;
    }

    public String getForeignTotalAmount() {
        return foreignTotalAmount;
    }

    public void setForeignTotalAmount(String foreignTotalAmount) {
        this.foreignTotalAmount = foreignTotalAmount;
    }
}