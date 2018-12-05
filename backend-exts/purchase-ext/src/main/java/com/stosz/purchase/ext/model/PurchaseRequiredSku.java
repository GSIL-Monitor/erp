package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.util.List;

public class PurchaseRequiredSku implements Serializable{

    private String sku;

    private String skuTitle;// sku标题

    private Integer state;// 状态 1 有效 0 无效

    private String supplierName;// 供应商名

    private Integer supplierId;// 供应商ID
    
    private String purchaseUrl;//采购链接

    private Integer planQty = 0;// 计划采购数

    private Integer purchaseQty = 0;// 采购需求数

    private Integer avgSaleQty = 0;// 3日平均交易

    private Integer pendingOrderQty = 0;// 待审订单数

    private Boolean display = true;//是否展现，默认为true
    
    public PurchaseRequiredSku() {
        
    }
    
    
    
    public PurchaseRequiredSku(String sku, Integer supplierId, Integer planQty,
            Integer purchaseQty, Integer avgSaleQty, Integer pendingOrderQty) {
        this.sku = sku;
        this.supplierId = supplierId;
        this.planQty = planQty;
        this.purchaseQty = purchaseQty;
        this.avgSaleQty = avgSaleQty;
        this.pendingOrderQty = pendingOrderQty;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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

    private List<PurchaseRequiredDept> purchaseRequiredDepts;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public List<PurchaseRequiredDept> getPurchaseRequiredDepts() {
        return purchaseRequiredDepts;
    }

    private  List<PurchaseRequired> purchaseRequiredList;

    public List<PurchaseRequired> getPurchaseRequiredList() {
        return purchaseRequiredList;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public void setPurchaseRequiredList(List<PurchaseRequired> purchaseRequiredList) {
        this.purchaseRequiredList = purchaseRequiredList;
    }

    public void setPurchaseRequiredDepts(List<PurchaseRequiredDept> purchaseRequiredDepts) {
        this.purchaseRequiredDepts = purchaseRequiredDepts;
    }

}
