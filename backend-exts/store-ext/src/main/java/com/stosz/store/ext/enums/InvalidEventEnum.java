package com.stosz.store.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * @Author:ChenShifeng
 * @Description:报废
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum InvalidEventEnum implements IEnum, FsmEventEnum {

    create("创建"),
    submit("提交"),
    auditPass("审核通过"),
    reject("审核不通过"),
    delete("删除");

    private String display;

    InvalidEventEnum(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static InvalidEventEnum fromName(String name) {
        for (InvalidEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
