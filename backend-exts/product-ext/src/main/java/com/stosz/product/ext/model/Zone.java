package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class Zone extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private Integer parentId;
	// private Zone zone;
	@DBColumn
	private Integer countryId;
	@DBColumn
	private String code;
	@DBColumn
	private String title;
	@DBColumn
	private String currency;
	@DBColumn
	private Integer sort;
	private String countryName;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String getTable() {
		return "zone";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	// public Zone getZone() {
	// return zone;
	// }
	//
	// public void setZone(Zone zone) {
	// this.zone = zone;
	// }


	public Integer getSort() {
		return sort == null ? 100 : sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
