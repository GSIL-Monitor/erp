package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 
 * 退换货原因
 * @author liusl
 */
public enum RmaSourceEnum implements IEnum{

    aftersalemail("售后邮件"),
    ordersquestion("问题件"),
    logisticregression("物流自退"),
    system("系统");


    private String display = "";

    RmaSourceEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static RmaSourceEnum fromId(Integer id) {
        RmaSourceEnum rmaSourceEnum = null;
        if (null == id  || id.intValue() < 0) return rmaSourceEnum;

        for (RmaSourceEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                rmaSourceEnum =  orderTypeEnum;
                break;
            }
        }

        return rmaSourceEnum;

    }
    
    public static boolean isInclude(String display){
        boolean include = false;
        for (RmaSourceEnum rmaSourceEnum : values()) {
            if (rmaSourceEnum.display().equalsIgnoreCase(display)) {
                include = true;
                break;
            }
        }
        return include;
    }
    public static RmaSourceEnum getFormEnum(String display){
        RmaSourceEnum orderTypeEnumFind = null;
        
        for (RmaSourceEnum ordersChangeFromEnum : values()) {
            if (ordersChangeFromEnum.display().equalsIgnoreCase(display)) {
                orderTypeEnumFind = ordersChangeFromEnum;
            }
        }
        return orderTypeEnumFind;
    }

    public static RmaSourceEnum fromName(String name) {
        for (RmaSourceEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }



}
