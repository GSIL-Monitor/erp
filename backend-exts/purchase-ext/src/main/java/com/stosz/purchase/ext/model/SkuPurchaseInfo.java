package com.stosz.purchase.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SkuPurchaseInfo extends AbstractParamEntity implements ITable ,Serializable{
    @DBColumn
    private Integer id;
    @DBColumn
    private Date createAt;
    @DBColumn
    private Date updateAt;
    @DBColumn
    private String sku;
    @DBColumn
    private Integer supplierId;
    @DBColumn
    private String purchaseUrl;
    @DBColumn
    private BigDecimal lastPurchasePrice;
    @DBColumn
    private Date lastPurchaseAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;

    private String supplierName;
    private int lowestFlag;

    public SkuPurchaseInfo() {

    }

    public SkuPurchaseInfo(String sku,Integer supplierId) {
        this.sku=sku;
        this.supplierId=supplierId;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public int getLowestFlag() {
        return lowestFlag;
    }

    public void setLowestFlag(int lowestFlag) {
        this.lowestFlag = lowestFlag;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(BigDecimal lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public Date getLastPurchaseAt() {
        return lastPurchaseAt;
    }

    public void setLastPurchaseAt(Date lastPurchaseAt) {
        this.lastPurchaseAt = lastPurchaseAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getTable() {
        return "sku_purchase_info";
    }
}