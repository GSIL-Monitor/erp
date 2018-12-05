package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:ChenShifeng
 * @Description:报废类型
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum CalculateTypeEnum implements IEnum {

    calculate_for_qty("按包裹数平均算入归属部门"),//按包裹数平均算入归属部门
    calculate_for_weight("按重量平均算入归属部门");//按重量平均算入归属部门

    private String display;

    CalculateTypeEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static CalculateTypeEnum fromName(String name) {
        for (CalculateTypeEnum typeEnum : values()) {
            if (typeEnum.name().equalsIgnoreCase(name)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static CalculateTypeEnum fromId(Integer id) {
        for (CalculateTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
