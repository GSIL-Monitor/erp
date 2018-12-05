package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 产品自定义类别枚举类型
 *
 * @author liufeng
 * @version [1.0 , 2017/9/20]
 */
public enum CustomEnum implements IEnum {
    normal("标品"),
    custome("定制");

    CustomEnum(String dis) {
        this.display = dis;
    }

    String display;

    public static CustomEnum fromName(String name) {
        for (CustomEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String display() {
        return this.display;
    }
}
