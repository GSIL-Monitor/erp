package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description:  盘点状态
 * @auther ChenShifeng
 * @Date Create time    2017/12/14
 */
public enum TakeStockStateEnum implements IEnum{

    waitAudit("待审核"),
    rebutFirst("初审驳回"),
    waitFinanceAudit("待财务复核"),
    rebutForFinance("财务驳回"),
    auditPass("已完成");

    TakeStockStateEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static TakeStockStateEnum fromName(String name) {
        for (TakeStockStateEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static TakeStockStateEnum fromId(Integer id) {
        for (TakeStockStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
