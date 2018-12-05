package com.stosz.store.service;

import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStorehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 15:33
 * @Update Time:
 */

@Component
public class WmsServiceImpl implements IStorehouseService {


    @Autowired
    private WmsService wmsService;


    private Logger logger = LoggerFactory.getLogger(WmsServiceImpl.class);

    @Override
    public Wms wmsTransferRequest(Integer wmsId) {

        Assert.notNull(wmsId, "wmsId不能为空");
        return wmsService.findById(wmsId);
    }

    @Override
    public List<Wms> wmsTransferRequest(Wms wms) {
        return wmsService.query(wms);
    }

    @Override
    public List<Wms> findWmsByIds(List<Integer> ids) {
        return wmsService.findByIds(ids);
    }

}

