package com.stosz.plat.wms.bean;


/**
 * 订单取消请求
 * @author lb
 *
 */
public class OrderCancelRequest {
	
	/**
	 * 单号
	 */
	private String orderId;
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
	private String memo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}

}
