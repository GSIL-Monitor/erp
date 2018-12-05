package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;


/**
 * @author wangqian
 * 2017-11-7
 * 订单列表  操作选择
 */
public enum OperationEnum implements IEnum {

    valid("有效订单"),

    unContact("待联系"),

//    inValid("无效订单"),

    repeat("重复订单"),

    inComplete("信息不完整"),

    malice("恶意下单"),

    crmAuto("系统自动审单")

    ;

    private String display;

    OperationEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static OperationEnum fromName(String name) {
        for (OperationEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OperationEnum fromId(Integer id) {
        for (OperationEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }
}
