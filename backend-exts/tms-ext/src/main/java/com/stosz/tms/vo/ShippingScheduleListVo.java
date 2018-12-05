package com.stosz.tms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stosz.plat.model.DBColumn;

import java.util.Date;

public class ShippingScheduleListVo {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date scheduleDate;
   
    private Integer templateId;
   
    private Integer shippingWayId;

    private String shippingWayName;

    private Integer wmsId;

    private String wmsName;

    private Integer generalCargoNum;
   
    private Integer specialCargoNum;
   
    private Integer cargoNum;
   
    private Integer todayGeneralNum;
   
    private Integer todaySpecialNum;
   
    private Integer todayCargoNum;
   
    private Integer eachSpecialNum;
   
    private Integer eachGeneralNum;
   
    private Integer eachCargoNum;
   
    private Integer onceGeneralNum;
   
    private Integer onceSpecialNum;
   
    private Integer onceCargoNum;
   
    private Integer sorted;
   
    private Integer version;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createAt;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateAt;
   
    private String creator;
   
    private Integer creatorId;

    private Integer enable;
    
    private Integer eachCount;
    
    


    public Integer getEachCount() {
		return eachCount;
	}

	public void setEachCount(Integer eachCount) {
		this.eachCount = eachCount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getShippingWayId() {
        return shippingWayId;
    }

    public void setShippingWayId(Integer shippingWayId) {
        this.shippingWayId = shippingWayId;
    }

    public String getShippingWayName() {
        return shippingWayName;
    }

    public void setShippingWayName(String shippingWayName) {
        this.shippingWayName = shippingWayName;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public Integer getGeneralCargoNum() {
        return generalCargoNum;
    }

    public void setGeneralCargoNum(Integer generalCargoNum) {
        this.generalCargoNum = generalCargoNum;
    }

    public Integer getSpecialCargoNum() {
        return specialCargoNum;
    }

    public void setSpecialCargoNum(Integer specialCargoNum) {
        this.specialCargoNum = specialCargoNum;
    }

    public Integer getCargoNum() {
        return cargoNum;
    }

    public void setCargoNum(Integer cargoNum) {
        this.cargoNum = cargoNum;
    }

    public Integer getTodayGeneralNum() {
        return todayGeneralNum;
    }

    public void setTodayGeneralNum(Integer todayGeneralNum) {
        this.todayGeneralNum = todayGeneralNum;
    }

    public Integer getTodaySpecialNum() {
        return todaySpecialNum;
    }

    public void setTodaySpecialNum(Integer todaySpecialNum) {
        this.todaySpecialNum = todaySpecialNum;
    }

    public Integer getTodayCargoNum() {
        return todayCargoNum;
    }

    public void setTodayCargoNum(Integer todayCargoNum) {
        this.todayCargoNum = todayCargoNum;
    }

    public Integer getEachSpecialNum() {
        return eachSpecialNum;
    }

    public void setEachSpecialNum(Integer eachSpecialNum) {
        this.eachSpecialNum = eachSpecialNum;
    }

    public Integer getEachGeneralNum() {
        return eachGeneralNum;
    }

    public void setEachGeneralNum(Integer eachGeneralNum) {
        this.eachGeneralNum = eachGeneralNum;
    }

    public Integer getEachCargoNum() {
        return eachCargoNum;
    }

    public void setEachCargoNum(Integer eachCargoNum) {
        this.eachCargoNum = eachCargoNum;
    }

    public Integer getOnceGeneralNum() {
        return onceGeneralNum;
    }

    public void setOnceGeneralNum(Integer onceGeneralNum) {
        this.onceGeneralNum = onceGeneralNum;
    }

    public Integer getOnceSpecialNum() {
        return onceSpecialNum;
    }

    public void setOnceSpecialNum(Integer onceSpecialNum) {
        this.onceSpecialNum = onceSpecialNum;
    }

    public Integer getOnceCargoNum() {
        return onceCargoNum;
    }

    public void setOnceCargoNum(Integer onceCargoNum) {
        this.onceCargoNum = onceCargoNum;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
