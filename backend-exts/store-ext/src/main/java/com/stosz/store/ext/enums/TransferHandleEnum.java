package com.stosz.store.ext.enums;

import com.stosz.plat.utils.IEnum;

/**
 * @Author:yangqinghui
 * @Description:订单库存操作枚举
 * @Created Time:2017/11/25 11:41
 * @Update Time:2017/11/25 11:41
 */
public enum TransferHandleEnum implements IEnum {

    transfer_apply("发起调拨单"),//发起调拨单
    transfer_cancel("调拨取消"),//调拨取消
    transfer_out_stock("调拨出库"),//调拨出库
    transfer_in_stock("调拨入库"),//退货回收取消
    transfer_intransit("调拨在途");//调拨在途

    private String display;

    TransferHandleEnum(String display) {
        this.display = display;
    }

    public String display(){
        return this.display;
    }

    public static TransferHandleEnum fromName(String name) {
        for (TransferHandleEnum orderTypeEnum : values()) {
            if (orderTypeEnum.name().equalsIgnoreCase(name)) {
                return orderTypeEnum;
            }
        }
        return null;
    }
}
