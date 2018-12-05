package com.stosz.crm.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum  OrderStatusEnum implements IEnum {

    sign("签收"),

    reject("拒签"),

    returned("退货"),

    other("其它");


    OrderStatusEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static OrderStatusEnum fromName(String name) {
        for (OrderStatusEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OrderStatusEnum fromId(Integer id) {
        for (OrderStatusEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
