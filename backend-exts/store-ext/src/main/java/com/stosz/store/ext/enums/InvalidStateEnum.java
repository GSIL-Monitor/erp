package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description:  报废状态
 * @auther ChenShifeng
 * @Date Create time    2017/12/14
 */
public enum InvalidStateEnum implements IEnum{

//    start("开始"),
    draft("草稿"),
    waitAudit("待审核"),
    reject("审核不通过"),
    auditPass("已完成");

    InvalidStateEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static InvalidStateEnum fromName(String name) {
        for (InvalidStateEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static InvalidStateEnum fromId(Integer id) {
        for (InvalidStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
