package com.stosz.tms.model;

import java.util.Date;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class ShippingSchedule extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private Date scheduleDate;
	@DBColumn
	private Integer templateId;
	@DBColumn
	private Integer shippingWayId;
	@DBColumn
	private Integer wmsId;
	@DBColumn
	private Integer generalCargoNum;
	@DBColumn
	private Integer specialCargoNum;
	@DBColumn
	private Integer cargoNum;
	@DBColumn
	private Integer todayGeneralNum;
	@DBColumn
	private Integer todaySpecialNum;
	@DBColumn
	private Integer todayCargoNum;
	@DBColumn
	private Integer eachSpecialNum;
	@DBColumn
	private Integer eachGeneralNum;
	@DBColumn
	private Integer eachCargoNum;
	@DBColumn
	private Integer onceGeneralNum;
	@DBColumn
	private Integer onceSpecialNum;
	@DBColumn
	private Integer onceCargoNum;
	@DBColumn
	private Integer sorted;
	@DBColumn
	private Integer version;
	@DBColumn
	private Date createAt;
	@DBColumn
	private Date updateAt;
	@DBColumn
	private String creator;
	@DBColumn
	private Integer creatorId;

	@DBColumn
	private Integer enable;

	@DBColumn
	private String modifier;
	@DBColumn
	private Integer modifierId;
	
	@DBColumn
	private Integer eachCount;//轮询次数

	private int addNum;


	public Integer getEachCount() {
		return eachCount;
	}

	public void setEachCount(Integer eachCount) {
		this.eachCount = eachCount;
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

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public int getAddNum() {
		return addNum;
	}

	public void setAddNum(int addNum) {
		this.addNum = addNum;
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

	public Integer getShippingWayId() {
		return shippingWayId;
	}

	public void setShippingWayId(Integer shippingWayId) {
		this.shippingWayId = shippingWayId;
	}

	public Integer getWmsId() {
		return wmsId;
	}

	public void setWmsId(Integer wmsId) {
		this.wmsId = wmsId;
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
		// TODO Auto-generated method stub
		return "shipping_schedule";
	}
}