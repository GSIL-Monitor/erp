package com.stosz.purchase.ext.common;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
public class OrderPurchaseItem implements Serializable {

    private String  wmsId;//仓库id

    private String sku;//采购的sku

    private Integer purchaseQty;//该sku采购的数量

    private Map<Integer, Integer> deptMaxQty;//各部门采购的数量

    public String getWmsId() {
        return wmsId;
    }

    public void setWmsId(String wmsId) {
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
