package com.stosz.tms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stosz.plat.model.DBColumn;

import java.util.Date;

public class TrackStatusClassifyListVo {

    private Integer id;

    private Integer trackApiId;


    private String shippingStatus;

    private String classifyCode;

    private String classifyStatus;

    private Integer enable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateAt;

    private String creator;

    private Integer creatorId;

    private String trackApiName;

    private String modifier;

    private Integer modifierId;

    private Integer statusType;
    
    private String shippingStatusCode;

    public String getShippingStatusCode() {
		return shippingStatusCode;
	}

	public void setShippingStatusCode(String shippingStatusCode) {
		this.shippingStatusCode = shippingStatusCode;
	}

	public Integer getStatusType() {
        return statusType;
    }

    public void setStatusType(Integer statusType) {
        this.statusType = statusType;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrackApiId() {
        return trackApiId;
    }

    public void setTrackApiId(Integer trackApiId) {
        this.trackApiId = trackApiId;
    }

    public String getTrackApiName() {
        return trackApiName;
    }

    public void setTrackApiName(String trackApiName) {
        this.trackApiName = trackApiName;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}
