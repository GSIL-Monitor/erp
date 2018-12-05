package com.stosz.plat.wms.bean;


/**
 * 销售退货计划单查询请求
 * @author Administrator
 *
 */
public class SaleReturnPlanInfoRequest {
	/*
	 * 销售退货计划单号
	 */
	private String orderCode;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
