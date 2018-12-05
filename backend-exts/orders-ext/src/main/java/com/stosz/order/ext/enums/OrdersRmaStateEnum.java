package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 
 * 退换货状态
 * @author liusl
 */
public enum OrdersRmaStateEnum implements IEnum{

    start("开始"),
    draft("草稿"),
    waitAudit("待审核"),
    auditPass("审核通过"),
    reject("审核不通过"),
    waitDelivery("执行中"),
    finished("完成"),
    cancel("取消");


    private String display = "";

    OrdersRmaStateEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }
    public static OrdersRmaStateEnum fromName(String name) {
        for (OrdersRmaStateEnum OrdersRmaStateEnum : values()) {
            if (OrdersRmaStateEnum.name().equalsIgnoreCase(name)) {
                return OrdersRmaStateEnum;
            }
        }
        return null;
    }
    
    public static OrdersRmaStateEnum fromId(Integer id) {
        OrdersRmaStateEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (OrdersRmaStateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }
    

}
