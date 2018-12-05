package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 是否可用状态
 *
 */
public enum  UseableEnum implements IEnum {

    other("不限"),

    use("是"),

    unuse("否")

    ;


    private String display;

    UseableEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }


    public static UseableEnum fromName(String name) {
        for (UseableEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static UseableEnum fromId(Integer id) {
        for (UseableEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}