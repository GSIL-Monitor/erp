package com.stosz.purchase.ext.model;

import java.io.Serializable;

public class DeptSkuQty implements Serializable {

    private Integer purchaseItemId;
    private Integer deptId;
    private String deptName;
    private Integer intransitQty=0; //在途数
    private Integer instockQty=0; //入库数
    private Integer purchaseQty=0; //采购单需求数量
    private Integer returnQty=0; //
    private Integer useQty=0; //sku可用库存
    private Integer intransitCancleQty = 0; //已采退数
    private Integer quantity = 0; //采购数量


    public Integer getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Integer purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
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

    public Integer getIntransitQty() {
        return intransitQty;
    }

    public void setIntransitQty(Integer intransitQty) {
        this.intransitQty = intransitQty;
    }

    public Integer getInstockQty() {
        return instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public Integer getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public Integer getIntransitCancleQty() {
        return intransitCancleQty;
    }

    public void setIntransitCancleQty(Integer intransitCancleQty) {
        this.intransitCancleQty = intransitCancleQty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
