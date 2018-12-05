package com.stosz.plat.wms.bean;

//import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 商品档案实体bean
 * @author lb
 *
 */
//@XStreamAlias("items")
public class SkuInfoBean {
	
	/*
	 * 商品货号	String	50	√
	 */
	private String goodsCode;	
	/*
	 * 商品名称	String	200	√
	 */
	private String goodsName;	
	/*
	 * 商品简称	String	50
	 */
	private String goodsAbbreviation;	
	/*
	 *品牌代码	String	50 
	 */
	private String brandCode;	
	/*
	 * 品牌名称	String	50
	 */
	private String brandName;
	/*
	 * 类别代码	String	50
	 */
	private String categoryCode;
	/*
	 * 类别名称	String	50
	 */
	private String categoryName;
	/**
	
	 *商品属性(默认P)
		P：正常商品
		G：赠品
		S：饰品
		M：辅料
		C:耗材
		B：包装
		X：虚拟物品
		L：残次品
		I:发票	
	 */
	private String goodsType;
	/**
	 * sku	String	50
	 */
	private String sku;	
	/**
	 * 	价格	double	18,4
	 */
	private String price;
	/**
	 * 	供应商代码	String	50
	 */
	private String supplierCode;
	/**
	 * 	规格名称	String	200
	 */
	private String standard;
	/**
	 * 	条形码	String	50
	 */
	private String brcode;
	/**
	 * 	颜色	String	50
	 */
	private String color;
	/**
	 * 	尺码	String	50
	 */
	private String size;
	/**
	 * 	长	String	50
	 */
	private String length;
	/**
	 * 	宽	String	50
	 */
	private String wide;
	/**
	 * 	高	String	50
	 */
	private String high;
	/**
	 * 	体积	String	50
	 */
	private String volume;
	/**
	 * 	更新时间	String	50
	 */
	private String updateDate;
	/**
	 * 	状态(默认有效)1:有效0:无效	String	10remark	备注	String	500
	 */
	private String state;
	/**
	 * 	货主编码
	 */
	private String goodsOwner;
	/**
	 * 	毛重
	 */
	private String grossWeight;
	/**
	 * 	净重
	 */
	private String netWeight;
	/**
	 * 	皮重
	 */
	private String tareWeight;
	/**
	 * 	商品单位
	 */
	private String unit;
	/**
	 * 	备注	String	500
	 * @return
	 */
	private String remark;
	/**
	 * 备用字段2
	 */
	private String gwf2;
	/**
	 * 备用字段3
	 */
	private String gwf3;
	/**
	 * 备用字段4
	 */
	private String gwf4;
	/**
	 * 备用字段5
	 */
	private String gwf5;
	/**
	 * 备用字段6
	 */
	private String gwf6;
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsAbbreviation() {
		return goodsAbbreviation;
	}
	public void setGoodsAbbreviation(String goodsAbbreviation) {
		this.goodsAbbreviation = goodsAbbreviation;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getBrcode() {
		return brcode;
	}
	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWide() {
		return wide;
	}
	public void setWide(String wide) {
		this.wide = wide;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(String tareWeight) {
		this.tareWeight = tareWeight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGwf2() {
		return gwf2;
	}
	public void setGwf2(String gwf2) {
		this.gwf2 = gwf2;
	}
	public String getGwf3() {
		return gwf3;
	}
	public void setGwf3(String gwf3) {
		this.gwf3 = gwf3;
	}
	public String getGwf4() {
		return gwf4;
	}
	public void setGwf4(String gwf4) {
		this.gwf4 = gwf4;
	}
	public String getGwf5() {
		return gwf5;
	}
	public void setGwf5(String gwf5) {
		this.gwf5 = gwf5;
	}
	public String getGwf6() {
		return gwf6;
	}
	public void setGwf6(String gwf6) {
		this.gwf6 = gwf6;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
