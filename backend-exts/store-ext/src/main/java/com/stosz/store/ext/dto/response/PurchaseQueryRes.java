package com.stosz.store.ext.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class PurchaseQueryRes implements Serializable{

    private Integer wmsId;//仓库ID

    private Integer deptId;//部门ID

    private String departmentName; //部门名称

    private String spu;//产品spu

    private String sku;//产品SKU

    private Integer usableQty;

    private Integer otherUsableQty;

    private BigDecimal price;


    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getUsableQty() {
        return usableQty;
    }

    public void setUsableQty(Integer usableQty) {
        this.usableQty = usableQty;
    }

    public Integer getOtherUsableQty() {
        return otherUsableQty;
    }

    public void setOtherUsableQty(Integer otherUsableQty) {
        this.otherUsableQty = otherUsableQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
