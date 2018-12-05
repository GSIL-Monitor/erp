package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 
 * 退换货类型
 * @author wangqian
 */
public enum ChangeTypeEnum implements IEnum{

    returned("退货"),

    reject("拒收");

    private String display = "";

    ChangeTypeEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static ChangeTypeEnum fromId(Integer id) {
        ChangeTypeEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (ChangeTypeEnum orderTypeEnum : values())
        {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

    public static ChangeTypeEnum fromName(String name) {
        for (ChangeTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }


}
