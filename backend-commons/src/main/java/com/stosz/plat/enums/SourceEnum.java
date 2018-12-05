package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 新品来源枚举类型
 * @author Administrator
 *
 */
public enum SourceEnum implements IEnum {
	market("市场"),
	taobao("淘宝"), 
	alibaba("阿里巴巴"),
	other("其他"),
	;
    String display;

    SourceEnum(String disp) {
        this.display = disp;
    }

    public String display() {
        return this.display;
    }

	public static SourceEnum fromName(String name) {
		for (SourceEnum orderTypeEnum : values()) {
			if (orderTypeEnum.name().equalsIgnoreCase(name)) {
				return orderTypeEnum;
			}
		}
		return null;
	}
}
