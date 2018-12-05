package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum UserProductRelState implements IEnum {
    CREATE("新建"),
    ENABLE("有效"),
    DISABLE("禁用");

    private String display;

    UserProductRelState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static UserProductRelState fromName(String name) {
        for (UserProductRelState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }

    public static UserProductRelState fromOrdinal(int value) {
        for (UserProductRelState relState : values()) {
            if (relState.ordinal() == value) {
                return relState;
            }
        }
        return null;
    }
}
