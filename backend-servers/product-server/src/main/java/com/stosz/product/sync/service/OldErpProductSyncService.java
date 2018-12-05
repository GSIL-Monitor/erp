package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.olderp.ext.service.IOldErpProductService;
import com.stosz.product.sync.thread.OldErpProductSyncThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 老erp 产品相关信息的同步
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/1]
 */
@Service
public class OldErpProductSyncService {

    @Resource
    private IOldErpProductService iOldErpProductService;
    @Resource
    private OldErpProductSyncThread oldErpProductSyncThread;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int limit = 2000;

    /**
     * 该操作只需要执行一次，所以直接弄全量的
     */
    @Async
    public Future<Object> pull() {
        Integer count = iOldErpProductService.count();
        Integer success = 0;
        Assert.notNull(count, "pull老erp产品的总记录数失败，返回的记录数为null");
        Assert.isTrue(count > 0, "pull老erp产品的总记录数失败，返回的记录条数为0");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int start = 0; start <= count; start = start + limit) {
            List<OldErpProduct> oldErpProductList = iOldErpProductService.findByLimit(limit, start);
            Future<Integer> future = oldErpProductSyncThread.pullList(oldErpProductList);
            futureList.add(future);
            logger.info("同步老erp产品表，pull操作成功，产品的集合为{}", oldErpProductList);
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("同步老erp产品表时，出现异常！", e);
                throw new RuntimeException("同步老erp产品表时，出现异常！", e);
            }
        }
        logger.info("同步pull老erp产品表成功，产品总数为{}，成功数为{}", count, success);
        return new AsyncResult<>("同步pull老erp产品表成功");
    }


}  
