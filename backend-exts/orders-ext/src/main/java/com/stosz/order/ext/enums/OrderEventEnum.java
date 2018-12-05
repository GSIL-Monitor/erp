package com.stosz.order.ext.enums;


import com.stosz.fsm.enums.FsmEventEnum;
import com.stosz.plat.utils.IEnum;

/**
 * @auther carter
 * create time    2017-11-17
 */
public enum OrderEventEnum  implements IEnum,FsmEventEnum {

    create("订单创建导入"),
    checkLinkInfo("检查联系方式"),
    checkInvalid("检查无效情况"),
    checkValid("检查有效"),
    linkSuccess("联系成功"),
    linkFail("联系失败"),
    cancel("客户取消"),
    revoke("撤回"),
    lockStockSuccess("锁定库存成功"),
    lockStockFail("锁定库存失败"),
    stockChange("库存变化"),
    logisticResponseOk("自建仓物流系统指派物流成功"),
    forwardResponseOk("转寄仓无需指定物流，直接变为待发货状态"),
    wmsOutCallback("WMS出库回调"),
    pushForward("推送转寄仓"),
    matchSign("匹配物流签收"),
    matchNotSign("匹配物流拒收"),
    matchLost("匹配物流丢件"),
    innerOrderProcess("内部订单流转"),
    innerCreate("内部创建订单"),
    transferCancel("转寄仓库盘亏"),
    forwardImportOk("转寄仓发货导入成功")
    ;

    private String name;

    OrderEventEnum(String name)
    { this.name = name;}


    @Override
    public String display() {
        return name;
    }

    public String getName() {
        return name;
    }


    public static OrderEventEnum fromName(String name) {
        for (OrderEventEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }




}
