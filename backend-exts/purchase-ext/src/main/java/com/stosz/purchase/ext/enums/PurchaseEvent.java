package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum PurchaseEvent implements IEnum,FsmEventEnum {
    create("创建"),
    submit("提交"),
    agreeByBusiness("业务同意"),
    disagreeByBusiness("业务不同意"),
    agreeByFinance("财务同意"),
    disagreeByFinance("财务不同意"),
    pay("付款"),
    refusePay("拒绝付款"),
    inStock("入库"),
    giveUp("放弃");



    private String display;

    PurchaseEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static PurchaseEvent fromName(String name) {
        for (PurchaseEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
