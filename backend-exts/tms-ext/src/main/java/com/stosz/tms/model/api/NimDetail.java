package com.stosz.tms.model.api;

import java.io.Serializable;

/**
 * Nim content 实体
 * @author xiepengcheng
 * @version [1.0,2017年12月6日]
 */
public class NimDetail implements Serializable{

	private String product_name;

	private String size_code;

	private String qty;//数量

	private String unit;

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getSize_code() {
		return size_code;
	}

	public void setSize_code(String size_code) {
		this.size_code = size_code;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}
