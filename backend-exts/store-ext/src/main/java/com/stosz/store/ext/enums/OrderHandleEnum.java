package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:订单库存操作枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum OrderHandleEnum implements IEnum {

    order_undelivered("待发货"),//配货将订单变为待发货
    order_delivered("订单已发货"),//订单已发货
    order_cancel("取消订单"),//取消订单
    return_apply("退货申请通过"),//退货申请通过
    return_apply_fail("退货回收取消"),//退货回收取消
    order_in_stock("退货入库");//退货入库

    private String display;

    OrderHandleEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static OrderHandleEnum fromName(String name) {
        for (OrderHandleEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
