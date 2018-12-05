package com.stosz.tms.model;

import com.stosz.tms.dto.AbstractResponse;
import com.stosz.tms.enums.AllowedProductTypeEnum;

public class AssignTransportResponse extends AbstractResponse {

	public static final String SUCCESS_AFTER_NOTIFY = "success_after_notify";// 推送订单到物流承运商后，通知订单(订单需要接收到异步回调后才能推单给WMS)
	// success 不需要推送订单到物流承运商(没有异步回调，获取到此状态可以直接通知WMS)

	private Integer shippingWayId;// 物流方式ID

	private Integer scheduleId;// 排程ID

	private String trackingNo;// 运单号

	private String shippingCode;// 物流别名的Code 如果是特货 返回 物流别名(特货)code 普货返回 物流别名(普货)Code

	private String shippingName;// 物流别名
	
	private String logisticsCode;//物流商Code
	
	private AllowedProductTypeEnum productType;//产品类型 0 所有 1 普货  2 特货


	public AllowedProductTypeEnum getProductType() {
		return productType;
	}

	public void setProductType(AllowedProductTypeEnum productType) {
		this.productType = productType;
	}

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
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

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public AssignTransportResponse fail(String errorMsg) {
		this.code = FAIL;
		this.errorMsg = errorMsg;
		return this;
	}


}
