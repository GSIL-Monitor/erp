package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 退换货原因
 * @author wangqian
 * @date 2017-11-28
 */
public enum RecycleGoodsEnum implements IEnum {

    no("不需要"),
    yes("需要");
    private String display = "";

    RecycleGoodsEnum(String display)
    {
        this.display = display;
    }

    public static RecycleGoodsEnum fromId(Integer id) {
        for (RecycleGoodsEnum changeReasonEnum : values()) {
            if (changeReasonEnum.ordinal() == id) {
                return changeReasonEnum;
            }
        }
        return null;
    }

    public static boolean isInclude(String display){
        boolean include = false;
        for (RecycleGoodsEnum changeReasonEnum : values()) {
            if (changeReasonEnum.display().equalsIgnoreCase(display)) {
                include = true;
                break;
            }
        }
        return include;
    }

    public static RecycleGoodsEnum getFormEnum(String display){
        RecycleGoodsEnum changeReasonEnum = null;

        for (RecycleGoodsEnum recycleGoods : values()) {
            if (recycleGoods.display().equalsIgnoreCase(display)) {
                changeReasonEnum = recycleGoods;
            }
        }
        return changeReasonEnum;
    }

    @Override
    public String display() {
        return this.display;
    }
    
}
