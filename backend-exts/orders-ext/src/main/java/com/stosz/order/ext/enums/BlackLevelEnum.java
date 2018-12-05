package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 黑名单的级别
 */
public enum BlackLevelEnum implements IEnum{
    warning("警告"),
    blacklist("黑名单"),
    other("其它");


    private String display = "";

    BlackLevelEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static BlackLevelEnum fromId(Integer id) {
        BlackLevelEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (BlackLevelEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
