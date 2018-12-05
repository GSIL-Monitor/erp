package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

public class CategoryAttributeRel extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@DBColumn
	private Integer categoryId;
	@DBColumn
	private Integer attributeId;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String creator;
	// 品类与属性的解绑和绑定的前端标记
	private String bindingSign;
	// 是否绑定的判断
	private Boolean bindIs;

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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	@Override
	public String getTable() {
		return "category_attribute_rel";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getBindingSign() {
		return bindingSign;
	}

	public void setBindingSign(String bindingSign) {
		this.bindingSign = bindingSign;
	}

	public Boolean getBindIs() {
		return bindIs;
	}

	public void setBindIs(Boolean bindIs) {
		this.bindIs = bindIs;
	}

}