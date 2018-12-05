package com.stosz.crm.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum  StateEnum implements IEnum {

    unusable("不可用"),

    usable("可用");



    StateEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static StateEnum fromName(String name) {
        for (StateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static StateEnum fromId(Integer id) {
        for (StateEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
