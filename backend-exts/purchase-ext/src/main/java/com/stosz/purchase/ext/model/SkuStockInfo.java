package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Sku库存相关信息
 *
 * @author minxiaolong
 * @create 2018-01-08 17:46
 **/
public class SkuStockInfo implements Serializable{

    private static final long serialVersionUID = -6641720230931077134L;
    private String sku;
    private String title;
    //松岗库存
    private Integer sgStockQty;
    //海外库存
    private Integer overseasStockQty;
    //日均销量
    private Integer dailySales;
    //采购成本
    private BigDecimal purchaseCost;
    //部门名称
    private String deptName;
    //部门id
    private Integer deptId;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSgStockQty() {
        return sgStockQty;
    }

    public void setSgStockQty(Integer sgStockQty) {
        this.sgStockQty = sgStockQty;
    }

    public Integer getOverseasStockQty() {
        return overseasStockQty;
    }

    public void setOverseasStockQty(Integer overseasStockQty) {
        this.overseasStockQty = overseasStockQty;
    }

    public Integer getDailySales() {
        return dailySales;
    }

    public void setDailySales(Integer dailySales) {
        this.dailySales = dailySales;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
