package com.stosz.tms.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stosz.plat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class TimesDetail {
	public static final Logger logger = LoggerFactory.getLogger(TimesDetail.class);
	private String sku;/// 货品SKU
	private String categoryId;// 货品分类编号
	private String categoryName;// 货品分类名称
	private String description;// 品名
	private String brand;// 牌子. 如包裹类型为手机及平板计算机, 此项必填
	private String model;// 型号. 如包裹类型为手机及平板计算机, 此项必填
	private Integer pieces;// 单项SKU件数
	private BigDecimal unitPrice;// 单项SKU单价
	private String unitPriceCurrency;// 货币单位, 使用ISO 4217标准
	private BigDecimal CODValue;// 单项SKU COD货价(件数*COD单价). 如付款方式为COD, 此项必填. 使用当地货币
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getPieces() {
		return pieces;
	}
	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getUnitPriceCurrency() {
		return unitPriceCurrency;
	}
	public void setUnitPriceCurrency(String unitPriceCurrency) {
		this.unitPriceCurrency = unitPriceCurrency;
	}
	
	@JsonProperty(value="CODValue")
	public BigDecimal getCODValue() {
		return CODValue;
	}
	public void setCODValue(BigDecimal cODValue) {
		CODValue = cODValue;
	}
	
	public static void main(String[] args) {
		TimesDetail timesDetail=new TimesDetail();
		timesDetail.setCODValue(new BigDecimal(100));
		timesDetail.setBrand("1");
		logger.info(JsonUtils.toJson(timesDetail));
	}
}
