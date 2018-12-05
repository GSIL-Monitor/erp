package com.stosz.tms.dto;

import java.io.Serializable;

public class TransportOrderResponse extends AbstractResponse implements Serializable {

	private static final long serialVersionUID = -8684976365927715190L;

	private String trackingNo;

	private String responseData;
	
	private String shippingCode;

	public TransportOrderResponse() {
	}

	public TransportOrderResponse(boolean error) {
		if (error) {
			this.code = FAIL;
		}
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode;
	}

}
