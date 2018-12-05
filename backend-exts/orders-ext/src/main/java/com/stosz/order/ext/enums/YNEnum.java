package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 是否有效
 */
public enum YNEnum implements IEnum{
    yes("是"),
    no("否"),
    other("不限");


    private String display = "";

    YNEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static YNEnum fromId(Integer id) {
        YNEnum ynEnumid = null;
        if (null == id  || id.intValue() < 0) return ynEnumid;

        for (YNEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                ynEnumid =  orderTypeEnum;
                break;
            }
        }
        return ynEnumid;

    }

}
