package com.stosz.plat.wms.bean;


/**
 * 库存异动查询明细信息实体bean
 * @author Administrator
 *
 */
public class StockChangeDetailInfoBean {
	/*
	 * sku
	 */
	private String sku;
	/*
	 * 数量
	 */
	private String qty;
	
	/**
	 * 备用字段1
	 */
	private String gwf1;
	/**
	 * 备用字段2
	 */
	private String gwf2;
	/**备用字段3
	 * 
	 */
	private String gwf3;
	/**
	 * 备用字段4
	 */
	private String gwf4;
	/**备用字段5
	 * 
	 */
	private String gwf5;
	
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

}
