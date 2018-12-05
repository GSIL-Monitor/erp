package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Description: 失败原因信息
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public enum ErrMsgEnum implements IEnum {

    errTrackingNo("未找到此运单，请检查运单号是否正确"),

    errOperate("系统操作异常，请重试"),

    duplicate("已入转寄仓，请检查是否重复导入"),

    errState("包裹状态异常，不允许导入，请与技术部联系"),

    errOrderId("未找到此订单号，请检查订单号是否正确"),

    delivered("已发货导入，请检查是否重复导入"),

    invalided("已报废，请检查是否重复导入"),

    errOrNoTrackingNo("操作失败，可能原因：未找到此运单，请检查运单号是否正确"),

    errWms("入库失败，导入仓库与物流指定仓库不一致")
    ;

    private String display;

    ErrMsgEnum(String display) {
        this.display = display;
    }

    @Override
    public String display() {
        return this.display;
    }

    public static ErrMsgEnum fromName(String name) {
        for (ErrMsgEnum errEnum : values()) {
            if (errEnum.name().equalsIgnoreCase(name)) {
                return errEnum;
            }
        }
        return null;
    }

    public static ErrMsgEnum fromId(Integer id) {
        for (ErrMsgEnum errEnum : values()) {
            if (errEnum.ordinal() == id) {
                return errEnum;
            }
        }
        return null;
    }
}
