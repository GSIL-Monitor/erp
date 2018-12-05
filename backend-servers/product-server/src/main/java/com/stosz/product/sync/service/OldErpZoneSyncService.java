package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpZone;
import com.stosz.olderp.ext.service.IOldErpZoneService;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/12]
 */
@Service
public class OldErpZoneSyncService {

    @Resource
    private IOldErpZoneService iOldErpZoneService;
    @Resource
    private IZoneService IZoneService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pull() {
        logger.info("========================== 同步pull区域信息开始了 ========================");
        List<OldErpZone> oldErpZoneList = iOldErpZoneService.findAll();
        Assert.notNull(oldErpZoneList, "获取到的老erp区域信息为null！");
        Assert.notEmpty(oldErpZoneList, "获取到的老erp区域信息为空！");
        List<Zone> zoneList = new ArrayList<>();
        int success = 0;
        int count = oldErpZoneList.size();
        for (OldErpZone oldErpZone : oldErpZoneList) {
            Integer id = oldErpZone.getId();
            Zone zone = new Zone();
            zone.setId(id);
            zone.setTitle(oldErpZone.getTitle());
            zone.setCountryId(oldErpZone.getCountryId());
            zone.setParentId(oldErpZone.getParentId());
            zone.setCurrency(oldErpZone.getCurrency());
            zone.setCode(oldErpZone.getCode());
            zoneList.add(zone);
            success++;
        }
        if (!zoneList.isEmpty()) {
            IZoneService.insertList(zoneList);
        }
        logger.info("同步pull老erp区域信息成功，总记录数{}，成功记录数{}", count, success);
        logger.info("========================== 同步pull区域信息完成了 ========================");
        return new AsyncResult<>("同步pull老erp区域信息成功");
    }

    @Async
    public Future<Object> push() {
        logger.info("========================== 同步push区域信息开始了 ========================");
        List<Zone> zoneList = IZoneService.findAll();
        Assert.notNull(zoneList, "获取到的区域信息为null！");
        Assert.notEmpty(zoneList, "获取到的区域信息为空！");
        int count = zoneList.size();
        int success = 0;
        for (Zone zone : zoneList) {
            OldErpZone oldErpZone = new OldErpZone();
            oldErpZone.setCode(zone.getCode());
            oldErpZone.setCountryId(zone.getCountryId());
            oldErpZone.setCurrency(zone.getCurrency());
            oldErpZone.setTitle(zone.getTitle());
            oldErpZone.setParentId(zone.getParentId());
            OldErpZone oldErpZoneDB = iOldErpZoneService.getById(zone.getId());
            if (oldErpZoneDB == null) {
                oldErpZone.setId(zone.getId());
                iOldErpZoneService.insert(oldErpZone);
            } else {
                oldErpZone.setId(oldErpZoneDB.getId());
                iOldErpZoneService.update(oldErpZone);
            }
            success++;
        }
        logger.info("同步push 区域信息成功，总记录数{}，成功数{}", count, success);
        logger.info("========================== 同步push区域信息完成了 ========================");
        return new AsyncResult<>("同步push 区域信息成功");
    }
}  
