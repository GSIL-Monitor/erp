package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 新品分类枚举类型
 * @author Administrator
 */
public enum ClassifyEnum implements IEnum {
    D("带电"),
    Y("液体"),
    S("普通");

	String display;

	ClassifyEnum(String disp) {
        this.display = disp;
    }

    public String display() {
        return this.display;
    }

	public static ClassifyEnum fromName(String name) {
		for (ClassifyEnum orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}
}
