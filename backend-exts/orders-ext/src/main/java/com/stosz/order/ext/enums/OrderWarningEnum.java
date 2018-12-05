package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum OrderWarningEnum implements IEnum {

    blackList("黑名单"),

    repeat("重复订单"),

    normal("正常");

    private String display;

    OrderWarningEnum(String display) {
        this.display = display;
    }


    @Override
    public String display() {
        return display;
    }

    public static OrderWarningEnum fromName(String name) {
        for (OrderWarningEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OrderWarningEnum fromId(Integer id) {
        for (OrderWarningEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }


}
