package com.stosz.admin.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * Created by XCY on 2017/8/24.
 * desc: 用户的状态Enum
 */
public enum UserState implements IEnum {

	probationary("试用"), official("正式"), temporary("临时"), deferred("试用延期"), dismissal("解聘"), dimission("离职"), retire("退休"), invalid("无效");
	;

	private String display;

	UserState(String display) {
		this.display = display;
	}

	@Override
	public String display() {
		return this.display;
	}

	public static UserState fromName(String name) {
		for (UserState orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}

	public static UserState fromId(Integer id) {
		for (UserState orderTypEnum : values()) {
			if (orderTypEnum.ordinal() == id) {
				return orderTypEnum;
			}
		}
		return null;
	}
}
