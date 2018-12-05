package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:仓库进出库枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum StockStateEnum implements IEnum {

    stock_in("入仓"),//进
    stock_out("出仓");//出


    private String display;

    StockStateEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static StockStateEnum fromName(String name) {
        for (StockStateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static StockStateEnum fromId(Integer id) {
        for (StockStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
