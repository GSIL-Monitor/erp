package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description: 盘点类型
 * @auther ChenShifeng
 * @Date Create time    2017/12/14
 */
public enum TakeStockTypeEnum implements IEnum {

    inventoryProfit("盘盈"),

    inventoryLosses("盘亏");

    TakeStockTypeEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static TakeStockTypeEnum fromName(String name) {
        for (TakeStockTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static TakeStockTypeEnum fromId(Integer id) {
        for (TakeStockTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
