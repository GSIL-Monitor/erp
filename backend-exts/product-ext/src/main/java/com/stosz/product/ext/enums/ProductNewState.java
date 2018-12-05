package com.stosz.product.ext.enums;

import com.stosz.plat.utils.IEnum;

public enum ProductNewState implements IEnum {
    start("开始"),
    draft("草稿"),
    waitAssign("待细化分类"),
    waitImageMatch("待图片匹配"),
    waitCheck("待排查"),
    checkOk("通过"),
    checkWarn("有风险"),
    duplication("重复产品"),
    waitApprove("待审批"),
    disappeared("已销档"),
    archived("已建档"),

    cancel("取消"),;

    private String display;

    ProductNewState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static ProductNewState fromName(String name) {
        for (ProductNewState orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }


}
