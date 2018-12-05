package com.stosz.plat.enums;

public enum UsableEnum {

	disable("无效"), enable("有效");

	String display;

	UsableEnum(String disp) {
		this.display = disp;
	}

	public String displayName() {
		return this.display;
	}

	public static UsableEnum fromName(String name) {
		for (UsableEnum orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}
	
	 public static String name(int ordinal) {
        for (UsableEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == ordinal) {
                return orderTypeEnum.display;
            }
        }
        return null;
    }
}
