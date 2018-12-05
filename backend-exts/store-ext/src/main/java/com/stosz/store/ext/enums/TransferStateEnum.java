package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:调拨单状态枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TransferStateEnum implements IEnum{

    start("开始"),
    draft("草稿"),
    cancel("已取消"),
    wait_approve("待审批"),
    wait_in_dept_approve("待入库部门审批"),
    approve_fail("审核不通过"),
    wait_out_store("待出库"),
    out_stored("已出库"),
    in_stored("已入库");

    private String display;

    TransferStateEnum(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }

    public static TransferStateEnum fromName(String name) {
        for (TransferStateEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
