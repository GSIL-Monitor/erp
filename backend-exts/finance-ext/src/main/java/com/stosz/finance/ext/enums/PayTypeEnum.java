package com.stosz.finance.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:仓库进出库枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum PayTypeEnum implements IEnum {

    purchase_amount("采购货款"),
    purchase_tms("采购物流费"),
    tms_cost("发货物流费");

    private String display;

    PayTypeEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static PayTypeEnum fromName(String name) {
        for (PayTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static PayTypeEnum fromId(Integer id) {
        for (PayTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
