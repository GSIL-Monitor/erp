package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

public class ProductAttributeValueRel extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	@DBColumn
	private Integer productAttributeId;
	@DBColumn
	private Integer attributeValueId;
	@DBColumn
	private Integer productId;
	@DBColumn
	private Boolean usable;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private String creator;

	private String optionTitle;

	private String title;
	// 是否绑定的判断
	private Boolean bindIs;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getTable() {
		return "product_attribute_value_rel";
	}

	public Integer getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(Integer attributeValueId) {
		this.attributeValueId = attributeValueId;
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

	public Integer getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttributeId(Integer productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Boolean getUsable() {
		return usable == null ? true : usable ;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
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
		this.creator = creator;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getBindIs() {
		return bindIs;
	}

	public void setBindIs(Boolean bindIs) {
		this.bindIs = bindIs;
	}

}
