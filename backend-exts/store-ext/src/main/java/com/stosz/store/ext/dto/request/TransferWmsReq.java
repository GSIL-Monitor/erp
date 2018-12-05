package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:56
 */
public class TransferWmsReq implements Serializable {

    private String orderCode;
    private String sourceType;//类型
    private String fromWhcode;//调出仓库id
    private String targetWhcode;//调入仓库id
    private String fromGoodsOwner = "1000";//调出货主  布谷鸟 对应id
    private String targetGoodsOwner = "1000";//调入货主
    private List<TransferItemReq> detailList;//skus

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getFromWhcode() {
        return fromWhcode;
    }

    public void setFromWhcode(String fromWhcode) {
        this.fromWhcode = fromWhcode;
    }

    public String getTargetWhcode() {
        return targetWhcode;
    }

    public void setTargetWhcode(String targetWhcode) {
        this.targetWhcode = targetWhcode;
    }

    public List<TransferItemReq> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TransferItemReq> detailList) {
        this.detailList = detailList;
    }

    public String getFromGoodsOwner() {
        return fromGoodsOwner;
    }

    public void setFromGoodsOwner(String fromGoodsOwner) {
        this.fromGoodsOwner = fromGoodsOwner;
    }

    @Override
    public String toString() {
        return "TransferWmsReq{" +
                "orderCode='" + orderCode + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", fromWhcode='" + fromWhcode + '\'' +
                ", targetWhcode='" + targetWhcode + '\'' +
                ", fromGoodsOwner='" + fromGoodsOwner + '\'' +
                ", targetGoodsOwner='" + targetGoodsOwner + '\'' +
                ", detailList=" + detailList +
                '}';
    }

    public String getTargetGoodsOwner() {
        return targetGoodsOwner;
    }

    public void setTargetGoodsOwner(String targetGoodsOwner) {
        this.targetGoodsOwner = targetGoodsOwner;
    }
}
