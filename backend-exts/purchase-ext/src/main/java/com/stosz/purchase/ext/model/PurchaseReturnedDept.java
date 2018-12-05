package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseReturnedDept implements Serializable{

    private Integer id;

    private Integer deptId;// 部门ID

    private String deptName;// 部门名称

    private String deptNo;// 部门编号

    private Integer quantity;// 采购需求数

    private Integer instockQty;// 入库数

    private Integer intransitQty;// 在途数

    private Integer usableStockQty;// 可用库存数

    private Integer purchaseCancleQty;// 采退数

    private BigDecimal purchasePrice;// 采购单价

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


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getInstockQty() {
        return instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public Integer getIntransitQty() {
        return intransitQty;
    }

    public void setIntransitQty(Integer intransitQty) {
        this.intransitQty = intransitQty;
    }

    public Integer getUsableStockQty() {
        return usableStockQty;
    }

    public void setUsableStockQty(Integer usableStockQty) {
        this.usableStockQty = usableStockQty;
    }

    public Integer getPurchaseCancleQty() {
        return purchaseCancleQty;
    }

    public void setPurchaseCancleQty(Integer purchaseCancleQty) {
        this.purchaseCancleQty = purchaseCancleQty;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

}
