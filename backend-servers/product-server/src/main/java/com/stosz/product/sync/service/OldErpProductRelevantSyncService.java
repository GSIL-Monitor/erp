package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.olderp.ext.service.IOldErpProductService;
import com.stosz.product.sync.thread.OldErpProductRelevantSyncThread;
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
 * 所有产品相关的老erp信息拉取
 * 废弃了
 * @author xiongchenyang
 * @version [1.0 , 2017/9/20]
 */
@Service
public class OldErpProductRelevantSyncService {

    @Resource
    private IOldErpProductService iOldErpProductService;
    @Resource
    private OldErpProductRelevantSyncThread oldErpProductRelevantSyncThread;

    public static final int limit = 2000;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public Future<Object> pull() {
        List<Integer> idList = iOldErpProductService.findAllId();
        Assert.notNull(idList, "获取老erp的id集合失败！");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (Integer id : idList) {
            OldErpProduct oldErpProduct = iOldErpProductService.getById(id);
            Assert.notNull(oldErpProduct, "根据id获取到的老erp产品为空！");
            Future<Integer> future1 = null;
            try {
                future1 = oldErpProductRelevantSyncThread.syncOne(oldErpProduct);
            } catch (ExecutionException | InterruptedException | RuntimeException e) {
                logger.error("同步产品时，出现异常!", e);
                throw new RuntimeException("同步产品时，出现异常!", e);
            }
            futureList.add(future1);
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                logger.info("同步产品id【{}】的所有信息完成！", i);
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("同步老erp产品表时，出现异常！", e);
                throw new RuntimeException("同步老erp产品表时，出现异常！", e);
            }
        }
        logger.info("同步pull老erp产品表成功，产品总数为{}", idList.size());
        return new AsyncResult<>("同步pull老erp产品表成功");
    }

}  
