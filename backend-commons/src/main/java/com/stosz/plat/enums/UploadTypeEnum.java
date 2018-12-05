package com.stosz.plat.enums;

public enum UploadTypeEnum {
	
	productNew("新品");
	
    String display;

    UploadTypeEnum(String disp) {
        this.display = disp;
    }

    public String getDisplay() {
        return this.display;
    }

	public static UploadTypeEnum fromName(String name) {
		for (UploadTypeEnum orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}
}
