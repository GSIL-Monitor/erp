package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 采购获取获取部门分配sku流水请求参数
 */
public class PurchaseItemRq implements Serializable {
    /**
     * 仓库ID
     */
    private Integer wmsId;
    /**
     * sku
     */
    private String sku;
    /**
     * 采购数量
     */
    private Integer purchaseQty;
    /**
     * 部门id,本次采购的最大数
     */
    private Map<Integer,Integer> deptMaxQty;

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public Map<Integer, Integer> getDeptMaxQty() {
        return deptMaxQty;
    }

    public void setDeptMaxQty(Map<Integer, Integer> deptMaxQty) {
        this.deptMaxQty = deptMaxQty;
    }
}
