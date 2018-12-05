package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:订单库存操作枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum InvoicingTypeEnum implements IEnum {

    purchase("采购"),//采购
    transfer("调拨"),//调拨
    order("销售"),//销售
    takeStock("盘点单"),
    discard("报废单");

    private String display;

    InvoicingTypeEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static InvoicingTypeEnum fromName(String name) {
        for (InvoicingTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static InvoicingTypeEnum fromId(Integer id) {
        for (InvoicingTypeEnum typeEnum : values()) {
            if (typeEnum.ordinal() == id) {
                return typeEnum;
            }
        }
        return null;
    }
}
