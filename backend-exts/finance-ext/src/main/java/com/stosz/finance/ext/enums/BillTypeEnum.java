package com.stosz.finance.ext.enums;

import com.stosz.plat.utils.IEnum;


/**
 * @author ChenShifeng
 * 2018-1-7
 *  发生 单据类型
 */
public enum BillTypeEnum implements IEnum {

    purchase("采购单"),
    recovery("采退单"),
    adjustment("调整单")
    ;

    private String display;

    BillTypeEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static BillTypeEnum fromName(String name) {
        for (BillTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static BillTypeEnum fromId(Integer id) {
        for (BillTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
