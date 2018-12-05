package com.stosz.crm.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * 老ERP订单装啊提
 */
public enum OldErpOrderState implements IEnum {

    OTHER("未知状态"),
    UNPROCESS ("未处理"), //
    PROCESSING ("待处理"), //
    VERIFICATION ("待审核"), //
    UNPICKING ("未配货"), //
    PICKING ("配货中"), //
    OUT_STOCK ("缺货"), //
    PICKED ("已配货"), //
    DELIVERING ("配送中"), //
    SIGNED ("已签收"), //
    RETURNED ("已退货"), //
    REPEAT ("重复下单"), //
    IMPERFECT ("信息不完整"), //
    MALICE ("恶意下单"), //
    CANCELED ("客户取消"), //
    DAMAGED ("质量问题"), //
    REJECTION ("拒收"), //
    PART_OUT_STOCK ("部分缺货"), //
    PACKAGED ("已打包"), //
    CLAIMS ("理赔"), //
    RETURN_WAREHOUSE ("退货入库"), //
    APPROVED ("已审核"),  //
    PROBLEM ("问题件"), //
    FORWARD ("已转寄"),  //
    MATCH_FORWARDING ("匹配转寄中"), //
    MATCH_FORWARDED ("已匹配转寄状态"), //
    MATCH_FINISH ("转寄完成"); //

    OldErpOrderState(String display) {
        this.display = display;
    }

    private String display;

    @Override
    public String display() {
        return this.display;
    }

    public static OldErpOrderState fromName(String name) {
        for (OldErpOrderState orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }

    public static OldErpOrderState fromId(Integer id) {
        for (OldErpOrderState orderTypEnum : values()) {
            if (orderTypEnum.ordinal() == id) {
                return orderTypEnum;
            }
        }
        return null;
    }

}
