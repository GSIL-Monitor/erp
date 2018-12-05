package com.stosz.order.ext.dto;

import java.io.Serializable;

public class TransportResponse implements Serializable {

	public static final String SUCCESS = "success";

	public static final String FAIL = "fail";

	private String code;

	private String errorMsg;// 错误消息

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	public  TransportResponse fail(String errorMsg) {
		this.code=FAIL;
		this.errorMsg=errorMsg;
		return this;
	}
	
	

}
