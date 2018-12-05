package com.stosz.store.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:调拨单状态枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TransferEventEnum implements IEnum, FsmEventEnum {

    create("创建"),
    submit("提交"),
    out_approve_pass("出库部门审核通过"),
    approve_pass("运营审核通过"),
    approve_fail("审核不通过"),
    in_approve_pass("入库部门审核通过"),
    out_store("出库"),
    out_transit("转寄仓出库"),
    in_store("入库"),
    in_transit("入转寄仓"),
    cancel("取消");

    private String display;

    TransferEventEnum(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static TransferEventEnum fromName(String name) {
        for (TransferEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
