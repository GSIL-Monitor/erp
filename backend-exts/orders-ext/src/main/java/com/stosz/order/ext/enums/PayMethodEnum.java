package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author wangqian
 * 2017-11-7
 * 支付方式
 */
public enum PayMethodEnum implements IEnum{

    cod("货到付款"),

    onlinePay("在线支付"),

    other("其它");

    private String display;

    PayMethodEnum(String display) {
        this.display = display;
    }


    @Override
    public String display() {
        return display;
    }

    public static PayMethodEnum fromName(String name) {
        for (PayMethodEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static PayMethodEnum fromId(Integer id) {
        for (PayMethodEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }


}
