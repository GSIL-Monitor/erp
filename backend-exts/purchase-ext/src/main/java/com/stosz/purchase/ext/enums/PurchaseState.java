package com.stosz.purchase.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public enum PurchaseState implements IEnum {
    start("开始"),
    draft("草稿"),
    cancel("已取消"),
    waitBusinessApprove("待业务审批"),
    businessDisagree("业务不同意"),
    waitFinanceApprove("待财务审批"),
    financeDisagree("财务不同意"),
    waitPayment("待付款"),
    refusePayment("拒绝付款"),
    executing("执行中"),
    completed("已完成");



    private String display;

    PurchaseState (String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static PurchaseState fromName(String name) {
        for (PurchaseState en : values()) {
            if (en.name().equalsIgnoreCase(name)) {
                return en;
            }
        }
        return null;
    }
}
