package com.stosz.store.ext.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;


public class SearchTransitPageInfoRes implements Serializable {

    private Integer id;
    /**
     * 原运单号
     */
    private String trackingNoOld;
    /**
     * 地区
     */
    private String zoneName;
    /**
     * 库存状态
     */
    private String state;
    /**
     * 库存状态名称
     */
    private String transitStateName;
    /**
     * 物流公司名称(原订单的)
     */
    private String logisticsNameOld;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 仓库id
     */
    private Integer wmsId;
    /**
     * 仓库名称
     */
    private String stockName;

    /**
     * 产品信息（产品标题）
     */
    private String productTitle;
    /**
     * 内部名
     */
    private String innerName;
    /**
     * 外文名称
     */
    private String foreignTitle;
    /**
     * sku
     */
    private String sku;
    /**
     * 原订单号id
     */
    private String orderIdOld;

    /**
     * 库位
     */
    private String storageLocal;
    /**
     * 入库方式
     */
    private String inStorageType;
    /**
     * 入库日期时间
     */
    private LocalDateTime inStorageTime;
    /**
     * 库龄
     */
    private Integer storageAge;

    /**
     * 新订单号id(已匹配订单号)
     */
    private String orderIdNew;
    /**
     * 下单日期时间
     */
    private LocalDateTime orderAt;
    /**
     * 拣货导出时间
     */
    private LocalDateTime exportAt;

    /**
     * 拣货导出人
     */
    private String exporter;

    /**
     * 发货运单号(新运单)
     */
    private String trackingNoNew;

    /**
     * 物流公司名称(新)
     */
    private String logisticsNameNew;

    /**
     * 物流状态(新)
     */
    private String trackingStatus;

    /**
     * 发货时间
     */
    private LocalDateTime trackingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTransitStateName() {
        return transitStateName;
    }

    public void setTransitStateName(String transitStateName) {
        this.transitStateName = transitStateName;
    }

    public String getLogisticsNameOld() {
        return logisticsNameOld;
    }

    public void setLogisticsNameOld(String logisticsNameOld) {
        this.logisticsNameOld = logisticsNameOld;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }

    public String getForeignTitle() {
        return foreignTitle;
    }

    public void setForeignTitle(String foreignTitle) {
        this.foreignTitle = foreignTitle;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(String orderIdOld) {
        this.orderIdOld = orderIdOld;
    }

    public String getStorageLocal() {
        return storageLocal;
    }

    public void setStorageLocal(String storageLocal) {
        this.storageLocal = storageLocal;
    }

    public String getInStorageType() {
        return inStorageType;
    }

    public void setInStorageType(String inStorageType) {
        this.inStorageType = inStorageType;
    }

    public LocalDateTime getInStorageTime() {
        return inStorageTime;
    }

    public void setInStorageTime(LocalDateTime inStorageTime) {
        this.inStorageTime = inStorageTime;
    }

    public Integer getStorageAge() {
        return storageAge;
    }

    public void setStorageAge(Integer storageAge) {
        this.storageAge = storageAge;
    }

    public String getOrderIdNew() {
        return orderIdNew;
    }

    public void setOrderIdNew(String orderIdNew) {
        this.orderIdNew = orderIdNew;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public LocalDateTime getExportAt() {
        return exportAt;
    }

    public void setExportAt(LocalDateTime exportAt) {
        this.exportAt = exportAt;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }

    public String getTrackingNoNew() {
        return trackingNoNew;
    }

    public void setTrackingNoNew(String trackingNoNew) {
        this.trackingNoNew = trackingNoNew;
    }

    public String getLogisticsNameNew() {
        return logisticsNameNew;
    }

    public void setLogisticsNameNew(String logisticsNameNew) {
        this.logisticsNameNew = logisticsNameNew;
    }

    public String getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(String trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public LocalDateTime getTrackingTime() {
        return trackingTime;
    }

    public void setTrackingTime(LocalDateTime trackingTime) {
        this.trackingTime = trackingTime;
    }
}
