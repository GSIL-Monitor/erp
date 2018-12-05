package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 退换货原因
 * @author wangqian
 * @date 2017-11-28
 */
public enum ChangeWayEnum implements IEnum {

    logisticsBack("物流自退"),
    noSetting("未设置");
    private String display = "";

    ChangeWayEnum(String display)
    {
        this.display = display;
    }

    public static ChangeWayEnum fromId(Integer id) {
        for (ChangeWayEnum changeReasonEnum : values()) {
            if (changeReasonEnum.ordinal() == id) {
                return changeReasonEnum;
            }
        }
        return null;
    }

    public static boolean isInclude(String display){
        boolean include = false;
        for (ChangeWayEnum changeReasonEnum : values()) {
            if (changeReasonEnum.display().equalsIgnoreCase(display)) {
                include = true;
                break;
            }
        }
        return include;
    }

    public static ChangeWayEnum getFormEnum(String display){
        ChangeWayEnum changeReasonEnum = null;

        for (ChangeWayEnum ordersChangeFromEnum : values()) {
            if (changeReasonEnum.display().equalsIgnoreCase(display)) {
                changeReasonEnum = ordersChangeFromEnum;
            }
        }
        return changeReasonEnum;
    }

    @Override
    public String display() {
        return this.display;
    }
    
}
