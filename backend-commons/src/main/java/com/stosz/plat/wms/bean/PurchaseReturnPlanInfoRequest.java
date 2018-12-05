package com.stosz.plat.wms.bean;


/**
 * 采购退货计划查询请求
 * @author Administrator
 *
 */
public class PurchaseReturnPlanInfoRequest {
	/*
	 * 采购退货计划单号
	 */
	private String orderCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
