package com.stosz.order.ext.enums;


import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * 
 * 退货操作Event枚举
 * @author liusl
 */
public enum OrderRmaBillEventEnum  implements IEnum,FsmEventEnum {

    create("订单创建(退货申请单)"),
    createReject("拒收申请单"),
    update("订单修改"),
    submitAudit("提交审核"),
    changeAuditPass("审核通过(商品寄回)"),
    changeAuditPassBut("审核通过(商品不寄回)"),
    updateTrackingNo("填写运单号"),
    changeReject("审核不通过"),
    sign("客户签收"),
    warehousing("退货/拒收入库"),
    changeCancel("取消");

    private String name;

    OrderRmaBillEventEnum(String name)
    { this.name = name;}


    @Override
    public String display() {
        return name;
    }

    public String getName() {
        return name;
    }


    public static OrderRmaBillEventEnum fromName(String name) {
        for (OrderRmaBillEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }




}
