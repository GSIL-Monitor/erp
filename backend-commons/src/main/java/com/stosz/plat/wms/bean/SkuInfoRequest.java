package com.stosz.plat.wms.bean;


/**
 * 商品档案查询请求
 * @author lb
 *
 */
public class SkuInfoRequest {
	/*
	 * 商品货号	String	50	当时间为空时,货号和物料编码不能同时为空
	 */
	private String goodsCode;	
	/*
	 * 物料编码	String	50
	 */
	private String sku;
	
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}	

}
