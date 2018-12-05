package com.stosz.product.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum ProductZoneState implements IEnum {
    start("开始"),
    archiving("建档中"),
    onsale("已上架"),
    waitoffsale("待下架"),
    offsale("已下架"),
    disappeared("已消档"),
    ;

    private String display;

    ProductZoneState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static ProductZoneState fromName(String name) {
        for (ProductZoneState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }


}
