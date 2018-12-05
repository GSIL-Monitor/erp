package com.stosz.order.ext.enums;

import com.google.common.base.Strings;
import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-12-25
 */
public enum  CancelPeriodEnum implements IEnum {


    audit("审核阶段"),
    purchase("采购阶段"),
    waitDeliver_SelfWarehouse("普通仓库待发货"),
    waitDeliver_forwardWarehouse("转寄仓待发货");

    private String display="";


    CancelPeriodEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }

    public static CancelPeriodEnum fromId(Integer id) {
        CancelPeriodEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (CancelPeriodEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }


    public static CancelPeriodEnum fromId(String name) {
        CancelPeriodEnum orderTypeEnumFind = null;
        if (Strings.isNullOrEmpty(name)) return orderTypeEnumFind;

        for (CancelPeriodEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name() ==name)
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }

}
