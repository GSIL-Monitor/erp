package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 币种三字码
 * @author xiepengcheng
 * @version [1.0,2017年12月5日]
 */
public enum MoneyTypeEnum implements IEnum {
	MONEY_TYPE_SAUDI("SAR","沙特"),MONEY_TYPE_AED("AED","阿联酋");

	private String code;
	private String name;

	MoneyTypeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String display() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
