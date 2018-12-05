package com.stosz.store.ext.dto.response;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:出入仓，仓库数据调整接口相关参数
 * @Created Time:2017/11/23 14:57
 * @Update Time:
 */
public class StockChangeRes implements Serializable{

    /**
     * 仓库id
     **/
    private Integer wmsId;
    /**
     * 部门id
     **/
    private Integer deptId;
    /**
     * 产品sku
     **/
    private String sku;
    /**
     * 返回码
     **/
    private Boolean code;

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Boolean getCode() {
        return code;
    }

    public void setCode(Boolean code) {
        this.code = code;
    }
}
