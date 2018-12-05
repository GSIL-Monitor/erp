package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2018-01-15
 */
public enum InterfaceTypeEnum implements IEnum{
    others("其它"),orders("订单");

    private String display = "";


    InterfaceTypeEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }


    public static InterfaceTypeEnum fromId(Integer id) {
        InterfaceTypeEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (InterfaceTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }


}
