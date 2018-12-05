package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:ChenShifeng
 * @Description:报废类型
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum InvalidTypeEnum implements IEnum {

    damage("破损"),//破损
    batchProcess("批处理");//批处理

    private String display;

    InvalidTypeEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static InvalidTypeEnum fromName(String name) {
        for (InvalidTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static InvalidTypeEnum fromId(Integer id) {
        for (InvalidTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
