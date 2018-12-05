package com.stosz.product.ext.enums;

import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

public enum ProductNewEvent implements IEnum,FsmEventEnum {

    create("创建"),
    submit("提交"),
    verify("审核"),
    assign("细化分类"),
    match("图片匹配"),
    backToCreated("驳回"),
    checkOk("排查通过"),
    goback("返回细化分类"),
    warn("预警"),
    duplicate("重复"),
    decline("拒绝"),
    requestApprove("申请审批"),
    archive("建档"),
    giveUp("放弃"),


    ;

    private String display;

    ProductNewEvent(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static ProductNewEvent fromName(String name) {
        for (ProductNewEvent orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
