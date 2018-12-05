package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

import java.io.Serializable;

/**
 * @author wangqian
 * 2017年11月6日
 * 订单状态
 */
public enum OrderStateEnum implements IEnum , Serializable{

    start("开始"),

    waitAudit("待审核"),

    waitContact("待联系"),

    invalidOrder("无效订单"),

    orderCancel("订单取消"),

    auditPass("审核通过"),

    waitSpecifyLogistic("待指定物流"),

    waitPurchase("待采购"),

    waitDeliver("待发货"),

    deliver("已发货"),

    sign("已签收"),

    rejection("拒收"),

    /*returned("退货"),

    exchange("换货"),*/
//
//    claims("丢件理赔"),
//
//    question("问题件"),
//
//    repeat("重复订单"),
//
//    incomplete("信息不完整"),
//
//    malice("恶意订单"),
//
//    specifyLogisticFail("指派物流失败")
    ;


    OrderStateEnum(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static OrderStateEnum fromName(String name) {
        for (OrderStateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OrderStateEnum fromId(Integer id) {
        for (OrderStateEnum orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }

        return null;
    }
}
