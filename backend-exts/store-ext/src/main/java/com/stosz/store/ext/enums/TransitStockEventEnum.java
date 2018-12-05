package com.stosz.store.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * @Author:ChenShifeng
 * @Description:转寄仓
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TransitStockEventEnum implements IEnum, FsmEventEnum {

    create("创建"),
    inStock("转寄入库"),
    //    match("配货"),
    exportPack("拣货导出"),
    importDeliver("发货导入"),
    transfer("调拨"),
    transferDept("转寄同仓调拨"),
    invalid("报废"),
    takeStock("盘点"),
    takeStockPass("盘点审核通过"),
    invalidPass("报废审核通过");

    private String display;

    TransitStockEventEnum(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static TransitStockEventEnum fromName(String name) {
        for (TransitStockEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
