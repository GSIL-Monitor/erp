package com.stosz.finance.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:仓库进出库枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum PayStateEnum implements IEnum {

    wait_pay("待支付"),
    paid("已支付"),
    refuse("拒绝");

    private String display;

    PayStateEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static PayStateEnum fromName(String name) {
        for (PayStateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static PayStateEnum fromId(Integer id) {
        for (PayStateEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
