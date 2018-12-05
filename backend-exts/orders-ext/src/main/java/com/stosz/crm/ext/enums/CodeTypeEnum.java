package com.stosz.crm.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 验证码类型
 */
public enum CodeTypeEnum implements IEnum {

    no("无"),

    right("正确"),

    wrong("错误");


    CodeTypeEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static CodeTypeEnum fromName(String name) {
        for (CodeTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static CodeTypeEnum fromId(Integer id) {
        for (CodeTypeEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
