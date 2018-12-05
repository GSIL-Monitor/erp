package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-14
 * 订单类型
 */
public enum  OrderTypeEnum implements IEnum{
    normal("建站订单"),
    internal("内部订单"),
    xlsImport("XLS导入订单"),
    oldErpSycn("老ERP导入订单");


    private String display = "";

    OrderTypeEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static OrderTypeEnum fromId(Integer id) {
        OrderTypeEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (OrderTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
