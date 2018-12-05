package com.stosz.tms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransportOrderDetail implements Serializable {

	private static final long serialVersionUID = -2046076785469232270L;
	
	private Long orderItemId;
	
	private String sku;//产品SKU
	
	private String productTitle;//产品标题

	private String productNameEN;//品名 英文

	private String productNameCN;//品名 中文

	private Integer orderDetailQty;// 数量

	private BigDecimal price;// 单价
	
	private BigDecimal totalAmount;//总价  数量*单价

	private String inlandHsCode;// 国内海关编码

	private String foreignHsCode;// 国外海关编码

	private String unit;

	private BigDecimal weight;//单个重量

	private BigDecimal totalWeight;//总重量

	public String getProductNameEN() {
		return productNameEN;
	}

	public void setProductNameEN(String productNameEN) {
		this.productNameEN = productNameEN;
	}

	public String getProductNameCN() {
		return productNameCN;
	}

	public void setProductNameCN(String productNameCN) {
		this.productNameCN = productNameCN;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getOrderDetailQty() {
		return orderDetailQty;
	}

	public void setOrderDetailQty(Integer orderDetailQty) {
		this.orderDetailQty = orderDetailQty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getInlandHsCode() {
		return inlandHsCode;
	}

	public void setInlandHsCode(String inlandHsCode) {
		this.inlandHsCode = inlandHsCode;
	}

	public String getForeignHsCode() {
		return foreignHsCode;
	}

	public void setForeignHsCode(String foreignHsCode) {
		this.foreignHsCode = foreignHsCode;
	}
	
	
	
}
