package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 物流运单号的运输类型
 * @author xiongchenyang
 * @version [1.0 , 2017/12/26]
 */
public enum ShippingTypeEnum implements IEnum {

    free("包邮"),
    toPay("到付");


    private String display;

    ShippingTypeEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static ShippingTypeEnum fromName(String name) {
        for (ShippingTypeEnum paymentMethodEnum : values()) {
            if (paymentMethodEnum.name().equalsIgnoreCase(name)) {
                return paymentMethodEnum;
            }
        }
        return null;
    }

}
