package com.stosz.purchase.ext.model;

import java.io.Serializable;

/**
 * 需求部门
 *
 * @author minxiaolong
 * @create 2018-01-08 17:51
 **/
public class DemandDepartment implements Serializable{

    private static final long serialVersionUID = -3469433049023049324L;

    private String departmentName;
    private String skuTitle;
    private Integer orderRequiredQty;// 订单需求数
    private Integer dailySales;//日均销量
    private Integer pendingQty; //待审物品数
    private Integer planQty; //采购计划数量
    private Integer transferStock; //调入库存数量

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public Integer getOrderRequiredQty() {
        return orderRequiredQty;
    }

    public void setOrderRequiredQty(Integer orderRequiredQty) {
        this.orderRequiredQty = orderRequiredQty;
    }

    public Integer getDailySales() {
        return dailySales;
    }

    public void setDailySales(Integer dailySales) {
        this.dailySales = dailySales;
    }

    public Integer getPendingQty() {
        return pendingQty;
    }

    public void setPendingQty(Integer pendingQty) {
        this.pendingQty = pendingQty;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getTransferStock() {
        return transferStock;
    }

    public void setTransferStock(Integer transferStock) {
        this.transferStock = transferStock;
    }
}
