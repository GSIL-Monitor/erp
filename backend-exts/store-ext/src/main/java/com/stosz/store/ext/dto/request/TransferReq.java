package com.stosz.store.ext.dto.request;

import java.io.Serializable;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:17
 */
public class TransferReq implements Serializable {

    private Integer outWmsId;//调出仓库id
    private Integer inWmsId;//调入仓库id
    private String outWmsName;//调出仓库名
    private String inWmsName;//调出仓库名
    private Integer outType;//调出仓库类型
    private Integer inType;//调入仓库类型
    private Integer inDeptId;//调入部门id
    private String inDeptName;//调入部门名
    private Integer outDeptId;//调出部门id
    private String outDeptName;//调出部门名
    private String trackingNo;//运单号,转寄仓调出记录运单号或者sku加数量组合
    private String description;//备注

    public Integer getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(Integer inDeptId) {
        this.inDeptId = inDeptId;
    }

    public String getOutWmsName() {
        return outWmsName;
    }

    public void setOutWmsName(String outWmsName) {
        this.outWmsName = outWmsName;
    }

    public String getInWmsName() {
        return inWmsName;
    }

    public void setInWmsName(String inWmsName) {
        this.inWmsName = inWmsName;
    }

    public Integer getOutDeptId() {
        return outDeptId;
    }

    public void setOutDeptId(Integer outDeptId) {
        this.outDeptId = outDeptId;
    }

    public String getOutDeptName() {
        return outDeptName;
    }

    public void setOutDeptName(String outDeptName) {
        this.outDeptName = outDeptName;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        this.inDeptName = inDeptName;
    }

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }

    public Integer getOutWmsId() {
        return outWmsId;
    }

    public void setOutWmsId(Integer outWmsId) {
        this.outWmsId = outWmsId;
    }

    public Integer getInWmsId() {
        return inWmsId;
    }

    public void setInWmsId(Integer inWmsId) {
        this.inWmsId = inWmsId;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
