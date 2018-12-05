package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum ReturnedTypeEnum implements IEnum {

    INTHELIBRARY("在库采退"), INTRANSIT("在途采退"),WRONGGOODS("错货采退");

    private String display;

    ReturnedTypeEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }
    
    public static ReturnedTypeEnum fromName(String name) {
        for (ReturnedTypeEnum en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }
    public static ReturnedTypeEnum fromId(Integer id) {
        for (ReturnedTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
