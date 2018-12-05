package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 货物的签收状态 或者拒收状态；
 */
public enum RejectStatusEnum implements IEnum{

    accept("签收"),
    reject("拒收"),
    other("未知");

    private String display = "";

    RejectStatusEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static RejectStatusEnum fromId(Integer id) {
        RejectStatusEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (RejectStatusEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
