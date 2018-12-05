package com.stosz.purchase.ext.model;

import com.stosz.plat.model.AbstractParamEntity;

import java.io.Serializable;
import java.util.List;

public class PurchaseRequiredSpu extends AbstractParamEntity implements Serializable{

    private Integer productId;// 产品ID

    private String productTitle;// 产品标题

    private String mainImageUrl;// 产品图片

    private Integer buyerId;// 采购员ID

    private String buyer;// 采购员
    
    private Integer buDeptId;//事业部ID

    private String buDeptName;//事业部名称

    private String buDeptNo;//事业部编号
    
    private String spu;

    private List<PurchaseRequiredSku> purchaseRequiredSkus;
    
    

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }


    public Integer getBuDeptId() {
        return buDeptId;
    }

    public void setBuDeptId(Integer buDeptId) {
        this.buDeptId = buDeptId;
    }

    public String getBuDeptName() {
        return buDeptName;
    }

    public void setBuDeptName(String buDeptName) {
        this.buDeptName = buDeptName;
    }

    public String getBuDeptNo() {
        return buDeptNo;
    }

    public void setBuDeptNo(String buDeptNo) {
        this.buDeptNo = buDeptNo;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
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

    public List<PurchaseRequiredSku> getPurchaseRequiredSkus() {
        return purchaseRequiredSkus;
    }

    public void setPurchaseRequiredSkus(List<PurchaseRequiredSku> purchaseRequiredSkus) {
        this.purchaseRequiredSkus = purchaseRequiredSkus;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
    }
}
