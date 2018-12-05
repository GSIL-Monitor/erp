package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 退款状态
 * 0 待审核 1 待退款 2已退款 3审核不通过
 *
 * @auther tangtao
 * create time  2018/1/3
 */
public enum OrdersRefundStatusEnum implements IEnum {
    start("开始"),
    toBeAudited("待审核"),
    toBeRefunded("待退款"),
    refunded("已退款"),
    notApproved("审核不通过"),;

    private String display = "";

    OrdersRefundStatusEnum(String display) {
        this.display = display;
    }

    public static OrdersRefundStatusEnum fromName(String name) {
        for (OrdersRefundStatusEnum ordersRefundStatusEnum : values()) {
            if (ordersRefundStatusEnum.name().equalsIgnoreCase(name)) {
                return ordersRefundStatusEnum;
            }
        }
        return null;
    }

    public static OrdersRefundStatusEnum fromId(Integer id) {
        for (OrdersRefundStatusEnum target : values()) {
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
