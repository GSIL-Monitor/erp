package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/26]
 */
public enum PayMethodEnum implements IEnum {

    payFirst("先款后货"),
    cod("货到付款");


    private String display;

    PayMethodEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static PayMethodEnum fromName(String name) {
        for (PayMethodEnum paymentMethodEnum : values()) {
            if (paymentMethodEnum.name().equalsIgnoreCase(name)) {
                return paymentMethodEnum;
            }
        }
        return null;
    }

}
