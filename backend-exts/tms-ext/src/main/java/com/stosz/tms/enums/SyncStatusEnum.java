package com.stosz.tms.enums;

import com.stosz.plat.utils.IEnum;

public enum SyncStatusEnum implements IEnum {

	DOWITHOUT("无须同步"), STAYSYNC("待同步"), SYNC_SUCCESS("同步成功"), SYNC_FAIL("同步失败");

	private String name;

	SyncStatusEnum(String name) {
		this.name = name;
	}

	@Override
	public String display() {
		return name;
	}

	public static SyncStatusEnum fromId(Integer id) {
		SyncStatusEnum syncStatusEnum = null;
		if (null == id || id.intValue() < 0)
			return syncStatusEnum;

		for (SyncStatusEnum orderTypeEnum : values()) {
			if (orderTypeEnum.ordinal() == id.intValue()) {
				syncStatusEnum = orderTypeEnum;
				break;
			}
		}
		return syncStatusEnum;

	}

}
