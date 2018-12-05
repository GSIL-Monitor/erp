package com.stosz.tms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.stosz.tms.mapper.ShippingTrackApiMapper;
import com.stosz.tms.model.ShippingTrackApi;

@Service
public class ShippingTrackApiService {

    @Resource
    private ShippingTrackApiMapper mapper;


    public int count(ShippingTrackApi trackApi) {
        return mapper.count(trackApi);
    }

    public List<ShippingTrackApi> queryList(ShippingTrackApi trackApi) {
        if (!StringUtils.hasText(trackApi.getOrderBy())) {
            trackApi.setOrderBy(" update_at desc,create_at");
        }
        return mapper.queryList(trackApi);
    }
    
    public ShippingTrackApi queryTrackApiById(Integer apiId) {
    	return mapper.getById(apiId);
    }
    
    public List<ShippingTrackApi> queryListAll(ShippingTrackApi trackApi) {
        return mapper.queryListAll(trackApi);
    }
    

}
