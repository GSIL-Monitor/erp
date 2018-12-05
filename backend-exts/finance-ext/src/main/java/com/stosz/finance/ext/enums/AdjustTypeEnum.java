package com.stosz.finance.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:ChenShifeng
 * @Description:调整类型枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
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
