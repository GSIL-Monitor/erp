package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PurchaseReturnedSku implements Serializable{

    private String sku;

    private String skuTitle;// sku标题

    private Integer quantity;// 采购数

    private Integer instockQty;// 入库数

    private Integer intransitQty;// 在途数

    private Integer usableStockQty;// 可用库存数

    private Integer purchaseCancleQty;// 采退数

    private BigDecimal purchasePrice;// 采购单价

    private List<PurchaseReturnedDept> purchaseReturnedDepts;

    public List<PurchaseReturnedDept> getPurchaseReturnedDepts() {
        return purchaseReturnedDepts;
    }

    public void setPurchaseReturnedDepts(List<PurchaseReturnedDept> purchaseReturnedDepts) {
        this.purchaseReturnedDepts = purchaseReturnedDepts;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

}
