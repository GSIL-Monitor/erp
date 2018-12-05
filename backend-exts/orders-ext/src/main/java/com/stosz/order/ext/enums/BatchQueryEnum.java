package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author wangqian
 * @date 2017-12-8
 *
 *  批量查询类型
 */
public enum BatchQueryEnum implements IEnum {

    trackingNo("运单号"),

    orderId("订单编号"),

    tel("手机号");


    private String display = "";

    BatchQueryEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static BatchQueryEnum fromName(String name) {
        for (BatchQueryEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static BatchQueryEnum fromId(Integer id) {
        for (BatchQueryEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
