package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

public class OldErpProductSku extends AbstractParamEntity implements
		Serializable, ITable {

	private static final long serialVersionUID = -1889461486362216145L;
	@DBColumn
	private Integer idProductSku;
	@DBColumn
	private Integer idProduct;
	@DBColumn
	private String sku;
	@DBColumn
	private String model;
	@DBColumn
	private String barcode;
	@DBColumn
	private String optionValue;
	@DBColumn
	private Boolean status;
	@DBColumn
	private String title;
	@DBColumn
	private String idDepartment;
	@DBColumn
	private String purchasePrice;
	@DBColumn
	private String weight;
	@DBColumn
	private String oldsku;

	private String attributeTitle;
	private String attributeValueTitle;
	private Integer attributeId;
	private Integer attributeValueId;

	public Integer getIdProductSku() {
		return idProductSku;
	}

	public void setIdProductSku(Integer idProductSku) {
		this.idProductSku = idProductSku;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdDepartment() {
		return idDepartment;
	}

	public void setIdDepartment(String idDepartment) {
		this.idDepartment = idDepartment;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getOldsku() {
		return oldsku;
	}

	public void setOldsku(String oldsku) {
		this.oldsku = oldsku;
	}

	@Override
	public String getTable() {
		return "erp_product_sku";
	}

	@Override
	public Integer getId() {
		return idProductSku;
	}

	@Override
	public void setId(Integer id) {
		this.idProductSku = id;
	}

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

}
