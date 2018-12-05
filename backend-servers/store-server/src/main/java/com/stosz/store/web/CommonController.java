package com.stosz.store.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.EnumUtils;
import com.stosz.store.ext.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangqian
 * 仓储页面基础数据
 */
@RestController
@RequestMapping("store/common")
public class CommonController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("enumList")
    public RestResult enumList() {
        RestResult result = new RestResult();
        Map<String, Object> map = new HashMap(8);
        map.put("invoicingTypeEnum", EnumUtils.getEnumJsonString(InvoicingTypeEnum.class));
        map.put("orderHandleEnum", EnumUtils.getEnumJsonString(OrderHandleEnum.class));
        map.put("purchaseHandleEnum", EnumUtils.getEnumJsonString(PurchaseHandleEnum.class));
        map.put("stockStateEnum", EnumUtils.getEnumJsonString(StockStateEnum.class));
        map.put("transferHandleEnum", EnumUtils.getEnumJsonString(TransferHandleEnum.class));
        map.put("transferStateEnum", EnumUtils.getEnumJsonString(TransferStateEnum.class));
        map.put("transferTypeEnum", EnumUtils.getEnumJsonString(TransferTypeEnum.class));
        map.put("transitStateEnum", EnumUtils.getEnumJsonString(TransitStateEnum.class));
        map.put("wmsTypeEnum", EnumUtils.getEnumJsonString(WmsTypeEnum.class));
        map.put("usesWmsEnum", EnumUtils.getEnumJsonString(UsesWmsEnum.class));
        map.put("invalidTypeEnum", EnumUtils.getEnumJsonString(InvalidTypeEnum.class));
        map.put("calculateTypeEnum", EnumUtils.getEnumJsonString(CalculateTypeEnum.class));
        map.put("takeStockStateEnum", EnumUtils.getEnumJsonString(TakeStockStateEnum.class));
        result.setCode(RestResult.OK);
        result.setTotal(map.size());
        result.setItem(map);
        return result;
    }


}
