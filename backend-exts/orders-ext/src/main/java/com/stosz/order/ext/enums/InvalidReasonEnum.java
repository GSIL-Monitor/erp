package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 无效订单-原因
 * @author wangqian
 * @date2017-12-25
 */
public enum InvalidReasonEnum implements IEnum {

    repeat("重复订单"),

    incomplete("信息不完整"),

    malice("恶意订单");

    InvalidReasonEnum(String display) {
        this.display = display;
    }

    private String display;


    @Override
    public String display() {
        return this.display;
    }

    public static InvalidReasonEnum fromName(String name) {
        for (InvalidReasonEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static InvalidReasonEnum fromId(Integer id) {
        for (InvalidReasonEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }

        return null;
    }
}
