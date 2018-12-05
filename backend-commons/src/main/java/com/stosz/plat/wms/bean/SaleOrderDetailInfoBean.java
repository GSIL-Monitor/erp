package com.stosz.plat.wms.bean;



/**
 * 销售订单明细信息实体bean
 * @author lb
 *
 */
public class SaleOrderDetailInfoBean {
	/*
	 * 	1.SKU编码
	 */
	private String sku;
	/*
	 * 	2.数量
	 */
	private String qty;
	/*
	 * 	3.单价
	 */
	private String price;
	/*
	 * 	4.平台SKU编码
	 */
	private String nsku;
	/*
	 * 	5.实际金额
	 */
	private String actualAmount;
	/*
	 *  6.优惠金额
	 */
	private String discountAmount;
	/*
	 * 	7.应付金额
	 */
	private String amountPayable;
	/*
	 * 	8.折扣
	 */
	private String discount;
	/*
	 * 9.商品生产日期 YYYY-MM-DD
	 */
	private String productDate;
	/*
	 * 10.商品过期日期YYYY-MM-DD
	 */
	private String expireDate;
	/*
	 * 11.生产批号
	 */
	private String produceCode;
	/*
	 * 12.批次编码
	 */
	private String batchCode;
	/*
	 * 13.备用字段1
	 */
	private String gwf1;
	/*
	 * 14.备用字段2
	 */
	private String gwf2;
	/*
	 * 15.备用字段3
	 */
	private String gwf3;
	/*
	 * 16.备用字段4
	 */
	private String gwf4;
	/*
	 * 17.备用字段5
	 */
	private String gwf5;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNsku() {
		return nsku;
	}
	public void setNsku(String nsku) {
		this.nsku = nsku;
	}
	public String getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(String amountPayable) {
		this.amountPayable = amountPayable;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getGwf1() {
		return gwf1;
	}
	public void setGwf1(String gwf1) {
		this.gwf1 = gwf1;
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
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getProduceCode() {
		return produceCode;
	}
	public void setProduceCode(String produceCode) {
		this.produceCode = produceCode;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

}
