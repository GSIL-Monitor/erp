package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:调拨单类型枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TransferTypeEnum implements IEnum {

    sameNormal("同仓调拨"),//0:同仓调拨
    normal2Normal("普通仓转普通仓"),//普通=>普通
    transit2Normal("转寄仓转普通仓"),//2,转寄仓转普通仓
    transit2Transit("转寄仓转转寄仓"),//3,转寄仓转转寄仓
    sameTransit("转寄仓同仓");

    private String display;

    TransferTypeEnum(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static TransferTypeEnum fromName(String name) {
        for (TransferTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static TransferTypeEnum fromId(Integer id) {
        for (TransferTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
