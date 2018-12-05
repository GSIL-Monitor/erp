package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum UserBuDeptState implements IEnum {

    CREATE("新建"),
    ENABLE("有效"),
    DISABLE("禁用");

    private String display;

    UserBuDeptState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static UserBuDeptState fromName(String name) {
        for (UserBuDeptState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }

}
