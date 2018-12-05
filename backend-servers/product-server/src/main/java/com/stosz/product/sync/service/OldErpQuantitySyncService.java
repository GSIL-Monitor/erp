package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpStock;
import com.stosz.olderp.ext.service.IOldErpProductService;
import com.stosz.olderp.ext.service.IOldErpStockService;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductZoneService;
import com.stosz.product.sync.thread.OldErpQuantitySyncThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
public class OldErpQuantitySyncService {

    @Resource
    private IOldErpProductService iOldErpProductService;
    @Resource
    private IOldErpStockService iOldErpStockService;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private OldErpQuantitySyncThread oldErpQuantitySyncThread;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int limit = 2000;

    @Async
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullStock() {
        int count = 0;
        int success = 0;
        // 带着产品区域部门去查
        ProductZone param = new ProductZone();
        List<Future<Integer>> futureList = new ArrayList<>();
        int start = 0;
        param.setLimit(limit);
        while (true) {
            param.setStart(start);
            List<ProductZone> productZoneList = productZoneService.find(param);
            Future<Integer> future = oldErpQuantitySyncThread.pullStock(productZoneList);
            futureList.add(future);
            count += productZoneList.size();
            if (productZoneList.isEmpty() || productZoneList.size() < limit) {
                break;
            }
            start = start + limit;
        }

        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("同步产品区域库存信息失败！", e);
                throw new RuntimeException("同步产品区域库存信息失败！", e);
            }

        }
        logger.info("同步产品区域信息成功，同步总数{},成功数{}！", count, success);
        return new AsyncResult<>("同步产品区域库存信息成功！");
    }

    @Async
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullLastOrderDate(LocalDateTime startTime, LocalDateTime endTime) {
        int count = 0;
        int start = 0;
        int success = 0;
        OldErpStock param = new OldErpStock();
        param.setMinCreateAt(startTime);
        param.setMaxCreateAt(endTime);
        param.setLimit(limit);
        List<Future<Integer>> futureList = new ArrayList<>();
        while (true) {
            param.setStart(start);
            List<OldErpStock> oldErpStocks = iOldErpStockService.findOrderAtByParam(param);
            if (oldErpStocks.isEmpty()) {
                break;
            }
            Future<Integer> future = oldErpQuantitySyncThread.pullHasLastOrderAt(oldErpStocks);
            futureList.add(future);
            start = start + limit;
            count = count + oldErpStocks.size();
            if (oldErpStocks.size() < limit) {
                break;
            }
        }
        for (Future<Integer> future : futureList) {
            try {
                int i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.info("同步产品区域最后下单时间信息时，出现异常！", e);
            }
        }
        logger.info("同步产品区域最后下单时间成功，总记录数{}，成功记录数{}", count, success);
        return new AsyncResult<>("同步产品区域最后下单时间成功！");
    }

    @Async
    public Future<Object> pullHasStockByDate(LocalDateTime startTime, LocalDateTime endTime) {
        int count = 0;
        int start = 0;
        int success = 0;
        OldErpStock param = new OldErpStock();
        param.setMinCreateAt(startTime);
        param.setMaxCreateAt(endTime);
        param.setLimit(limit);
        List<Future<Integer>> futureList = new ArrayList<>();
        while (true) {
            param.setStart(start);
            List<OldErpStock> oldErpStockList = iOldErpStockService.findStockByParam(param);
            if (oldErpStockList == null || oldErpStockList.isEmpty()) {
                break;
            }
            Future<Integer> future = oldErpQuantitySyncThread.pullStockByDate(oldErpStockList);
            count = count + oldErpStockList.size();
            futureList.add(future);
            if (oldErpStockList.size() < limit) {
                break;
            }
            start = start + limit;
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("增量同步区域库存失败！", e);
                throw new RuntimeException("增量同步区域库存失败！", e);
            }
        }
        logger.info("增量同步区域库存信息成功，总记录数{}，成功数{}", count, success);
        return new AsyncResult<>("增量同步区域库存信息成功！");
    }

    @Async
    public Future<Object> pullHasStock() {
        OldErpStock oldErpStock = new OldErpStock();
        oldErpStock.setLimit(limit);
        Integer start = 0;
        Integer count = 0;
        Integer success = 0;
        List<Future<Integer>> futureList = new ArrayList<>();
        while (true) {
            oldErpStock.setStart(start);
            List<OldErpStock> oldErpStockList = iOldErpStockService.findHasStock(oldErpStock);
            if (oldErpStockList.isEmpty()) {
                break;
            }
            Future<Integer> future = oldErpQuantitySyncThread.pullHasStock(oldErpStockList);
            count = count + oldErpStockList.size();
            futureList.add(future);
            if (oldErpStockList.size() < limit) {
                break;
            }
            start = start + limit;
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("同步区域库存失败！", e);
                throw new RuntimeException("同步区域库存失败！", e);
            }
        }
        logger.info("同步区域库存信息成功，总记录数{}，成功数{}", count, success);
        return new AsyncResult<>("同步区域库存信息成功！");
    }

    @Async
    public Future<Object> pullHasLastOrderDate() {
        Integer success = 0;
        Integer count = 0;
        List<Integer> idList = iOldErpProductService.findAllId();
        Assert.notNull(idList, "获取老erp的id集合失败！");
        List<Future<Integer>> futureList = new ArrayList<>();
        for (Integer id : idList) {
            List<OldErpStock> list = iOldErpStockService.findHasOrderAtByProductId(id);
            if (!list.isEmpty()) {
                Future<Integer> future = oldErpQuantitySyncThread.pullHasLastOrderAt(list);
                count += list.size();
                futureList.add(future);
            }
        }
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
            } catch (InterruptedException | ExecutionException | RuntimeException e) {
                logger.error("同步最后下单时间失败！", e);
                throw new RuntimeException("同步最后下单时间失败！", e);
            }
        }
        logger.info("同步最后下单时间成功，总记录数{}，成功记录数{}", count, success);
        return new AsyncResult<>("同步最后下单时间成功!");
    }


}  
