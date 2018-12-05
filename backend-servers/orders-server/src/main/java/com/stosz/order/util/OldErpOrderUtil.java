package com.stosz.order.util;


import com.stosz.crm.ext.enums.OrderStatusEnum;

public class OldErpOrderUtil {

    public static OrderStatusEnum getOrderType(Integer status){
        if(status == 16 || status == 24 ){
            //拒收
            return OrderStatusEnum.reject;
        }else if(status == 10 || status == 21){
            //退货
            return OrderStatusEnum.reject;
        }else if(status == 9){
            //成功签收
            return OrderStatusEnum.sign;
        }
        //其它状态
        return OrderStatusEnum.other;
    }
}
