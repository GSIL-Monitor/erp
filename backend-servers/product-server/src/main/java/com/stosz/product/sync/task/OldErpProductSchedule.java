package com.stosz.product.sync.task;

import com.stosz.product.sync.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 老erp产品相关的数据同步
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/30]
 */
@Component
@EnableScheduling
public class OldErpProductSchedule {

    @Resource
    private OldErpCategorySyncService oldErpCategorySyncService;
    @Resource
    private OldErpAttributeSyncService oldErpAttributeSyncService;
    @Resource
    private OldErpAttributeValueSyncService oldErpAttributeValueSyncService;
    @Resource
    private OldErpProductSkuSyncService oldErpProductSkuSyncService;
    @Resource
    private OldErpProductZoneSyncService oldErpProductZoneSyncService;
    @Resource
    private OldErpQuantitySyncService oldErpQuantitySyncService;
    @Resource
    private OldErpZoneSyncService oldErpZoneSyncService;
    @Resource
    private OldErpCountrySyncService oldErpCountrySyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 老erp分类的同步
     */
//    @Scheduled(cron = "0 0 0/8 * * ? ")
    public void categorySync() {
        logger.info("======================老erp分类同步调度开始了！====================");
        oldErpCategorySyncService.push();
        logger.info("======================老erp分类同步调度结束了！====================");
    }

    /**
     * 老erp属性的同步，属性部分不自动推送，跟随产品推送
     */
//    @Scheduled(cron = "0 10 0/8 * * ?")
    @Deprecated
    public void attributeSync() {
        logger.info("======================老erp产品属性同步调度开始了！====================");
        Future<Object> future = oldErpAttributeSyncService.push(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("老erp产品属性同步时出现异常！", e);
            throw new RuntimeException("老erp产品属性同步时出现异常！", e);
        }
        logger.info("======================老erp产品属性同步调度完成了！====================");
    }


    /**
     * 老erp属性的同步,目前不自动推送，跟随产品一起推送
     */
//    @Scheduled(cron = "0 40 0/8 * * ?")
    @Deprecated
    public void attributeValueSync() {
        logger.info("======================pull老erp产品属性值同步调度开始了！====================");
        Future<Object> future = oldErpAttributeValueSyncService.push(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("老erp产品属性值同步时出现异常！", e);
            throw new RuntimeException("老erp产品属性值同步时出现异常！", e);
        }
        logger.info("======================pull老erp产品属性值同步调度结束了！====================");
    }

    /**
     * 老erp产品sku的同步
     */
//    @Scheduled(cron = "0 40 0/8 * * ?")
    public void productSkuSync(){
        logger.info("======================老erp产品sku表同步开始===========================");
        Future<Object> future = oldErpProductSkuSyncService.push(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("老erp产品SKU同步时出现异常！", e);
            throw new RuntimeException("老erp产品SKU同步时出现异常！", e);
        }
        logger.info("======================老erp产品sku表同步结束===========================");
    }

    /**
     * 老erp产品区域信息同步
     *  目前产品区域信息需要与产品一起推送，不能调度自动推送
     */
    //    @Scheduled(cron = "0 40 0/8 * * ?")
    @Deprecated
    public void productZoneSync() {
        logger.info("======================老erp产品区域表同步开始===========================");
        Future<Object> future = oldErpProductZoneSyncService.push(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("老erp产品区域同步时出现异常！", e);
            throw new RuntimeException("老erp产品区域同步时出现异常！", e);
        }
        logger.info("======================老erp产品区域表同步结束===========================");
    }

    //    @Scheduled(cron = "0 40 0/8 * * ?")
    public void countrySync() {
        logger.info("====================== 老erp 国家表同步开始 ============================");
        Future<Object> future = oldErpCountrySyncService.push();
        try {
            Object object = future.get();
            logger.info("{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("老erp国家同步时出现异常！", e);
            throw new RuntimeException("老erp国家同步时出现异常！", e);
        }
        logger.info("====================== 老erp 国家表同结束 ============================");
    }

    //    @Scheduled(cron = "0 40 0/8 * * ?")
    public void zoneSync() {
        logger.info("====================== 老erp 区域表同步开始 ============================");
        Future<Object> future = oldErpZoneSyncService.push();
        try {
            Object object = future.get();
            logger.info("区域同步返回：{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("老erp区域同步时出现异常！", e);
            throw new RuntimeException("老erp区域同步时出现异常！", e);
        }
        logger.info("====================== 老erp 区域表同结束 ============================");
    }


//    @Scheduled(cron = "0 0 * * * ?")
    public void stockSync() {
        logger.info("====================== 同步产品区域表库存开始 ============================");
        Future<Object> future = oldErpQuantitySyncService.pullHasStockByDate(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("库存信息同步线程返回：{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步产品区域表库存时出现异常！", e);
            throw new RuntimeException("同步产品区域表库存时出现异常！", e);
        }
        logger.info("====================== 同步产品区域表库存结束 ============================");
    }


    @Scheduled(cron = "0 20 * * * ?")
    public void lastOrderAtSync() {
        logger.info("====================== 同步最后下单时间开始 ============================");
        Future<Object> future = oldErpQuantitySyncService.pullLastOrderDate(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("最后下单时间线程返回:{}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步产品区域表最后下单时间时出现异常！", e);
            throw new RuntimeException("同步产品区域表最后下单时间时出现异常！", e);
        }
        logger.info("====================== 同步最后下单时间结束 ============================");
    }
    
}  
