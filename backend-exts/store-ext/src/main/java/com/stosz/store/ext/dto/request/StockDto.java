package com.stosz.store.ext.dto.request;

import java.io.Serializable;

public class StockDto implements Serializable {

    private Integer deptId;//部门ID

    private String sku;//产品SKU

    private int lockStockQty;//锁库存数
    
    private boolean lockStatus;//状态

    public StockDto() {

    }

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

    public int getLockStockQty() {
        return lockStockQty;
    }

    public void setLockStockQty(int lockStockQty) {
        this.lockStockQty = lockStockQty;
    }

    public boolean isLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public StockDto(Integer deptId, String sku) {
        this.deptId = deptId;
        this.sku = sku;
    }

    public StockDto(Integer deptId, String sku, Integer lockStockQty) {
        super();
        this.deptId = deptId;
        this.sku = sku;
        this.lockStockQty = lockStockQty;
    }
}
