package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 支付状态
 */
public enum PayStateEnum implements IEnum {

    unPaid("未付款"),

    paid("已付款"),

    somePaid("部分付款"),

    Paying("付款中");

    private String display;

    PayStateEnum(String display) {
        this.display = display;
    }


    @Override
    public String display() {
        return display;
    }

    public static PayStateEnum fromName(String name) {
        for (PayStateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static PayStateEnum fromId(Integer id) {
        for (PayStateEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }


}