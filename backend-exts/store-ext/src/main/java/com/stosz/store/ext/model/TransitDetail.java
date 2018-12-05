package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 转寄流水表
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class TransitDetail extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;
    /**
     * 转寄仓id
     */
    @DBColumn
    private Integer transitId;
    /**
     * 仓库id
     */
    @DBColumn
    private Integer wmsId;
    /**
     * 仓库名称
     */
    @DBColumn
    private String stockName;
    @DBColumn
    private String trackingNoOld;
    @DBColumn
    private String trackingNoNew;
    @DBColumn
    private Long OrderIdOld;
    @DBColumn
    private Long OrderIdNew;
    @DBColumn
    private String state;
    @DBColumn
    private String storageLocal;
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public String getTrackingNoNew() {
        return trackingNoNew;
    }

    public void setTrackingNoNew(String trackingNoNew) {
        this.trackingNoNew = trackingNoNew;
    }

    public Long getOrderIdOld() {
        return OrderIdOld;
    }

    public void setOrderIdOld(Long orderIdOld) {
        OrderIdOld = orderIdOld;
    }

    public Long getOrderIdNew() {
        return OrderIdNew;
    }

    public void setOrderIdNew(Long orderIdNew) {
        OrderIdNew = orderIdNew;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStorageLocal() {
        return storageLocal;
    }

    public void setStorageLocal(String storageLocal) {
        this.storageLocal = storageLocal;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getTransitId() {
        return transitId;
    }

    public void setTransitId(Integer transitId) {
        this.transitId = transitId;
    }

    @Override
    public String getTable() {
        return "transit_detail";
    }
}
