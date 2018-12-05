package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description: 仓库状态
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public enum WmsStateEnum implements IEnum{

    disabled("无效"),//0-无效

    enable("有效");//1-有效



    WmsStateEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static WmsStateEnum fromName(String name) {
        for (WmsStateEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static WmsStateEnum fromId(Integer id) {
        for (WmsStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
