package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 国家二字码
 * @author xiepengcheng
 * @version [1.0,2017年12月5日]
 */
public enum ContryCodeEnum implements IEnum {
	EGYPT("EG","埃及"), KUWAIT("KW","科威特"),BAHRAIN("BH","巴林"),SAUDI("SA","沙特"),
	OMAN("OM","阿曼"),IRAN("IR","伊朗"),QATAR("QA","卡塔尔"),ARAB("AE","阿联酋");

	private String code;
	private String name;

	ContryCodeEnum(String code,String name) {
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
