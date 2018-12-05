package com.stosz.purchase.ext.model;

import java.io.Serializable;
import java.util.List;

/**
 * 有库存部门
 *
 * @author minxiaolong
 * @create 2018-01-08 17:04
 **/
public class InStockDepartment implements Serializable{

    private static final long serialVersionUID = 1011584298021218539L;

    private String deptName;
    private List<SkuStockInfo> skuStockInfoList;

    public List<SkuStockInfo> getSkuStockInfoList() {
        return skuStockInfoList;
    }

    public void setSkuStockInfoList(List<SkuStockInfo> skuStockInfoList) {
        this.skuStockInfoList = skuStockInfoList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
