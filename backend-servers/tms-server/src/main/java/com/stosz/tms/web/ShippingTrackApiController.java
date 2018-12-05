package com.stosz.tms.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stosz.plat.common.RestResult;
import com.stosz.tms.model.ShippingTrackApi;
import com.stosz.tms.service.ShippingTrackApiService;

@RestController
@RequestMapping("/tms/trackApi")
public class ShippingTrackApiController {

    @Resource
    private ShippingTrackApiService service;

    @RequestMapping(method = RequestMethod.POST)
    public RestResult query(ShippingTrackApi trackApi) {
        RestResult restResult = new RestResult();
        int count = service.count(trackApi);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

        List<ShippingTrackApi> trackApis = service.queryList(trackApi);
        restResult.setItem(trackApis);
        restResult.setCode(RestResult.OK);
        return restResult;
    }
    
}
