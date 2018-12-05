package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

public class AttributeValueLang extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private String name;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private LocalDateTime updateAt;
	@DBColumn
	private Integer attributeValueId;
	@DBColumn
	private String langCode;

	private String langName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(Integer attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode == null ? null : langCode.trim();
	}

	@Override
	public String getTable() {

		return "attribute_value_lang";
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

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;

	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

}