package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class PurchaseReturnSku implements Serializable{

    private String sku;// 产品标题

    private String skuTitle;// 产品图片

    private BigDecimal price;

    private List<DeptSkuQty> deptSkuQties;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<DeptSkuQty> getDeptSkuQties() {
        return deptSkuQties;
    }

    public void setDeptSkuQties(List<DeptSkuQty> deptSkuQties) {
        this.deptSkuQties = deptSkuQties;
    }
}
