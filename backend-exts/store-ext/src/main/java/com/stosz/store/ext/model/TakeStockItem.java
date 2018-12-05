package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * @Author:ChenShifeng
 * @Description: 盘点主表
 * @Created Time:2017/12/22 18:16
 * @Update Time:
 */
public class TakeStockItem extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;
    /**
     * 盘点单id
     */
    @DBColumn
    private Integer takeStockId;

    /**
     * 转寄仓id
     */
    @DBColumn
    private Integer transitId;

    /**
     * 原运单号
     */
    @DBColumn
    private String trackingNoOld;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTakeStockId() {
        return takeStockId;
    }

    public void setTakeStockId(Integer takeStockId) {
        this.takeStockId = takeStockId;
    }

    public Integer getTransitId() {
        return transitId;
    }

    public void setTransitId(Integer transitId) {
        this.transitId = transitId;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    @Override
    public String getTable() {
        return "take_stock_item";
    }

}
