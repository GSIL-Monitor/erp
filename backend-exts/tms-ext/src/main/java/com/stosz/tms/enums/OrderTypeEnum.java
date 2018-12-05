package com.stosz.tms.enums;


import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 订单类型
 */
public enum OrderTypeEnum implements IEnum {
	normal("订货订单"), barter("换货订单"), getsth("代取订单");

	private String display = "";

	OrderTypeEnum(String display) {
		this.display = display;
	}

	@Override
	public String display() {
		return display;
	}

	public static OrderTypeEnum fromId(Integer id) {
		OrderTypeEnum orderTypeEnumFind = null;
		if (null == id || id.intValue() < 0)
			return orderTypeEnumFind;

		for (OrderTypeEnum orderTypeEnum : values()) {
			if (orderTypeEnum.ordinal() == id.intValue()) {
				orderTypeEnumFind = orderTypeEnum;
				break;
			}
		}

		return orderTypeEnumFind;

	}

}
