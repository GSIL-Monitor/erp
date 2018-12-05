package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 物流方式
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
public enum ShippingWayEnum implements IEnum {

	THAILANDBJX("THAILANDBJX", "泰国博佳图 "), //
	THAILAND_TIMES_B2C("THAILAND_TIMES_B2C", "泰国TimesB2C");//

	private String display = "";

	private String code = "";

	ShippingWayEnum(String code, String display) {
		this.code = code;
		this.display = display;
	}

	@Override
	public String display() {
		return display;
	}

	public String code() {
		return code;
	}

	public static ShippingWayEnum fromId(Integer id) {
		ShippingWayEnum orderTypeEnumFind = null;
		if (null == id || id.intValue() < 0)
			return orderTypeEnumFind;

		for (ShippingWayEnum orderTypeEnum : values()) {
			if (orderTypeEnum.ordinal() == id.intValue()) {
				orderTypeEnumFind = orderTypeEnum;
				break;
			}
		}
		return orderTypeEnumFind;

	}

}
