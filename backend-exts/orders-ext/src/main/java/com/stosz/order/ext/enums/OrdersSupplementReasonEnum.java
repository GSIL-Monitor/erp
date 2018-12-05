package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;
import com.stosz.plat.utils.StringUtils;

/**
 * 补发原因
 *
 * @author tangtao
 * @date 2017-12-18
 */
public enum OrdersSupplementReasonEnum implements IEnum {

    miss("漏发"),
    lose("丢件"),
    faulty("瑕疵品"),;

    private String display = "";

    OrdersSupplementReasonEnum(String display) {
        this.display = display;
    }


    public static OrdersSupplementReasonEnum fromId(Integer id) {

        if (null == id || id.intValue() < 0) return null;

        for (OrdersSupplementReasonEnum reasonEnum : values()) {
            if (reasonEnum.ordinal() == id.intValue()) {
                return reasonEnum;
            }
        }
        return null;
    }

    public static OrdersSupplementReasonEnum fromDisplay(String name) {

        if (StringUtils.isEmpty(name)) return null;

        for (OrdersSupplementReasonEnum reasonEnum : values()) {
            if (reasonEnum.display().equals(name)) {
                return reasonEnum;
            }
        }
        return null;
    }

    @Override
    public String display() {
        return this.display;
    }

}
