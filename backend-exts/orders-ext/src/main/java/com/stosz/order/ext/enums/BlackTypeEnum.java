package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 黑名单的ip地址
 */
public enum BlackTypeEnum implements IEnum{

    ip("IP"),
    phone("电话"),
    email("邮箱"),
    address("地址"),
    name("姓名"),
    other("其它");

    private String display = "";

    BlackTypeEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static BlackTypeEnum fromId(Integer id) {
        BlackTypeEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (BlackTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

    public static BlackTypeEnum fromName(String name) {
        for (BlackTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }





}
