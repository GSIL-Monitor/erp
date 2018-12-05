package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseInItem extends AbstractParamEntity implements ITable,Serializable {

    @DBColumn
    private Integer id;
    @DBColumn
    private LocalDateTime createAt;

    private LocalDateTime updateAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;
    @DBColumn
    private Integer purchaseInId;
    @DBColumn
    private Integer deptId;

    private String deptNo;
    @DBColumn
    private String deptName;
    @DBColumn
    private String sku;
    @DBColumn
    private String spu;
    @DBColumn
    private BigDecimal price;
    @DBColumn
    private Integer instockQty;
    @DBColumn
    private Integer qcfailQty;
    @DBColumn
    private Integer quantity;
    @DBColumn
    private BigDecimal amount;



    @Override
    public Integer getId() {
        return id;
    }


    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getPurchaseInId() {
        return purchaseInId;
    }

    public void setPurchaseInId(Integer purchaseInId) {
        this.purchaseInId = purchaseInId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getInstockQty() {
        return instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public Integer getQcfailQty() {
        return qcfailQty;
    }

    public void setQcfailQty(Integer qcfailQty) {
        this.qcfailQty = qcfailQty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    @Override
    public String getTable() {
        return "purchase_in_item";
    }
}