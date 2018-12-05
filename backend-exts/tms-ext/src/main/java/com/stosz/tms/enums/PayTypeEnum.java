package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 支付方式
 * @author xiepengcheng
 * @version [1.0,2017年12月12日]
 */
public enum PayTypeEnum implements IEnum {
	sendpay(1,"寄方付"), receivepay(2,"收方付"),thirdpay(3,"第三方付");

	private Integer type;
	private String display = "";

	PayTypeEnum(Integer type, String display) {
		this.type = type;
		this.display = display;
	}

	@Override
	public String display() {
		return display;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
