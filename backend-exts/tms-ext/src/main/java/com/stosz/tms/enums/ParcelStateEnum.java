package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

public enum ParcelStateEnum implements IEnum {

	CREATE(1, "新建"), ASSIGN_SUCCESS(2, "指派成功"), ASSIGN_FAIL(3, "指派失败"), PUSHORDER_SUCCSS(4, "推送订单成功"), PUSHORDER_FAIL(5, "推送订单失败"), CANCEL(6, "单据取消");

	private Integer type;

	private String name;

	ParcelStateEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	@Override
	public String display() {
		return name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
