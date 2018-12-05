package com.stosz.product.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum ProductEvent implements IEnum,FsmEventEnum {
    create("创建"),
    fill("填充"),
    archive("建档"),
    cancel("销档"),
    offsale("下架"),
    archiving("建档中"),
    revoke("撤回");

    private String display;

    ProductEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static ProductEvent fromName(String name) {
        for (ProductEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
