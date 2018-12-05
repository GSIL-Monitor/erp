package com.stosz.order.ext.enums;


import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * 退款操作Event枚举
 *
 * @author liusl
 */
public enum OrderRefundEventEnum implements IEnum, FsmEventEnum {

    create("退款单创建(货到付款)"),
    createPre("退款单创建(预付款)"),
    refundAuditPass("审核通过"),
    refundAuditReject("审核不通过"),
    toApplyRefund("申请退款(预付款)"),
    toRefunded("申请退款(货到付款)"),
    confrimRefund("财务退款确认");

    private String name;

    OrderRefundEventEnum(String name) {
        this.name = name;
    }


    @Override
    public String display() {
        return name;
    }

    public String getName() {
        return name;
    }


    public static OrderRefundEventEnum fromName(String name) {
        for (OrderRefundEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
