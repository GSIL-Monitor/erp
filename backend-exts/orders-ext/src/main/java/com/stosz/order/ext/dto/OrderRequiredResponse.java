package com.stosz.order.ext.dto;

import java.io.Serializable;

public class OrderRequiredResponse implements Serializable{

	private Integer orderRequiredQty;// 订单需求数    === 待采购物品数

	private Integer avgSaleQty;// 3日平均交易量      ===  昨天，前天，大前天   todo 后面根据上线时间细化；

	private Integer pendingOrderQty;// 待审单数       === 待审物品数量

	public Integer getOrderRequiredQty() {
		return orderRequiredQty;
	}

	public void setOrderRequiredQty(Integer orderRequiredQty) {
		this.orderRequiredQty = orderRequiredQty;
	}

	public Integer getAvgSaleQty() {
		return avgSaleQty;
	}

	public void setAvgSaleQty(Integer avgSaleQty) {
		this.avgSaleQty = avgSaleQty;
	}

	public Integer getPendingOrderQty() {
		return pendingOrderQty;
	}

	public void setPendingOrderQty(Integer pendingOrderQty) {
		this.pendingOrderQty = pendingOrderQty;
	}

	
	
}
