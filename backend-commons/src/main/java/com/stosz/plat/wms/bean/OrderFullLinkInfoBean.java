package com.stosz.plat.wms.bean;
/**
 *  订单全链路查询,开始时间,结束时间,订单,开始和结束时间不能超过1天
 * @author lb
 *
 */
public class OrderFullLinkInfoBean {
	/*
	 *	销售订单号
	 */
	private String orderCode;
	/*
	 *	节点编码
	 */
	private String pointCode;//
	/*
	 *	节点名称
	 */
	private String pointName;//	
	/*
	 *	来源单号
	 */
	private String socoCode;
	/*
	 *	店铺编码
	 */
	private String shopCode;
	/*
	 *	店铺名称
	 */
	private String shopName;
	/*
	 *	创建人编码
	 */
	private String createUserCode;	
	/*
	 *	创建人名称
	 */
	private String createUserName;
	/*
	 *	创建时间
	 */
	private String createTime;
	/*
	 *	物流公司编码
	 */
	private String logisticsCompanyCode;
	/*
	 *	快递单号
	 */
	private String expressNo;
	/*
	 *	备注
	 */
	private String remark;
	/*
	 * 备用字段1
	 */
	private String gwf1;
	/*
	 * 备用字段2
	 */
	private String gwf2;
	/*
	 * 备用字段3
	 */
	private String gwf3;
	/*
	 * 备用字段4
	 */
	private String gwf4;
	/*
	 * 备用字段5
	 */
	private String gwf5;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getPointCode() {
		return pointCode;
	}
	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getSocoCode() {
		return socoCode;
	}
	public void setSocoCode(String socoCode) {
		this.socoCode = socoCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLogisticsCompanyCode() {
		return logisticsCompanyCode;
	}
	public void setLogisticsCompanyCode(String logisticsCompanyCode) {
		this.logisticsCompanyCode = logisticsCompanyCode;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	

}
