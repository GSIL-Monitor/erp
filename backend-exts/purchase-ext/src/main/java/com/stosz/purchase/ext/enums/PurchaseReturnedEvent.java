package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum PurchaseReturnedEvent implements IEnum, FsmEventEnum {

    create("创建"),
    submit("提交财务"),
    agreeByBusiness("业务同意"),
    financeAffirmRefund("财务付款"),
    cancelRefund("取消采退"),
    returnByInStock("在库退货"),
    returnByInWay("在途退货"),
    deliveryStock("出库");

    private String display;

    PurchaseReturnedEvent(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static PurchaseReturnedEvent fromName(String name) {
        for (PurchaseReturnedEvent en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }

}
