package com.stosz.product.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public enum WmsState implements IEnum {
    create("新建"),
    open("开启"),
    close("关闭")
    ;

    private String display;

    WmsState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static WmsState fromName(String name) {
        for (WmsState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }


}

