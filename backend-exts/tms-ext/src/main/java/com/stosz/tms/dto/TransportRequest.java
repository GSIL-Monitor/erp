package com.stosz.tms.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.stosz.tms.enums.OrderTypeEnum;

public class TransportRequest implements Serializable {
	private static final long serialVersionUID = -4826842674674050013L;

	private Integer orderId;// 订单ID

	private String orderNo;// 订单号

	private BigDecimal orderAmount;// 订单金额

	private Integer goodsQty;// 商品数量

	private BigDecimal weight;// 重量

	private OrderLinkDto orderLinkDto;// 订单客户信息

	private List<TransportOrderDetail> orderDetails;

	private OrderTypeEnum orderTypeEnum;// 订单类型 订货订单 换货订单

	private Integer payState;// 支付状态（0：未支付，1：已支付）

	private Date orderDate;// 订单时间

	private String remark;// 订单备注

	private String serviceRemark;// 客服备注

	private String currencyCode;// 货币代码 CNY 人民币

	private Integer warehouseId;// 发货仓ID

	private Integer zoneId;// 区域ID

	// ---不需要订单传入---
	private Integer warehouseZoneId;// 仓库所在区域ID
	public String getServiceRemark() {
		return serviceRemark;
	}

	public void setServiceRemark(String serviceRemark) {
		this.serviceRemark = serviceRemark;
	}

	public Integer getWarehouseZoneId() {
		return warehouseZoneId;
	}

	public void setWarehouseZoneId(Integer warehouseZoneId) {
		this.warehouseZoneId = warehouseZoneId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public OrderLinkDto getOrderLinkDto() {
		return orderLinkDto;
	}

	public void setOrderLinkDto(OrderLinkDto orderLinkDto) {
		this.orderLinkDto = orderLinkDto;
	}

	public List<TransportOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<TransportOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderTypeEnum getOrderTypeEnum() {
		return orderTypeEnum;
	}

	public void setOrderTypeEnum(OrderTypeEnum orderTypeEnum) {
		this.orderTypeEnum = orderTypeEnum;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
