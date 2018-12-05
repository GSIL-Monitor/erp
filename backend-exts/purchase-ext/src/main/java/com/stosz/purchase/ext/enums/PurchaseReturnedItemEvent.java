package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum PurchaseReturnedItemEvent implements IEnum, FsmEventEnum {

    create("创建"),
    financeAffirmRefund("在库-财务确认退款"),
    financeAffirmRefundInWay("在途-财务确认退款"),
    cancelRefund("取消采退"),
    returnByInStock("在库退货"),
    returnByInWay("在途退货"),
    deliveryStock("出库");

    private String display;

    PurchaseReturnedItemEvent(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static PurchaseReturnedItemEvent fromName(String name) {
        for (PurchaseReturnedItemEvent en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }

}
