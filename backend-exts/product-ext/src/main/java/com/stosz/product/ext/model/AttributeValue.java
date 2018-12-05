package com.stosz.product.ext.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 属性值实体类
 * 
 * @author he_guitang
 *
 */
public class AttributeValue extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private String title;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private LocalDateTime updateAt;
	@DBColumn
	private Integer attributeId;
	@DBColumn
	private Integer version;

	// 是否绑定的判断
	private Boolean bindIs;

	//对应的关联表的id;
	@JsonIgnore
	private Integer productAttributeValueRelId;

	// 属性值语言
	private List<AttributeValueLang> attributeValueLangs;
	// 产品属性值关系表
	private Integer productId;
	private ProductAttributeValueRel productAttributeValueRel;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
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

	public void setUpdateAt(LocalDateTime createAt) {
		this.updateAt = createAt;
	}

	@Override
	public String getTable() {
		return "attribute_value";
	}

	public List<AttributeValueLang> getAttributeValueLangs() {
		return attributeValueLangs;
	}

	public void setAttributeValueLangs(List<AttributeValueLang> attributeValueLangs) {
		this.attributeValueLangs = attributeValueLangs;
	}

	public ProductAttributeValueRel getProductAttributeValueRel() {
		return productAttributeValueRel;
	}

	public void setProductAttributeValueRel(ProductAttributeValueRel productAttributeValueRel) {
		this.productAttributeValueRel = productAttributeValueRel;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Boolean getBindIs() {
		return bindIs;
	}

	public void setBindIs(Boolean bindIs) {
		this.bindIs = bindIs;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getProductAttributeValueRelId() {
		return productAttributeValueRelId;
	}

	public void setProductAttributeValueRelId(Integer productAttributeValueRelId) {
		this.productAttributeValueRelId = productAttributeValueRelId;
	}
}