package com.stosz.store.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * @Author:ChenShifeng
 * @Description:盘点单状态枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TakeStockEventEnum implements IEnum, FsmEventEnum {

    create("创建"),
    firstAuditPass("初审通过"),
    rejectFirst("初审不通过"),
    passFinance("财务复核通过"),
    rejectFinance("财务复核不通过");

    private String display;

    TakeStockEventEnum(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static TakeStockEventEnum fromName(String name) {
        for (TakeStockEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
