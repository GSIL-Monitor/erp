package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description: 仓库类型
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public enum WmsTypeEnum implements IEnum{

    self("自建"),//普通仓

    transit("转寄仓"),//1，2均属转寄仓

    third("第三方");

    WmsTypeEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static WmsTypeEnum fromName(String name) {
        for (WmsTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static WmsTypeEnum fromId(Integer id) {
        for (WmsTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
