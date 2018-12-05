package com.stosz.plat.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2018-01-15
 * type 可以用来觉得发起重试的类别，可以发起http rpc方式的重试
 */
public enum InterfaceNameEnum implements IEnum {

    others("其它","http"),
    addSaleOrder("新增销售订单","http"),
    cancelSaleOrder("取消新增销售订单","http"),
    saleOutCallback("出库回调","http")
    ;


    private String display = "";
    private String type = "http";


    InterfaceNameEnum(String display,String type)
    {
        this.display = display;
        this.type = type;
    }

    @Override
    public String display() {
        return display;
    }


    public boolean isHttp(){
        return "http".equalsIgnoreCase(getType());
    }

    public boolean isRpc(){
        return "rpc".equalsIgnoreCase(getType());
    }


    public String getType() {
        return type;
    }

    public static InterfaceNameEnum fromId(Integer id) {
        InterfaceNameEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (InterfaceNameEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }


}