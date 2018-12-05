package com.stosz.crm.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author wangqian
 * 用户信用等级
 */
public enum  CustomerCreditEnum implements IEnum {

    normal("普通"),
//    excellent("优质"),
    risk("风险"),
    bad("恶意");

    CustomerCreditEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static CustomerCreditEnum fromName(String name) {
        for (CustomerCreditEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static CustomerCreditEnum fromId(Integer id) {
        for (CustomerCreditEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
