package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 退换货原因
 * @author wangqian
 * @date 2017-11-28
 */
public enum ChangeReasonEnum implements IEnum {

    quality("质量原因"),
    wrong("货不对版"),
    personal("个人原因"),
    missing("丢件"),
    unclearance("清关失败"),
    other("其它");
    private String display = "";

    ChangeReasonEnum(String display)
    {
        this.display = display;
    }

    public static ChangeReasonEnum fromId(Integer id) {
        for (ChangeReasonEnum changeReasonEnum : values()) {
            if (changeReasonEnum.ordinal() == id) {
                return changeReasonEnum;
            }
        }
        return null;
    }

    public static boolean isInclude(String display){
        boolean include = false;
        for (ChangeReasonEnum changeReasonEnum : values()) {
            if (changeReasonEnum.display().equalsIgnoreCase(display)) {
                include = true;
                break;
            }
        }
        return include;
    }

    public static ChangeReasonEnum getFormEnum(String display){
        ChangeReasonEnum changeReasonEnum = null;

        for (ChangeReasonEnum changeReason : values()) {
            if (changeReason.display().equalsIgnoreCase(display)) {
                changeReasonEnum = changeReason;
            }
        }
        return changeReasonEnum;
    }

    public static ChangeReasonEnum fromName(String name) {
        for (ChangeReasonEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    @Override
    public String display() {
        return this.display;
    }
    
}
