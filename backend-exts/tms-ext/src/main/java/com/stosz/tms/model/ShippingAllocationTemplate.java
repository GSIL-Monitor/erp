package com.stosz.tms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class ShippingAllocationTemplate extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private Integer shippingWayId;
	@DBColumn
	private Integer generalCargoNum;
	@DBColumn
	private Integer specialCargoNum;
	@DBColumn
	private Integer cargoNum;
	@DBColumn
	private Integer wmsId;
	@DBColumn
	private Integer sorted;
	@DBColumn
	private Integer eachSpecialNum;
	@DBColumn
	private Integer eachGeneralNum;
	@DBColumn
	private Integer eachCargoNum;

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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShippingWayId() {
		return shippingWayId;
	}

	public void setShippingWayId(Integer shippingWayId) {
		this.shippingWayId = shippingWayId;
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

	public Integer getWmsId() {
		return wmsId;
	}

	public void setWmsId(Integer wmsId) {
		this.wmsId = wmsId;
	}

	public Integer getSorted() {
		return sorted;
	}

	public void setSorted(Integer sorted) {
		this.sorted = sorted;
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
		return "shipping_allocation_template";
	}
}