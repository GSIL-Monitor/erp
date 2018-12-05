package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import com.stosz.product.ext.model.ProductAttributeRel;
import com.stosz.product.service.ProductAttributeRelService;
import com.stosz.product.sync.thread.OldErpAttributeSyncThread;
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
 * 老erp 属性和产品-属性关联的service同步
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/30]
 */
@Service
public class OldErpAttributeSyncService {

    @Resource
    private IOldErpAttributeService iOldErpAttributeService;
    @Resource
    private OldErpAttributeSyncThread oldErpAttributeSyncThread;
    @Resource
    private ProductAttributeRelService productAttributeRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int limit = 2000;//一次查询老erp数据2000条

    /**
     * 拉取老erp属性表，同步到新erp
     */
    @Async
    public Future<Object> pull() {
        logger.info("=============================== 拉取老erp的产品属性信息开始了！=========================");
        Integer count = iOldErpAttributeService.count();
        int success = 0;
        Assert.notNull(count, "pull老erp数据的总记录数失败，返回的记录数为null");
        Assert.isTrue(count > 0, "pull老erp数据的总记录数失败，返回的记录条数为0");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int start = 0; start <= count; start = start + limit) {
            List<OldErpAttribute> oldErpAttributeList = iOldErpAttributeService.findByLimit(limit, start);
            Future<Integer> future = oldErpAttributeSyncThread.pull(oldErpAttributeList);
            futureList.add(future);
            logger.info("同步老erp产品属性表，pull操作成功，产品属性的集合为{}", oldErpAttributeList);
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.info("同步老erp产品属性表时出现异常", e);
                throw new RuntimeException("同步老erp产品属性表时出现异常", e);
            }
        }
        logger.info("同步老erp产品属性成功，总记录数{}，成功数{}", count, success);
        logger.info("=============================== 拉取老erp的产品属性信息完成了！=========================");
        return new AsyncResult<>("拉取老erp全部的属性信息成功！");
    }

    /**
     * 将新erp数据推送到老erp
     * 目前不开启自动推送产品属性值的功能
     */
    @Async
    @Deprecated
    public Future<Object> push(LocalDateTime startTime, LocalDateTime endTime) {
        logger.info("=============================== 推送产品属性信息到老erp开始了！=========================");
        Assert.notNull(startTime, "推送属性到老erp的起始时间不允许为空！");
        Assert.notNull(endTime, "推送属性到老erp的结束时间不允许为空！");
        int count = productAttributeRelService.countByDate(startTime, endTime);
        List<Future<Integer>> futureList = new ArrayList<>();
        ProductAttributeRel param = new ProductAttributeRel();
        param.setMinCreateAt(startTime);
        param.setMaxCreateAt(endTime);
        int success = 0;
        if (count > 0) {
            int start = 0;
            for (; start < count; start = start + limit) {
                param.setStart(start);
                List<ProductAttributeRel> productAttributeRels = productAttributeRelService.findByDate(param);
                if (!productAttributeRels.isEmpty()) {
                    Future<Integer> future = oldErpAttributeSyncThread.push(productAttributeRels);
                    futureList.add(future);
                }
            }
            for (Future<Integer> future : futureList) {
                try {
                    Integer i = future.get();
                    success += i;
                } catch (InterruptedException | ExecutionException | RuntimeException e) {
                    logger.info("推送属性到老erp的时候出现异常！", e);
                    throw new RuntimeException("推送属性到老erp的时候出现异常！", e);
                }
            }
        }
        logger.info("============= 推送属性到老erp成功，总数{}，成功数{}==============", count, success);
        logger.info("=============================== 推送产品属性信息到老erp完成了！=========================");
        return new AsyncResult<>("将新erp的属性信息推送到老erp成功！");
    }


}
