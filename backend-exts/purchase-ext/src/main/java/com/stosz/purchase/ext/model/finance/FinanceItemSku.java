package com.stosz.purchase.ext.model.finance;

import java.io.Serializable;

public class FinanceItemSku implements Serializable {

    private String sku;// sku

    private String skuTitle;// 产品标题

    private int qty = 0;

    public FinanceItemSku() {
    }

    public FinanceItemSku(String sku, String skuTitle, int qty) {
        this.sku = sku;
        this.skuTitle = skuTitle;
        this.qty = qty;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
