package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 包裹类型   普货 特货
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
public enum AllowedProductTypeEnum implements IEnum {
	all("所有"),general("普货"), sensitive("特货");

	private String display = "";

	AllowedProductTypeEnum(String display) {
		this.display = display;
	}

	@Override
	public String display() {
		return display;
	}

	public static AllowedProductTypeEnum fromId(Integer id) {
		AllowedProductTypeEnum orderTypeEnumFind = null;
		if (null == id || id.intValue() < 0)
			return orderTypeEnumFind;

		for (AllowedProductTypeEnum orderTypeEnum : values()) {
			if (orderTypeEnum.ordinal() == id.intValue()) {
				orderTypeEnumFind = orderTypeEnum;
				break;
			}
		}

		return orderTypeEnumFind;

	}

}
