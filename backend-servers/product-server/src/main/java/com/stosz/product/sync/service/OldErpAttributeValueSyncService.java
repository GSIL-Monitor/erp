package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.olderp.ext.service.IOldErpAttributeValueService;
import com.stosz.product.ext.model.ProductAttributeValueRel;
import com.stosz.product.service.ProductAttributeValueRelService;
import com.stosz.product.sync.thread.OldErpAttributeValueSyncThread;
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
 * 老erp产品属性值
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/31]
 */
@Service
public class OldErpAttributeValueSyncService {

    @Resource
    private IOldErpAttributeValueService iOldErpAttributeValueService;
    @Resource
    private OldErpAttributeValueSyncThread oldErpAttributeValueSyncThread;
    @Resource
    private ProductAttributeValueRelService productAttributeValueRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int limit = 2000;

    @Async
    public Future<Object> pull() {
        logger.info("=============================== 拉取老erp产品属性值开始了！==========================");
        Integer count = iOldErpAttributeValueService.count();
        int success = 0;
        Assert.notNull(count, "pull老erp数据的总记录数失败，返回的记录数为null");
        Assert.isTrue(count > 0, "pull老erp数据的总记录数失败，返回的记录条数为0");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int start = 0; start <= count; start = start + limit) {
            List<OldErpAttributeValue> oldErpAttributeValueList = iOldErpAttributeValueService.findByLimit(limit, start);
            Future<Integer> future = oldErpAttributeValueSyncThread.pull(oldErpAttributeValueList);
            futureList.add(future);
            logger.info("同步老erp产品属性表，pull操作成功，产品属性的集合为{}", oldErpAttributeValueList);
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("拉取老erp产品属性值时出现异常！", e);
                throw new RuntimeException("拉取老erp产品属性值时出现异常！", e);
            }
        }
        logger.info("拉取老erp产品属性值成功，总记录数{}，成功数{}", count, success);
        logger.info("=============================== 拉取老erp产品属性值结束了！==========================");
        return new AsyncResult<>("拉取老erp产品属性值成功！");
    }

    /**
     * 目前不开启自动推送产品属性值的功能
     */
    @Async
    @Deprecated
    public Future<Object> push(LocalDateTime startTime, LocalDateTime endTime) {
        logger.info("=============================== 推送产品属性值到老erp开始了！==========================");
        int count = productAttributeValueRelService.countByDate(startTime, endTime);
        int start = 0;
        int success = 0;
        ProductAttributeValueRel param = new ProductAttributeValueRel();
        List<Future<Integer>> futureList = new ArrayList<>();
        param.setMinCreateAt(startTime);
        param.setMaxCreateAt(endTime);
        param.setLimit(limit);
        if (count > 0) {
            for (; start <= count; start = start + limit) {
                param.setStart(start);
                List<ProductAttributeValueRel> productAttributeValueRelList = productAttributeValueRelService.findByDate(param);
                if (productAttributeValueRelList.isEmpty()) {
                    break;
                } else {
                    Future<Integer> future = oldErpAttributeValueSyncThread.push(productAttributeValueRelList);
                    futureList.add(future);
                }
            }
            for (Future<Integer> future : futureList) {
                try {
                    Integer i = future.get();
                } catch (InterruptedException | ExecutionException | RuntimeException e) {
                    logger.error("推送产品属性到老erp时出现异常！", e);
                }
            }
        }
        logger.info("推送产品属性值到老erp成功，推送时间段{}~~{} ，总记录数{}，成功数{}", startTime, endTime, count, success);
        logger.info("=============================== 推送产品属性值到老erp结束了！==========================");
        return new AsyncResult<>("推送产品属性值到老erp成功");
    }



}  
