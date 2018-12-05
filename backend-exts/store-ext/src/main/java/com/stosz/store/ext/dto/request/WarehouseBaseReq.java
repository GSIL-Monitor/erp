package com.stosz.store.ext.dto.request;

import com.stosz.plat.common.BaseDTO;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class WarehouseBaseReq extends BaseDTO implements Serializable {

    private Integer wmsId;
    private Integer deptId;
    private String spu;
    private String sku;

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
}
