package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

public class Language extends AbstractParamEntity implements ITable {

	@DBColumn
	private Integer id;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private LocalDateTime updateAt;
	@DBColumn
	private String name;
	@DBColumn
	private String langCode;
	@DBColumn
	private Integer sort;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	@Override
	public String getTable() {
		return "language";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getSort() {
		return sort == null ? 100 : sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
