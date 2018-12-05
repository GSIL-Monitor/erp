package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpProductSku;
import com.stosz.olderp.ext.service.IOldErpProductSkuService;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.service.ProductSkuService;
import com.stosz.product.sync.thread.OldErpProductSkuSyncThread;
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
 * 产品sku表同步
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class OldErpProductSkuSyncService {


    @Resource
    private IOldErpProductSkuService iOldErpProductSkuService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private OldErpProductSkuSyncThread oldErpProductSkuSyncThread;


    private final Logger logger = LoggerFactory.getLogger(getClass());


    public static final int limit = 2000;

    /**
     * 拉取老erp产品sku表信息
     */
    @Async
    public Future<Object> pull() {
        logger.info("================================ 同步pull 老erp的产品SKU 开始 ==========================");
        int count = iOldErpProductSkuService.countList();
        int success = 0;
        Assert.isTrue(count > 0, "pull老erp数据的总记录数失败，返回的记录条数为0");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int start = 0; start <= count; start = start + limit) {
            List<OldErpProductSku> list = iOldErpProductSkuService.findList(limit, start);
            Future<Integer> future = oldErpProductSkuSyncThread.pull(list);
            futureList.add(future);
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.info("pull老erp数据的sku时，发生异常！", e);
                throw new RuntimeException("pull老erp数据的sku时，发生异常！", e);
            }
        }
        logger.info("pull老erp数据的sku成功，总数{}，成功数{}", count, success);
        logger.info("================================ 同步pull 老erp的产品SKU 结束 ==========================");
        return new AsyncResult<>("pull老erp数据的sku成功");
    }


    /**
     * 将新erp的信息推送到老erp
     * 目前不开启自动推送产品SKU的功能
     */
    @Async
    @Deprecated
    public Future<Object> push(LocalDateTime startTime, LocalDateTime endTime) {
        logger.info("================================ 同步push产品SKU 开始 ==========================");
        Integer count = productSkuService.countByDate(startTime, endTime);
        int success = 0;
        ProductSku param = new ProductSku();
        param.setMinCreateAt(startTime);
        param.setMaxCreateAt(endTime);
        param.setLimit(limit);
        List<Future<Integer>> futureList = new ArrayList<>();
        if (count > 0) {
            int start = 0;
            for (; start <= count; start = start + limit) {
                List<ProductSku> productSkuList = productSkuService.findByDate(param);
                Future<Integer> future = oldErpProductSkuSyncThread.push(productSkuList);
                futureList.add(future);
            }
            for (Future<Integer> future : futureList) {
                try {
                    Integer i = future.get();
                    success += i;
                } catch (InterruptedException | ExecutionException | RuntimeException e) {
                    logger.info("push老erp数据的sku时，发生异常！", e);
                    throw new RuntimeException("push老erp数据的sku时，发生异常！", e);
                }
            }
            logger.info("push产品sku成功，总数{}，成功数{}", count, success);
        } else {
            logger.info("该时间段{} -- {}内没有要同步的产品sku", startTime, endTime);
        }
        logger.info("================================ 同步push产品SKU 结束 ==========================");
        return new AsyncResult<>("push产品sku成功");
    }


}

	




