package com.stosz.product.ext.enums;

import com.stosz.plat.utils.IEnum;

import java.io.Serializable;


public enum TypeEnum implements IEnum,Serializable {
    supplier("供应商"),
    shipper("发货物流商"),
    purchase("采购物流商");

    private String display;

    @Override
    public String display() {
        return this.display = display();
    }

    TypeEnum(String display){
        this.display = display;
    }

    public static TypeEnum fromName(String name) {
        for (TypeEnum en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }
}
