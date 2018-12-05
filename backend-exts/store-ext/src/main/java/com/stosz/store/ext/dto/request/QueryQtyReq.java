package com.stosz.store.ext.dto.request;

import java.io.Serializable;

public class QueryQtyReq implements Serializable{

    private Integer wmsId;//仓库ID

    private Integer deptId;//部门ID

    private String departmentName; //部门名称

    private String spu;//产品spu

    private String sku;//产品SKU

    public QueryQtyReq() {

    }

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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
