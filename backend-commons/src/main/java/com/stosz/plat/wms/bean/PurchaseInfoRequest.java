package com.stosz.plat.wms.bean;


/**
 * 采购订单查询请求
 * @author Administrator
 *
 */
public class PurchaseInfoRequest {
	/*
	 * 采购单号
	 */
	private String orderCode;
	/*
	 * 供应商编码
	 */
	private String supplierCode;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	

}
