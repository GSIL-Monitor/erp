package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum PurchaseReturnItemState implements IEnum {

    start("开始"),
    stayRefund("待财务退款"),
    cancel("已取消"),
    alreadyRefund("已申请退款"),
    returnByStorage("待仓储退货"),
    completed("完成");

    private String display;

    PurchaseReturnItemState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static PurchaseReturnItemState fromName(String name) {
        for (PurchaseReturnItemState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }

}
