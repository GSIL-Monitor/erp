package com.stosz.purchase.ext.common;

/**
 * 采购信息回写的data
 * @author xiongchenyang
 * @version [1.0 , 2017/11/25]
 */
public class WriteBackItemDto {

    private String sku;

    private String skuReceived; //入库数量

    private String priceReceived; //入库金额

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuReceived() {
        return skuReceived;
    }

    public void setSkuReceived(String skuReceived) {
        this.skuReceived = skuReceived;
    }

    public String getPriceReceived() {
        return priceReceived;
    }

    public void setPriceReceived(String priceReceived) {
        this.priceReceived = priceReceived;
    }

    @Override
    public String toString() {
        return "WriteBackItemDto{" +
                "sku='" + sku + '\'' +
                ", skuReceived='" + skuReceived + '\'' +
                ", priceReceived='" + priceReceived + '\'' +
                '}';
    }
}
