package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.util.List;

public class PurchaseReturnSpu implements Serializable{

    private Integer productId;// 产品ID

    private String productTitle;// 产品标题

    private String mainImageUrl;// 产品图片

    private String spu;

    private List<PurchaseReturnSku> purchaseReturnSkus;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public List<PurchaseReturnSku> getPurchaseReturnSkus() {
        return purchaseReturnSkus;
    }

    public void setPurchaseReturnSkus(List<PurchaseReturnSku> purchaseReturnSkus) {
        this.purchaseReturnSkus = purchaseReturnSkus;
    }
}
