package com.stosz.tms.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.stosz.tms.enums.AllowedProductTypeEnum;
import com.stosz.tms.enums.OrderTypeEnum;

public class TransportOrderRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orderNo;// 订单号

	private BigDecimal orderAmount;// 订单金额

	private Integer goodsQty;// 商品数量

	private Double weight;// 重量

	private String unit;// 重量单位

	private OrderLinkDto orderLinkDto;// 订单客户信息

	private List<TransportOrderDetail> orderDetails;

	private OrderTypeEnum orderTypeEnum;// 订单类型 订货订单 换货订单

	private Integer payState;// 支付状态（0：未支付，1：已支付）

	private AllowedProductTypeEnum productType;// 包裹类型 普货 特货

	private Date orderDate;// 订单时间

	private String remark;// 备注

	private String currencyCode;// 货币代码 CNY 人民币

	private String trackNo;// 运单号

	private Date sendstarttime; // 物流公司上门取货开始时间

	private Date sendendtime;// 物流公司上门取货结束时间

	private Integer areaId;//区域id

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Date getSendstarttime() {
		return sendstarttime;
	}

	public void setSendstarttime(Date sendstarttime) {
		this.sendstarttime = sendstarttime;
	}

	public Date getSendendtime() {
		return sendendtime;
	}

	public void setSendendtime(Date sendendtime) {
		this.sendendtime = sendendtime;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public AllowedProductTypeEnum getProductType() {
		return productType;
	}

	public void setProductType(AllowedProductTypeEnum productType) {
		this.productType = productType;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public OrderTypeEnum getOrderTypeEnum() {
		return orderTypeEnum;
	}

	public void setOrderTypeEnum(OrderTypeEnum orderTypeEnum) {
		this.orderTypeEnum = orderTypeEnum;
	}

	public OrderLinkDto getOrderLinkDto() {
		return orderLinkDto;
	}

	public void setOrderLinkDto(OrderLinkDto orderLinkDto) {
		this.orderLinkDto = orderLinkDto;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public List<TransportOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<TransportOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

}
