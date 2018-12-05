package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 仓库类别
 */
public enum WarehouseTypeEnum implements IEnum{
    normal("自建仓库"),
    forward("转寄仓库"),
    third("第三方仓库");


    private String display = "";

    WarehouseTypeEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static WarehouseTypeEnum fromId(Integer id) {
        WarehouseTypeEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (WarehouseTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
