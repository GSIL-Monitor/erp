package com.stosz.store.ext.dto.response;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:仓库查询接口返回信息
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class StockRes implements Serializable {

    private Integer wmsId;//仓库id
    private Integer deptId;//部门id
    private String deptNo;//部门编号
    private String deptName;//部门名称
    private String spu;//部门spu
    private String sku;//部门sku
    private Integer instockQty;//库存数
    private Integer usableQty;//可用数
    private Integer occupyQty;//暂用数
    private Integer intransitQty;//在途数
    private Long version;//版本

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

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Integer getInstockQty() {
        return instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public Integer getUsableQty() {
        return usableQty;
    }

    public void setUsableQty(Integer usableQty) {
        this.usableQty = usableQty;
    }

    public Integer getOccupyQty() {
        return occupyQty;
    }

    public void setOccupyQty(Integer occupyQty) {
        this.occupyQty = occupyQty;
    }

    public Integer getIntransitQty() {
        return intransitQty;
    }

    public void setIntransitQty(Integer intransitQty) {
        this.intransitQty = intransitQty;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
