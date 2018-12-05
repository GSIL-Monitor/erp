package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 采购获取获取部门分配sku流水
 */
public class PurchaseItemDTO implements Serializable{
    private Integer deptId;
    private String sku;
    private Integer qty;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
