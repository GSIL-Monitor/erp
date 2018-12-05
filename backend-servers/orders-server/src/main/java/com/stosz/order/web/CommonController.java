package com.stosz.order.web;

import com.stosz.order.ext.enums.*;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.enums.TimeRegionEnum;
import com.stosz.plat.utils.EnumUtils;
import com.stosz.plat.utils.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author wangqian
 * 订单页面基础数据
 */
@RestController
@RequestMapping("orders/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("enumList")
    public RestResult enumList() {
        RestResult result = new RestResult();
        Map<String, Object> map = new HashMap(8);
        List<Map<String, Object>> orderStateEnum = EnumUtils.getEnumJsonString(OrderStateEnum.class);
        removeEnum(orderStateEnum, OrderStateEnum.start);
        map.put("orderStateEnum", orderStateEnum);
        map.put("operationEnum", EnumUtils.getEnumJsonString(OperationEnum.class));
        map.put("payMethodEnum", EnumUtils.getEnumJsonString(PayMethodEnum.class));
        map.put("codeStateEnum", EnumUtils.getEnumJsonString(TelCodeState.class));
        map.put("orderQuestionStatusEnum", EnumUtils.getEnumJsonString(OrderQuestionStatusEnum.class));
        map.put("timeRegionEnum", EnumUtils.getEnumJsonString(TimeRegionEnum.class));
        map.put("ordersSupplementReasonEnum", EnumUtils.getEnumJsonString(OrdersSupplementReasonEnum.class));
        map.put("orderCancelReasonEnum", EnumUtils.getEnumJsonString(OrderCancelReasonEnum.class));
        List<Map<String, Object>> useableEnum = EnumUtils.getEnumJsonString(UseableEnum.class);
        removeEnum(useableEnum, UseableEnum.other);
        map.put("useableEnum", useableEnum);
        List<Map<String, Object>> refundStatusEnum = EnumUtils.getEnumJsonString(OrdersRefundStatusEnum.class);
        removeEnum(refundStatusEnum, OrdersRefundStatusEnum.start);
        map.put("refundStatusEnum", refundStatusEnum);
        map.put("refundMethodEnum", EnumUtils.getEnumJsonString(OrdersRefundMethodEnum.class));
        map.put("refundTypeEnum", EnumUtils.getEnumJsonString(OrdersRefundTypeEnum.class));
        List<Map<String, Object>> ordersRmaStateEnum =  EnumUtils.getEnumJsonString(OrdersRmaStateEnum.class);
        removeEnum(ordersRmaStateEnum, OrdersRmaStateEnum.start);
        map.put("ordersRmaStateEnum",ordersRmaStateEnum);
        map.put("changeTypeEnum", EnumUtils.getEnumJsonString(ChangeTypeEnum.class));
        map.put("recycleGoodsEnum", EnumUtils.getEnumJsonString(RecycleGoodsEnum.class));
        map.put("rmaSourceEnum", EnumUtils.getEnumJsonString(RmaSourceEnum.class));
        map.put("changeReasonEnum", EnumUtils.getEnumJsonString(ChangeReasonEnum.class));
        result.setCode(RestResult.OK);
        result.setTotal(9);
        result.setItem(map);
        return result;
    }

    private void removeEnum(List orderStateEnum, IEnum e) {
        for (Iterator<Map<String, Object>> iter = orderStateEnum.iterator(); iter.hasNext(); ) {
            Map<String, Object> next = iter.next();
            for (String key : next.keySet()) {
                if (next.get(key).equals(e.name())) {
                    iter.remove();
                }
            }
        }
    }


}
