package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.tms.enums.ClassifyEnum;

import java.io.Serializable;
import java.util.Date;

public class TransportDTO implements Serializable {

	public static final String  MESSAGE_TYPE = "logisticsStatusNotify";

	private Integer orderId;//订单号

	private Integer shippingWayId;// 物流方式ID

	private String trackingNo;// 运单号
	
	private OrderEventEnum orderEventEnum;//订单操作事件

	private ClassifyEnum classifyEnum;//归类状态code


	private Date classifyStatusTime; //归类状态时间


	public TransportDTO(){
	}


	public TransportDTO(Integer orderId, Integer shippingWayId, String trackingNo, OrderEventEnum orderEventEnum, ClassifyEnum classifyEnum, Date classifyStatusTime) {
		this.orderId = orderId;
		this.shippingWayId = shippingWayId;
		this.trackingNo = trackingNo;
		this.orderEventEnum = orderEventEnum;
		this.classifyEnum = classifyEnum;
		this.classifyStatusTime = classifyStatusTime;
	}


	public ClassifyEnum getClassifyEnum() {
		return classifyEnum;
	}

	public void setClassifyEnum(ClassifyEnum classifyEnum) {
		this.classifyEnum = classifyEnum;
	}

	public Date getClassifyStatusTime() {
		return classifyStatusTime;
	}

	public void setClassifyStatusTime(Date classifyStatusTime) {
		this.classifyStatusTime = classifyStatusTime;
	}

	public OrderEventEnum getOrderEventEnum() {
		return orderEventEnum;
	}

	public void setOrderEventEnum(OrderEventEnum orderEventEnum) {
		this.orderEventEnum = orderEventEnum;
	}


	public Integer getShippingWayId() {
		return shippingWayId;
	}

	public void setShippingWayId(Integer shippingWayId) {
		this.shippingWayId = shippingWayId;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public static void main(String[] args) {
//		TransportDTO transportDTO = new  TransportDTO(1,25,"2123","顺丰速运","顺丰速运",OrderEventEnum.matchSign,"顺利送达",new BigDecimal("2.3"));
//		RabbitMQMessage rabbitMQMessage = new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setMessageType(MESSAGE_TYPE).setData(transportDTO);
//		logger.info(JsonUtils.toJson(rabbitMQMessage));
	}
}
