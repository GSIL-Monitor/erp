package com.stosz.store.ext.dto.request;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:56
 */
public class TransferItemReq implements Serializable {

    private String sku;//产品sku
    private Integer qty;//预计调拨数

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "TransferItemReq{" +
                "sku='" + sku + '\'' +
                ", qty=" + qty +
                '}';
    }
}
