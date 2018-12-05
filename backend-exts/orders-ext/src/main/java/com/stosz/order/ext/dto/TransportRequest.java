package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.util.HashMap;

public class TransportRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer shippingWayId;// 物流方式ID

	private String trackingNo;// 运单号
	
	private String shippingCode;// 物流别名的Code 如果是特货 返回 物流别名(特货)code 普货返回 物流别名(普货)Code

	private String shippingName;// 物流别名

	private Integer orderId;//订单号

	private  String code;
	
	private HashMap<String, Object> sheetDataMap;// 物流面单Map
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static final String SUCCESS = "success";
	
	public static final String SUCCESS_AFTER_NOTIFY = "success_after_notify";// 推送订单到物流承运商后，通知订单(订单需要接收到异步回调后才能推单给WMS)

	public static final String FAIL = "fail";
	

	public HashMap<String, Object> getSheetDataMap() {
		return sheetDataMap;
	}

	public void setSheetDataMap(HashMap<String, Object> sheetDataMap) {
		this.sheetDataMap = sheetDataMap;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
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
}
