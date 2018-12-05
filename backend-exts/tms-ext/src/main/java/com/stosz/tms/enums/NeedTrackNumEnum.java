package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

public enum NeedTrackNumEnum implements IEnum {

	NOTNEED("不需要预先导入"), NEED("需要预先导入");
	NeedTrackNumEnum(String display) {
		this.display = display;
	}

	private String display;

	@Override
	public String display() {
		return this.display;
	}

	public static NeedTrackNumEnum fromId(Integer id) {
		for (NeedTrackNumEnum orderTypEnum : values()) {
			if (orderTypEnum.ordinal() == id) {
				return orderTypEnum;
			}
		}

		return null;
	}
}
