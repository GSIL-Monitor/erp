package com.stosz.purchase.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;


/**
 * 应付款单的事件
 * @author xiongchenyang
 */
public enum PayableEventEnum implements IEnum ,FsmEventEnum {
    create("创建"),
    createPayment("生成付款单"),
    refusePay("拒绝付款"),
    pay("付款");

    private String display;

    PayableEventEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static PayableEventEnum fromName(String name) {
        for (PayableEventEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static PayableEventEnum fromId(Integer id) {
        for (PayableEventEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
