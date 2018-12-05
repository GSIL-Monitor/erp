package com.stosz.product.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum ProductZoneEvent implements IEnum,FsmEventEnum {
    create("创建"),
    archive("建档"),
    waitoffsale("待下架"),
    offsale("下架"),
    cancel("销档"),
    archiving("建档中"),


    ;

    private String display;

    ProductZoneEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static ProductZoneEvent fromName(String name) {
        for (ProductZoneEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
