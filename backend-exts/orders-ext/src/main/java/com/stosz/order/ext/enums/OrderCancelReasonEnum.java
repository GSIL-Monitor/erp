package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum OrderCancelReasonEnum implements IEnum {

    customer("客户取消"),

    business("业务取消"),

    purchase("采购取消"),

    other("其它原因取消")

            ;

    private String display;

    OrderCancelReasonEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static OrderCancelReasonEnum fromName(String name) {
        for (OrderCancelReasonEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OrderCancelReasonEnum fromId(Integer id) {
        for (OrderCancelReasonEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}