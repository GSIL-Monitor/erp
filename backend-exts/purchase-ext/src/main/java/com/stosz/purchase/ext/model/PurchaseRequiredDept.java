package com.stosz.purchase.ext.model;

import java.io.Serializable;

public class PurchaseRequiredDept implements Serializable{
   
    private Integer id;
    private Integer planQty;//计划采购数
    
    private Integer purchaseQty;//采购需求数
    
    private Integer avgSaleQty;//3日平均交易
    
    private Integer pendingOrderQty;//待审订单数
    

    private Integer deptId;// 部门ID

    private String deptName;// 部门名称

    private String deptNo;// 部门编号


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public Integer getAvgSaleQty() {
        return avgSaleQty;
    }

    public void setAvgSaleQty(Integer avgSaleQty) {
        this.avgSaleQty = avgSaleQty;
    }

    public Integer getPendingOrderQty() {
        return pendingOrderQty;
    }

    public void setPendingOrderQty(Integer pendingOrderQty) {
        this.pendingOrderQty = pendingOrderQty;
    }
    
    
    
}
