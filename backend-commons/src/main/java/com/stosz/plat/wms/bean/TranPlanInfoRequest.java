package com.stosz.plat.wms.bean;


/**
 * 调拨计划查询请求
 * @author Administrator
 *
 */
public class TranPlanInfoRequest {
	/*
	 * 调拨计划单号
	 */
	private String orderCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
