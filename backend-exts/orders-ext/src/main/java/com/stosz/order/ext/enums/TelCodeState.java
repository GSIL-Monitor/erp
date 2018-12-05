package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author wangqian
 * 2017-11-7
 *  订单列表页 手机搜索状态
 */
public enum  TelCodeState implements IEnum{

    succ("验证成功"),

    fail("验证失败"),

    unValidate("未验证");

    private String display;

    TelCodeState(String display) {
        this.display = display;
    }


    @Override
    public String display() {
        return this.display;
    }


    public static TelCodeState fromName(String name) {
        for (TelCodeState orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static TelCodeState fromId(Integer id) {
        for (TelCodeState orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
