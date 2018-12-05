package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 运单关联sku表（冗余订单系统）
 * @Created Time:2017/12/5 18:16
 * @Update Time:
 */
public class TransitItem extends AbstractParamEntity implements ITable, Serializable {

    /**
     * 自增主键
     */
    @DBColumn
    private Integer id;

    /**
     * 转寄仓id
     */
    @DBColumn
    private Integer transitId;
    /**
     * 订单id
     */
    @DBColumn
    private Long orderIdOld = new Long(0);

    /**
     * 原运单号
     */
    @DBColumn
    private String trackingNoOld;

    @DBColumn
    private Integer deptId;
    /**
     * 部门名称
     */
    @DBColumn
    private String deptName;

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

    /**
     * 创建时间
     */
    @DBColumn
    private java.time.LocalDateTime createAt = java.time.LocalDateTime.now();

    /**
     * rma申请单号
     */
    @DBColumn
    private Integer rmaId;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getRmaId() {
        return rmaId;
    }

    public void setRmaId(Integer rmaId) {
        this.rmaId = rmaId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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


    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }


    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getTransitId() {
        return transitId;
    }

    public void setTransitId(Integer transitId) {
        this.transitId = transitId;
    }

    @Override
    public String getTable() {
        return "transit_item";
    }
}