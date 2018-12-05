package com.stosz.admin.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * Created by XCY on 2017/8/24.
 * desc: 职位与权限Enum
 */
public enum JobAuthorityRelEnum implements IEnum {

	all("全公司"), myDepartment("本部门"), myself("个人");

	private String display;

	JobAuthorityRelEnum(String display) {
		this.display = display;
	}

	@Override
	public String display() {
		return this.display;
	}

	public static JobAuthorityRelEnum fromName(String name) {
		for (JobAuthorityRelEnum orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}

	public static JobAuthorityRelEnum fromId(Integer id) {
		for (JobAuthorityRelEnum orderTypEnum : values()) {
			if (orderTypEnum.ordinal() == id) {
				return orderTypEnum;
			}
		}
		return null;
	}
}
