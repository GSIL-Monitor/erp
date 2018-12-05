package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2018-01-15
 */
public enum ResponseResultEnum  implements IEnum {
    others("其它"),
    wait("等待"),
    success("成功"),
    fail("失败"),
    exception("异常")
    ;

    private String display = "";


    ResponseResultEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }


    public static ResponseResultEnum fromId(Integer id) {
        ResponseResultEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (ResponseResultEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }


}