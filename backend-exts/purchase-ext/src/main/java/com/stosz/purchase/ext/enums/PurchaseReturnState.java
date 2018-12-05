package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum PurchaseReturnState implements IEnum {

    start("开始"),
    waitAuditor("待业务审核"),
    stayRefund("待财务退款"),
    cancel("已取消"),
    alreadyRefund("已申请退款"),
    returnByStorage("待仓储退货"),
    completed("完成");

    private String display;

    PurchaseReturnState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static PurchaseReturnState fromName(String name) {
        for (PurchaseReturnState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }

}
