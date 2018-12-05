package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:采购库存操作枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum PurchaseHandleEnum implements IEnum {

    ready_purchase("草稿"),//采购需求变为草稿采购单
    purchase_cancel("采购单取消"),//采购单取消
    purchase_in_stock("采购入库"),//采购入库
    purchase_lock_stock("入库锁库存"),//部分入库如立马分配归属权，则需要锁库存
    intransit_minus("在途数减少"),//在途采退
    intransit_add("在途数增加"),//在途采退
    return_apply("在库采退申请"),//在库采退申请
    return_final("在库采退完成"),//在库采退完成
    return_fail("在库采退取消");//在库采退取消

    private String display;

    PurchaseHandleEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static PurchaseHandleEnum fromName(String name) {
        for (PurchaseHandleEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
