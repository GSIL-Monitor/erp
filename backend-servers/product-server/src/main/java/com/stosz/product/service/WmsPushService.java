package com.stosz.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.stosz.product.ext.model.WmsPush;
import com.stosz.product.mapper.WmsPushMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @auther carter
 * create time    2017-11-07
 */
@Service
public class WmsPushService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WmsPushMapper wmsPushMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public Long insert(Long objectId, String objectType,String url, String param) {
        Assert.isTrue(null != objectId && objectId.longValue() > 0, "objectId为正整数");
        Assert.isTrue(!Strings.isNullOrEmpty(objectType),"objectType不能为空");
        Assert.isTrue(!Strings.isNullOrEmpty(url),"url不能为空");
        Assert.isTrue(!Strings.isNullOrEmpty(param),"param不能为空");

        WmsPush wmsPush = new WmsPush();
        wmsPush.setObjectId(objectId);
        wmsPush.setObjectType(objectType);
        wmsPush.setPushUrl(url);
        wmsPush.setPushParam(param);

        wmsPushMapper.insert(wmsPush);

        return Long.valueOf(wmsPush.getId());
    }


    private boolean update(Long id,String responseMessage)
    {
        Assert.isTrue(null!=id && id.longValue()>0,"id为正整数");
        Assert.isTrue(!Strings.isNullOrEmpty(responseMessage),"responseMessage不能为空");

        if(responseMessage.length()>255) responseMessage=responseMessage.substring(0,254);

        Integer pushStatus = -1;
        Map map = null;
        try {
             map = objectMapper.readValue(responseMessage, Map.class);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        if(null != map && "true".equalsIgnoreCase(map.get("isSuccess").toString()))
        {
            pushStatus = 1;
        }

        Integer updateResult = wmsPushMapper.update(id, pushStatus,responseMessage);
        return   null!=updateResult&&updateResult>0;

    }

    public List<WmsPush> getByStatus(Integer pushStatus)
    {
        Assert.isTrue(null != pushStatus && Arrays.asList(-1, 1, 0).contains(pushStatus), "pushStatus为0,1或者-1");
        return wmsPushMapper.getByStatus(pushStatus);
    }


    public void pushResult(Long id, String responseString) {
        update(id,responseString);
    }
}
