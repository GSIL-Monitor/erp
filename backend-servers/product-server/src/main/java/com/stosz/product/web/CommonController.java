package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.EnumUtils;
import com.stosz.product.ext.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品页面枚举
 */
@RestController
@RequestMapping("product/commons")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @GetMapping("/enums")
    public RestResult enumList(){
       RestResult result = new RestResult();
       Map<String , Object> map = new HashMap();
       map.put("productEventEnum",EnumUtils.getEnumJsonString(ProductEvent.class));
       map.put("productNewEventEnum",EnumUtils.getEnumJsonString(ProductNewEvent.class));
       map.put("productStateEnum",EnumUtils.getEnumJsonString(ProductState.class));
       map.put("productZoneEventEnum",EnumUtils.getEnumJsonString(ProductZoneEvent.class));
       map.put("productZoneStateEnum",EnumUtils.getEnumJsonString(ProductZoneState.class));
       result.setCode(RestResult.OK);
       result.setTotal(map.size());
       result.setItem(map);
       return result;
    }

    @GetMapping("/productStateEnum")
    public RestResult ProductStateEnum(){
        RestResult result = new RestResult();
        result.setCode(RestResult.OK);
        result.setItem(EnumUtils.getEnumJsonString(ProductState.class));
        return result;
    }


}
