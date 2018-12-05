package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum ErrorGoodsEvent implements IEnum,FsmEventEnum {
    create("创建"),
    agreeByBusiness("业务同意"),
    disagreeByBusiness("业务不同意"),
    completing("完成"),
    giveUp("放弃");



    private String display;

    ErrorGoodsEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static ErrorGoodsEvent fromName(String name) {
        for (ErrorGoodsEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
