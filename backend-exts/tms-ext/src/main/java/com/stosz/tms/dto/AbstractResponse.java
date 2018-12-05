package com.stosz.tms.dto;

import java.io.Serializable;

public abstract class AbstractResponse implements Serializable{

	private static final long serialVersionUID = 2576121982465661438L;

	public static final String SUCCESS = "success";

	public static final String FAIL = "fail";

	protected String code;

	protected String errorMsg;

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

	public AbstractResponse fail(String errorMsg) {
		this.code = FAIL;
		this.errorMsg = errorMsg;
		return this;
	}

}
