package com.stosz.store.ext.dto.request;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:56
 */
public class WmsReqSku implements Serializable {

    private String sku;//产品sku
    private Integer transferQty;//预计调拨数
    private Integer outSkuReceived;//实际出库数量

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(Integer transferQty) {
        this.transferQty = transferQty;
    }

    public Integer getOutSkuReceived() {
        return outSkuReceived;
    }

    public void setOutSkuReceived(Integer outSkuReceived) {
        this.outSkuReceived = outSkuReceived;
    }

    @Override
    public String toString() {
        return "WmsReqSku{" +
                "sku='" + sku + '\'' +
                ", transferQty=" + transferQty +
                ", outSkuReceived=" + outSkuReceived +
                '}';
    }
}
