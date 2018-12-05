package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description: 是否使用WMS
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public enum UsesWmsEnum implements IEnum{

    no("否"),//0-否

    yes("是");//1-是



    UsesWmsEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static UsesWmsEnum fromName(String name) {
        for (UsesWmsEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static UsesWmsEnum fromId(Integer id) {
        for (UsesWmsEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
