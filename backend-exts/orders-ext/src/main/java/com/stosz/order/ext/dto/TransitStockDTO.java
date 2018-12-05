package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库表
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class TransitStockDTO implements Serializable {
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
     * 原订单id
     */
    private Long orderIdOld;
    /**
     * 内部名 格式：商品一,商品二,商品三
     */
    private String innerName;
    /**
     * sku
     * 格式：sku1,sku2,sku3
     */
    private String sku;
    /**
     * 运单号
     */
    private String trackingNoOld;

    /**
     *  rma申请单号
     */
    private Integer rmaId;

    /**
     *  订单明细list
     */
    private List<TransitItemDTO> TransitItemList;

    public Integer getRmaId() {
        return rmaId;
    }

    public void setRmaId(Integer rmaId) {
        this.rmaId = rmaId;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Long getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(Long orderIdOld) {
        this.orderIdOld = orderIdOld;
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

    public List<TransitItemDTO> getTransitItemList() {
        return TransitItemList;
    }

    public void setTransitItemList(List<TransitItemDTO> transitItemList) {
        TransitItemList = transitItemList;
    }
}
