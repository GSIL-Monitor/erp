package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;


/**
 * @author xiongchenyang
 * 应付款单的状态
 */
public enum PayableStateEnum implements IEnum {
    start("开始"),
    waitPay("待付款"),
    executing("执行中"),
    paid("已支付"),
    nonPay("拒绝付款")
    ;

    private String display;

    PayableStateEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static PayableStateEnum fromName(String name) {
        for (PayableStateEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static PayableStateEnum fromId(Integer id) {
        for (PayableStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
