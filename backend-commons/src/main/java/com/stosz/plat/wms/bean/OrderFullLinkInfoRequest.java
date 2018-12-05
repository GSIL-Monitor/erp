package com.stosz.plat.wms.bean;


/**
 * 订单全链路查询,开始时间,结束时间,订单,开始和结束时间不能超过1天
 * @author Administrator
 *
 */
public class OrderFullLinkInfoRequest {
	/*
	 *	订单号
	 */
	private String orderCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
