package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 退款类型
 * 0 退货 1 拒收 2订单取消
 *
 * @auther tangtao
 * create time  2018/1/3
 */
public enum OrdersRefundTypeEnum implements IEnum {

    returned("退货"),
    rejection("拒收"),;

    private String display = "";

    OrdersRefundTypeEnum(String display) {
        this.display = display;
    }

    public static OrdersRefundTypeEnum fromId(Integer id) {
        for (OrdersRefundTypeEnum target : values()) {
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
