package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 设置客户级别的方式
 */
public enum SetLevelEnum implements IEnum{
    system("系统"),
    manpower("人工");


    private String display = "";

    SetLevelEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static SetLevelEnum fromId(Integer id) {
        SetLevelEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (SetLevelEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
