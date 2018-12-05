package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpProductZone;
import com.stosz.olderp.ext.model.OldErpProductZoneDomain;
import com.stosz.olderp.ext.service.IOldErpProductZoneDomainService;
import com.stosz.olderp.ext.service.IOldErpProductZoneService;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductZoneService;
import com.stosz.product.sync.thread.OldErpProductZoneSyncThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/6]
 */
@Service
public class OldErpProductZoneSyncService {

    @Resource
    private IOldErpProductZoneService iOldErpProductZoneService;
    @Resource
    private IOldErpProductZoneDomainService iOldErpProductZoneDomainService;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private OldErpProductZoneSyncThread oldErpProductZoneSyncThread;


    public static final int limit = 1000;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public Future<Object> pull() {
        Integer count = iOldErpProductZoneService.count();
        Integer success = 0;
        Assert.notNull(count, "pull老erp产品区域的总记录数失败，返回的记录数为null");
        Assert.isTrue(count > 0, "pull老erp产品区域的总记录数失败，返回的记录条数为0");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int start = 0; start < count; start = start + limit) {
            List<OldErpProductZone> oldErpProductZones = iOldErpProductZoneService.findByLimit(limit, start);
            Future<Integer> future = oldErpProductZoneSyncThread.pull(oldErpProductZones);
            futureList.add(future);
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.info("同步老erp产品区域信息时，出现异常！", e);
                throw new RuntimeException("同步老erp产品区域信息时，出现异常！", e);
            }
        }
        logger.info("同步老erp产品区域表，pull操作成功，总记录数{}，成功总数{}", count, success);
        return new AsyncResult<>("同步老erp产品区域表，pull操作成功");
    }


    /**
     * 目前不开启自动推送产品区域的功能
     */
    @Async
    @Deprecated
    public Future<Object> push(LocalDateTime startTime, LocalDateTime endTime) {
        ProductZone param = new ProductZone();
        param.setMinCreateAt(startTime);
        param.setMaxCreateAt(endTime);
        param.setLimit(limit);
        Integer count = productZoneService.countByDate(startTime, endTime);
        List<Future<Integer>> futureList = new ArrayList<>();
        Integer success = 0;
        if (count > 0) {
            int start = 0;
            for (; start <= count; start = start + limit) {
                List<ProductZone> productZoneList = productZoneService.findByDate(param);
                if (productZoneList == null || productZoneList.isEmpty()) {
                    logger.info("从{} 到{} 时间段内，已经没有要push的产品区域信息了！", startTime, endTime);
                    break;
                }
                Future<Integer> future = oldErpProductZoneSyncThread.push(productZoneList);
                futureList.add(future);
            }
            for (Future<Integer> future : futureList) {
                try {
                    Integer i = future.get();
                    success += i;
                } catch (InterruptedException | ExecutionException | RuntimeException e) {
                    logger.error("从{} 到{} 时间段内，push产品区域信息时出现异常！", startTime, endTime, e);
                }
            }
        }
        logger.info("从{} 到{} 时间段内，推送产品区域信息到老erp成功,总记录数{}，成功数{}！", startTime, endTime, count, success);
        return new AsyncResult<>("推送产品区域信息到老erp成功！");
    }


    @Async
    public Future<Object> pullDomain() {
        int count = iOldErpProductZoneDomainService.count();
        Assert.isTrue(count > 1, "同步产品区域时出错，获取到的记录数为0！");
        int success = 0;
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int start = 0; start <= count; start = start + limit) {
            List<OldErpProductZoneDomain> list = iOldErpProductZoneDomainService.findByLimit(limit, start);
            if (!list.isEmpty()) {
                Future<Integer> future = oldErpProductZoneSyncThread.pullProductZoneDomain(list);
                futureList.add(future);
            }
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.info("同步老erp产品区域域名时，出现异常！", e);
                throw new RuntimeException("同步老erp产品区域域名时，出现异常！", e);
            }
        }
        logger.info("同步老erp产品区域域名成功，总数{}，成功数{}", count, success);
        return new AsyncResult<>("同步产品区域域名成功！");
    }



}  
