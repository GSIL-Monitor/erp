package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 订单来源
 *
 * @author wangqian
 * @date 2017-12-20
 */
public enum MerchantEnum implements IEnum {

    other("其它"),

    single("单品站"),

    multiple("综合站"),

    innererp("内部ERP")

    ;

    private String display;

    MerchantEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static MerchantEnum fromName(String name) {
        for (MerchantEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static MerchantEnum fromId(Integer id) {
        for (MerchantEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
