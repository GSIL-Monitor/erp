package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-12-06
 * 问题件的处理状态
 */
public enum OrderQuestionStatusEnum implements IEnum {

    NotHandle("未处理"),
    SureReject("确认拒收"),
    HasSendEmail("已发邮件"),
    CanRetreat("可退"),
    CanNotRetreat("不可退"),
    ReDelivery("重新派送"),
    Linking("联系中"),
    ;


    private String display = "";


     OrderQuestionStatusEnum(String display)
    {
        this.display = display;
    }

    @Override
    public String display() {
        return display;
    }


    public static OrderQuestionStatusEnum fromId(Integer id) {
        OrderQuestionStatusEnum orderTypeEnumFind = null;
        if (null == id  || id.intValue() < 0) return orderTypeEnumFind;

        for (OrderQuestionStatusEnum orderTypeEnum : values())
        {
            if (orderTypeEnum.ordinal() == id.intValue())
            {
                orderTypeEnumFind =  orderTypeEnum;
                break;
            }
        }

        return orderTypeEnumFind;

    }



}
