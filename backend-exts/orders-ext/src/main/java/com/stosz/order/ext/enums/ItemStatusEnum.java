package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 订单项的状态
 */
public enum ItemStatusEnum implements IEnum{

    unAudit("待审核"),
    audit("已审核"),
    lockStock("锁定库存");


    private String display = "";

    ItemStatusEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static ItemStatusEnum fromId(Integer id) {
        ItemStatusEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (ItemStatusEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
