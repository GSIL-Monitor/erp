package com.stosz.plat.wms.bean;


/**
 * 货主变更查询请求
 * @author Administrator
 *
 */
public class OwnerChangeInfoRequest {
	/*
	 * 货主变更单号
	 */
	private String orderCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
