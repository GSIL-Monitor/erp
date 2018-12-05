package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.util.List;

public class ProductSkuAttributeValueRel extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;
	@DBColumn
	private String sku;
	@DBColumn
	private Integer productId;
	@DBColumn
	private Integer attributeId;
	@DBColumn
	private Integer attributeValueId;
	@DBColumn
	private Integer productAttributeId;
	
	private String attributeTitle;
	private String attributeValueTitle;
	private List<Integer> attributeValueIds;
	private Integer countAttribute;

	public String getAttributeTitle() {
		return attributeTitle;
	}

	public void setAttributeTitle(String attributeTitle) {
		this.attributeTitle = attributeTitle;
	}

	public String getAttributeValueTitle() {
		return attributeValueTitle;
	}

	public void setAttributeValueTitle(String attributeValueTitle) {
		this.attributeValueTitle = attributeValueTitle;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.trim();
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public Integer getAttributeValueId() {
		return attributeValueId;
	}

	public void setAttributeValueId(Integer attributeValueId) {
		this.attributeValueId = attributeValueId;
	}

	@Override
	public String getTable() {
		return "product_sku_attribute_value_rel";
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<Integer> getAttributeValueIds() {
		return attributeValueIds;
	}

	public void setAttributeValueIds(List<Integer> attributeValueIds) {
		this.attributeValueIds = attributeValueIds;
	}

	public Integer getCountAttribute() {
		return countAttribute;
	}

	public void setCountAttribute(Integer countAttribute) {
		this.countAttribute = countAttribute;
	}

	public Integer getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttributeId(Integer productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

}