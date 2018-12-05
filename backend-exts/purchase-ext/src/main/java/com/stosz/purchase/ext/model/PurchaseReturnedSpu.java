package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.util.List;

public class PurchaseReturnedSpu implements Serializable{

    private Integer productId;// 产品ID

    private String productTitle;// 产品标题

    private String mainImageUrl;// 产品图片
    
    private List<PurchaseReturnedSku> purchaseReturnedSkus;
    
    

    public List<PurchaseReturnedSku> getPurchaseReturnedSkus() {
        return purchaseReturnedSkus;
    }

    public void setPurchaseReturnedSkus(List<PurchaseReturnedSku> purchaseReturnedSkus) {
        this.purchaseReturnedSkus = purchaseReturnedSkus;
    }

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

}
