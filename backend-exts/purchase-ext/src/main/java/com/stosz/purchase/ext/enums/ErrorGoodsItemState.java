package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public enum ErrorGoodsItemState implements IEnum {
    start("开始"),
    cancel("已取消"),
    waitBusinessApprove("待业务审批"),
    businessDisagree("业务不同意"),
    executing("执行中"),//执行中生成采购单和采退单后直接变成已完成
    completed("已完成");



    private String display;

    ErrorGoodsItemState(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static ErrorGoodsItemState fromName(String name) {
        for (ErrorGoodsItemState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }
}
