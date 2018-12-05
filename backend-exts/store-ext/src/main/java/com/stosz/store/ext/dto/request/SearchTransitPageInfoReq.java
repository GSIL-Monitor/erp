package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 搜索转寄
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public class SearchTransitPageInfoReq implements Serializable {
    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 仓库id
     */
    private Integer wmsId;

    /**
     * 库存状态
     */
    private String state;

    /**
     * 入库日期开始时间
     */
    private LocalDateTime inStorageStartTime;

    /**
     * 入库日期结束时间
     */
    private LocalDateTime inStorageEndTime;

    /**
     * 下单日期开始时间
     */
    private LocalDateTime orderStartTime;

    /**
     * 下单日期结束时间
     */
    private LocalDateTime orderEndTime;

    /**
     * 拣货导出日期开始时间
     */
    private LocalDateTime pickingStartTime;

    /**
     * 拣货导出日期结束时间
     */
    private LocalDateTime pickingEndTime;

    /**
     * 新订单号id
     */
    private String orderIdNew;

    /**
     * 内部名  模糊查
     */
    private String innerName;

    /**
     * sku
     */
    private String sku;

    /**
     * 库龄  >=
     */
    private Integer storageAge;

    /**
     * 原运单号
     */
    private String trackingNoOld;

    /**
     * 发货运单号
     */
    private String trackingNoNew;

    /**
     * 原运单号批量
     */
    private String trackingNoOldBat;

    /**
     * 起始位置
     */
    private Integer start = 0;

    /**
     * 显示条数
     */
    private Integer limit = 20;

    public String getTrackingNoOldBat() {
        return trackingNoOldBat;
    }

    public void setTrackingNoOldBat(String trackingNoOldBat) {
        this.trackingNoOldBat = trackingNoOldBat;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getInStorageStartTime() {
        return inStorageStartTime;
    }

    public void setInStorageStartTime(LocalDateTime inStorageStartTime) {
        this.inStorageStartTime = inStorageStartTime;
    }

    public LocalDateTime getInStorageEndTime() {
        return inStorageEndTime;
    }

    public void setInStorageEndTime(LocalDateTime inStorageEndTime) {
        this.inStorageEndTime = inStorageEndTime;
    }

    public LocalDateTime getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(LocalDateTime orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public LocalDateTime getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(LocalDateTime orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public LocalDateTime getPickingStartTime() {
        return pickingStartTime;
    }

    public void setPickingStartTime(LocalDateTime pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    public LocalDateTime getPickingEndTime() {
        return pickingEndTime;
    }

    public void setPickingEndTime(LocalDateTime pickingEndTime) {
        this.pickingEndTime = pickingEndTime;
    }

    public String getOrderIdNew() {
        return orderIdNew;
    }

    public void setOrderIdNew(String orderIdNew) {
        this.orderIdNew = orderIdNew;
    }

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStorageAge() {
        return storageAge;
    }

    public void setStorageAge(Integer storageAge) {
        this.storageAge = storageAge;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public String getTrackingNoNew() {
        return trackingNoNew;
    }

    public void setTrackingNoNew(String trackingNoNew) {
        this.trackingNoNew = trackingNoNew;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
