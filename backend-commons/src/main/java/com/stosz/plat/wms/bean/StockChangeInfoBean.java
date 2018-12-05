package com.stosz.plat.wms.bean;


/**
 * 库存异动信息同步
 * @author Administrator
 *
 */
public class StockChangeInfoBean {

	private String orderCode;
	/*
	 * 模块类型
	 */
	private String moduleCode;
	/*
	 *商品编码 	
	 */
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}
