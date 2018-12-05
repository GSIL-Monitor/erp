package com.stosz.store.ext.dto.request;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 查询调拨请求参数
 * @Created Time:
 * @Update Time:2017/11/29 17:17
 */
public class SearchTransferReq extends AbstractParamEntity implements ITable, Serializable {

    private Integer id;  //调拨单id
    private Integer type;//调拨类型
    private String state;//调拨状态
    private Integer outWmsId;//调出仓库id
    private Integer inWmsId;//调入仓库id
    private LocalDateTime createStartTime; //创建起始时间
    private LocalDateTime createEndTime; //创建结束时间

    //权限
    private Integer deptId;
    private String jobAuthorityRel;
    private Integer userId;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getJobAuthorityRel() {
        return jobAuthorityRel;
    }

    public void setJobAuthorityRel(String jobAuthorityRel) {
        this.jobAuthorityRel = jobAuthorityRel;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public LocalDateTime getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(LocalDateTime createStartTime) {
        this.createStartTime = createStartTime;
    }

    public LocalDateTime getCreateEndTime() {
        // 若为日期，是否加1天
        return createEndTime;
    }

    public void setCreateEndTime(LocalDateTime createEndTime) {
        this.createEndTime = createEndTime;
    }

    @Override
    public String getTable() {
        return "transfer";
    }
}
