package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:订单库存操作枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TransitHandleEnum implements IEnum {

    create_takeStock("创建盘点单"),//锁库存
    takeStock_apply_fail("审核不通过"),//解库存
    takeStock_apply_pass("审核通过"),//出库减库存
    create_discard("创建报废单"),//锁库存
    discard_apply_fail("报废单审核不通过"),//解库存
    discard_apply_pass("报废单审核通过");//出库减库存

    private String display;

    TransitHandleEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static TransitHandleEnum fromName(String name) {
        for (TransitHandleEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
