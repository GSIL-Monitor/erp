package com.stosz.store.ext.dto.response;

import java.io.Serializable;

/**
 * @Author:ChenShifeng
 * @Description:转寄仓配货接口返回信息
 * @Created Time:2017/12/22 18:16
 * @Update Time:
 */
public class MatchPackRes implements Serializable {

    private Integer id;//转寄仓库id

    private Integer wmsId;//仓库id
    /**
     * 仓库名称
     */
    private String stockName;


    private String trackingNoOld;

    private Long orderIdOld;
    /**
     * 库位
     */
    private String storageLocal;

    /**
     * sku集
     */
    private String skus;

    /**
     * 数量集
     */
    private String qtys;

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    public String getQtys() {
        return qtys;
    }

    public void setQtys(String qtys) {
        this.qtys = qtys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public Long getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(Long orderIdOld) {
        this.orderIdOld = orderIdOld;
    }

    public String getStorageLocal() {
        return storageLocal;
    }

    public void setStorageLocal(String storageLocal) {
        this.storageLocal = storageLocal;
    }
}
