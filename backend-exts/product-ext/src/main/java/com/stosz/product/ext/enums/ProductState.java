package com.stosz.product.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum ProductState implements IEnum {
    start("开始"),
    waitFill("待填充"),
    archiving("建档中"),
    onsale("已上架"),
    offsale("已下架"),
    disappeared("已消档"),
    ;

    private String display;

    ProductState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static ProductState fromName(String name) {
        for (ProductState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }


}
