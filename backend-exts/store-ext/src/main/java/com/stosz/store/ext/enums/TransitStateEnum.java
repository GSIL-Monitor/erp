package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description: 转寄仓状态
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public enum TransitStateEnum implements IEnum {

    wait_inTransit("待入转寄仓"),

    inTransit("已入转寄仓"),

    matched("转寄已匹配"),

    picking("转寄拣货中"),

    delivered("已发货"),

    transferring("调拨中"),

    invaliding("报废中"),

    invalided("已报废"),

    takingStock("盘亏中"),

    tookStock("已盘亏");

    TransitStateEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static TransitStateEnum fromName(String name) {
        for (TransitStateEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static TransitStateEnum fromId(Integer id) {
        for (TransitStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
