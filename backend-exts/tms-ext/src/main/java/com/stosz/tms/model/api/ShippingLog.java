package com.stosz.tms.model.api;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.util.Date;

public class ShippingLog extends AbstractParamEntity implements ITable {
	@DBColumn
	private Long id;
	
	@DBColumn
	private String orderNo;
	@DBColumn
	private Integer type;
	
	@DBColumn
	private String code;
	@DBColumn
	private String interfaceUrl;
	@DBColumn
	private Date executeTime;
	@DBColumn
	private String requestBody;
	@DBColumn
	private String responseBody;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public Integer getId() {
		return id == null ? null : id.intValue();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceurl) {
		this.interfaceUrl = interfaceurl == null ? null : interfaceurl.trim();
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	@Override
	public void setId(Integer id) {
		this.id = id.longValue();
	}

	@Override
	public String getTable() {
		return "shipping_log";
	}
}