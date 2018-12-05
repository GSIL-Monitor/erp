package com.stosz.plat.wms.bean;


/**
 * 订单取消请求
 * @author lb
 *
 */
public class CancelOrderRequest {
	
	/**
	 * 单号
	 */
	private String orderCode;
	/**
	 * 取消订单类型
	 * JYCK:销售订单
	 * XTRK:销售退货
	 * CGRK:采购订单
	 * CGTH:采购退货
	 * TRAN:调拨计划
	 */
	private String orderType;
	/*
	 * 货主
	 */
	private String goodsOwner;	
	/**
	 * 备注
	 */
	private String remark;
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}

}
