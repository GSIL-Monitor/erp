package com.stosz.tms.model;

import java.util.Date;
import java.util.List;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class TrackStatusClassify extends AbstractParamEntity implements ITable {

    @DBColumn
    private Integer id;

    @DBColumn
    private Integer trackApiId;

    @DBColumn
    private String shippingStatus;

    @DBColumn
    private String classifyCode;

    @DBColumn
    private String classifyStatus;

    @DBColumn
    private Integer enable;

    @DBColumn
    private Date createAt;

    @DBColumn
    private Date updateAt;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    @DBColumn
    private String modifier;

    @DBColumn
    private Integer modifierId;

    @DBColumn
    private Integer statusType;
    
    @DBColumn
    private String shippingStatusCode;
    
    
    private String apiCode;


    private List<Integer> trackApiIdList;

    public List<Integer> getTrackApiIdList() {
        return trackApiIdList;
    }

    public void setTrackApiIdList(List<Integer> trackApiIdList) {
        this.trackApiIdList = trackApiIdList;
    }

    public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

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

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
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

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus == null ? null : shippingStatus.trim();
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus == null ? null : classifyStatus.trim();
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
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getTable() {
        return "tracking_status_classify";
    }
}