package com.stosz.purchase.ext.common;

import com.stosz.purchase.ext.model.PurchaseItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/17]
 */
public class PurchaseSkuDto implements Serializable{

    private String sku;

    private String skuTitle;

    private Integer totalPurchaseQty;

    private Integer totalAvgSaleQty;

    private Integer totalPendingOrderQty;

    private Integer totalInstockQty;//总入库数

    private Integer totalIntransitQty;//总在途数

    private Integer totalUseAbleQty;//总的可用库存数

    private Integer totalReturnedQty;//总的采退数

    private BigDecimal price;

    private Integer totalQuantity;

    private List<PurchaseItem> purchaseItemList;

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

    public Integer getTotalPurchaseQty() {
        return totalPurchaseQty;
    }

    public void setTotalPurchaseQty(Integer totalPurchaseQty) {
        this.totalPurchaseQty = totalPurchaseQty;
    }

    public Integer getTotalAvgSaleQty() {
        return totalAvgSaleQty;
    }

    public void setTotalAvgSaleQty(Integer totalAvgSaleQty) {
        this.totalAvgSaleQty = totalAvgSaleQty;
    }

    public Integer getTotalPendingOrderQty() {
        return totalPendingOrderQty;
    }

    public void setTotalPendingOrderQty(Integer totalPendingOrderQty) {
        this.totalPendingOrderQty = totalPendingOrderQty;
    }


    public List<PurchaseItem> getPurchaseItemList() {
        return purchaseItemList;
    }

    public void setPurchaseItemList(List<PurchaseItem> purchaseItemList) {
        this.purchaseItemList = purchaseItemList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }


    public Integer getTotalInstockQty() {
        return totalInstockQty;
    }

    public void setTotalInstockQty(Integer totalInstockQty) {
        this.totalInstockQty = totalInstockQty;
    }

    public Integer getTotalIntransitQty() {
        return totalIntransitQty;
    }

    public void setTotalIntransitQty(Integer totalIntransitQty) {
        this.totalIntransitQty = totalIntransitQty;
    }

    public Integer getTotalUseAbleQty() {
        return totalUseAbleQty;
    }

    public void setTotalUseAbleQty(Integer totalUseAbleQty) {
        this.totalUseAbleQty = totalUseAbleQty;
    }

    public Integer getTotalReturnedQty() {
        return totalReturnedQty;
    }

    public void setTotalReturnedQty(Integer totalReturnedQty) {
        this.totalReturnedQty = totalReturnedQty;
    }

    @Override
    public String toString() {
        return "PurchaseSkuDto{" +
                "sku='" + sku + '\'' +
                ", skuTitle='" + skuTitle + '\'' +
                ", totalPurchaseQty=" + totalPurchaseQty +
                ", totalAvgSaleQty=" + totalAvgSaleQty +
                ", totalPendingOrderQty=" + totalPendingOrderQty +
                ", price=" + price +
                ", totalQuantity=" + totalQuantity +
                ", purchaseItemList=" + purchaseItemList +
                '}';
    }
}
