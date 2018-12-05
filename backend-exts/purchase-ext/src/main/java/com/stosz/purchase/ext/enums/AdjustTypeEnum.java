package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 调整单类型的枚举
 * @author xiongchengyang
 */
public enum AdjustTypeEnum implements IEnum {

    pay("付款"),
    collect("收款");

    private String display;

    AdjustTypeEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static AdjustTypeEnum fromName(String name) {
        for (AdjustTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static AdjustTypeEnum fromId(Integer id) {
        for (AdjustTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
