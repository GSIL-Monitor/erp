package com.stosz.purchase.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.EnumUtils;
import com.stosz.purchase.ext.enums.*;
import com.stosz.purchase.ext.enums.AdjustTypeEnum;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.enums.PayableStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiognchenyang
 * 采购页面基础数据
 */
@RestController
@RequestMapping("/purchase/common")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("enumList")
    public RestResult enumList() {
        RestResult result = new RestResult();
        Map<String, Object> map = new HashMap(4);
        map.put("purchaseState", EnumUtils.getEnumJsonString(PurchaseState.class));
        map.put("purchaseItemState", EnumUtils.getEnumJsonString(PurchaseItemState.class));
        map.put("purchaseReturnedState", EnumUtils.getEnumJsonString(PurchaseReturnState.class));
        map.put("purchaseReturnedType", EnumUtils.getEnumJsonString(ReturnedTypeEnum.class));
        map.put("PurchaseReturnItemState", EnumUtils.getEnumJsonString(PurchaseReturnItemState.class));
        map.put("errorGoodsState", EnumUtils.getEnumJsonString(ErrorGoodsState.class));
        map.put("errorGoodsItemState", EnumUtils.getEnumJsonString(ErrorGoodsItemState.class));
        map.put("billTypeEnum", EnumUtils.getEnumJsonString(BillTypeEnum.class));
        map.put("generateTypeEnum", EnumUtils.getEnumJsonString(PayableStateEnum.class));
        map.put("adjustTypeEnum", EnumUtils.getEnumJsonString(AdjustTypeEnum.class));
        result.setCode(RestResult.OK);
        result.setTotal(4);
        result.setItem(map);
        return result;
    }


}
