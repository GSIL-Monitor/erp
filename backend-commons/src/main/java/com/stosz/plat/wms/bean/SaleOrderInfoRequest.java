package com.stosz.plat.wms.bean;


/**
 * 销售订单查询请求
 * @author lb
 *
 */
public class SaleOrderInfoRequest {
	/*
	 * 销售订单号
	 */
	private String orderCode;
	/*
	 * 物流公司编码
	 */
	private String logisticsCompanyCode;
	/*
	 * 物流单号
	 */
	private String expressNo;
	/*
	 * 店铺名称
	 */
	private String shopName;
	/*
	 * 开始时间
	 */
	private String beginDate;
	/*
	 * 结束时间
	 */
	private String endDate;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getLogisticsCompanyCode() {
		return logisticsCompanyCode;
	}
	public void setLogisticsCompanyCode(String logisticsCompanyCode) {
		this.logisticsCompanyCode = logisticsCompanyCode;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
