package com.stosz.old.erp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by ChenShifeng on 2018-1-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 老erp 仓库表实体类
 */
public class OldErpWarehouse extends AbstractParamEntity implements ITable, Serializable {

    /**
     * 自增主键
     */
    @DBColumn
    private Integer idWarehouse;
    @DBColumn
    private Integer idZone;//区域id

    private String zoneName;//区域名
    /**
     * 优先级:订单出货优先级(1~10)
     */
    @DBColumn
    private Integer priority;
    /**
     * 仓库名称
     */
    @DBColumn
    private String title;
    /**
     * 仓库地址
     */
    @DBColumn
    private String address;
    /**
     * 是否投入使用 0-否   1-是
     */
    @DBColumn
    private Integer status;
    /**
     * 是否为转寄仓库 0-否   1-是
     */
    @DBColumn
    private Integer forward;
    /**
     * WMS仓库编码
     */
    @DBColumn
    private String warehouseWmsTitle;

    /**
     * 是否为wms仓库 0-否   1-是
     */
    @DBColumn
    private Integer wmswarehouse;

    /**
     * 创建时间
     */
    @DBColumn
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
    /**
     * 更新时间
     */
    @DBColumn
    private java.time.LocalDateTime updatedAt = java.time.LocalDateTime.now();

    public OldErpWarehouse() {
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(Integer idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getForward() {
        return forward;
    }

    public void setForward(Integer forward) {
        this.forward = forward;
    }

    public String getWarehouseWmsTitle() {
        return warehouseWmsTitle;
    }

    public void setWarehouseWmsTitle(String warehouseWmsTitle) {
        this.warehouseWmsTitle = warehouseWmsTitle;
    }

    public Integer getWmswarehouse() {
        return wmswarehouse;
    }

    public void setWmswarehouse(Integer wmswarehouse) {
        this.wmswarehouse = wmswarehouse;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getTable() {
        return "erp_warehouse";
    }

}