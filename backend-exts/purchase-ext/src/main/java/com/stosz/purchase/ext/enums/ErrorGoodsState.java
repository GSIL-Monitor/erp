package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public enum ErrorGoodsState implements IEnum {
    start("开始"),
    cancel("已取消"),
    waitBusinessApprove("待业务审批"),
    businessDisagree("业务不同意"),
    executing("执行中"),
    completed("已完成");



    private String display;

    ErrorGoodsState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static ErrorGoodsState fromName(String name) {
        for (ErrorGoodsState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }
}
