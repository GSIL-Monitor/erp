package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 退款方式
 * 0 物流承运商 1 PayPal
 *
 * @auther tangtao
 * create time  2018/1/3
 */
public enum OrdersRefundMethodEnum implements IEnum {

    logistics("物流承运商"),
    payPal("PayPal"),;


    private String display = "";

    OrdersRefundMethodEnum(String display) {
        this.display = display;
    }

    public static OrdersRefundMethodEnum fromId(Integer id) {
        for (OrdersRefundMethodEnum target : values()) {
            if (target.ordinal() == id) {
                return target;
            }
        }
        return null;
    }

    @Override
    public String display() {
        return display;
    }
}
