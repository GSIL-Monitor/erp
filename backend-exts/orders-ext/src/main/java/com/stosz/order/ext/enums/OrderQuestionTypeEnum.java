package com.stosz.order.ext.enums;

import com.stosz.plat.utils.IEnum;
import com.stosz.plat.utils.StringUtils;

/**
 * 问题件问题类型
 *
 * @auther tangtao
 * create time    2017-12-12
 */
public enum OrderQuestionTypeEnum implements IEnum {
    ContReach("联系不上"),
    WrongAddress("地址错误"),
    Rejection("拒收"),
    Refund("退货"),
    Exchange("换货"),
    Other("其它"),;

    OrderQuestionTypeEnum(String display) {
        this.display = display;
    }

    private String display = "";

    @Override
    public String display() {
        return display;
    }

    public static OrderQuestionTypeEnum fromId(Integer id) {
        OrderQuestionTypeEnum orderQuestionTypeEnumFind = null;
        if (null == id || id.intValue() < 0) return orderQuestionTypeEnumFind;

        for (OrderQuestionTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.ordinal() == id.intValue()) {
                orderQuestionTypeEnumFind = orderTypeEnum;
                break;
            }
        }
        return orderQuestionTypeEnumFind;
    }

    public static OrderQuestionTypeEnum fromDisplay(String name) {
        OrderQuestionTypeEnum orderQuestionTypeEnumFind = null;
        if (StringUtils.isEmpty(name)) return orderQuestionTypeEnumFind;

        for (OrderQuestionTypeEnum orderTypeEnum : values()) {
            if (orderTypeEnum.display().equals(name)) {
                orderQuestionTypeEnumFind = orderTypeEnum;
                break;
            }
        }
        return orderQuestionTypeEnumFind;
    }
}
