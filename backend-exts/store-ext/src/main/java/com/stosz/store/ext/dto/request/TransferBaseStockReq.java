package com.stosz.store.ext.dto.request;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:17
 */
public class TransferBaseStockReq extends TransferItemReq implements Serializable {

    private Integer wmsId;//仓库id
    private Integer inDeptId;//调入部门id
    private String inDeptNo;
    private String inDeptName;
    private Integer outDeptId;//调出部门id
    private String outDeptNo;
    private String outDeptName;
    private String spu;

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Integer getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(Integer inDeptId) {
        this.inDeptId = inDeptId;
    }

    public String getInDeptNo() {
        return inDeptNo;
    }

    public void setInDeptNo(String inDeptNo) {
        this.inDeptNo = inDeptNo;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        this.inDeptName = inDeptName;
    }

    public Integer getOutDeptId() {
        return outDeptId;
    }

    public void setOutDeptId(Integer outDeptId) {
        this.outDeptId = outDeptId;
    }

    public String getOutDeptNo() {
        return outDeptNo;
    }

    public void setOutDeptNo(String outDeptNo) {
        this.outDeptNo = outDeptNo;
    }

    public String getOutDeptName() {
        return outDeptName;
    }

    public void setOutDeptName(String outDeptName) {
        this.outDeptName = outDeptName;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }
}
