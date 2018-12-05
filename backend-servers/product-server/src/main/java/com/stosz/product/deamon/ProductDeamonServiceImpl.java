package com.stosz.product.deamon;

import com.stosz.product.ext.service.IProductDeamonService;
import com.stosz.product.sync.service.OldErpQuantitySyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/9]
 */
@Component
public class ProductDeamonServiceImpl implements IProductDeamonService {

    @Resource
    ProductNewReportDeamon productNewReportDeamon;
    @Resource
    ProductZoneDeamon productZoneDeamon;
    @Resource
    private OldErpQuantitySyncService oldErpQuantitySyncService;
    @Resource
    private OldErpCategoryNewDeamon oldErpCategoryNewDeamon;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 排重报表自动调度
     */
    public void productNewReport() {
        logger.info("================ 排重每日报表调度开始[时间:" + new Date() + "] ==================");
        productNewReportDeamon.excludeRepeatReport();
        logger.info("================ 排重每日报表调度结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 排重报表手动调度
     */
    public void productNewReportManual(List<Integer> startTime, List<Integer> endTime) {
        logger.info("================ 排重每日报表手动调度开始[时间:" + new Date() + "] ==================");
        productNewReportDeamon.excludeRepeatReport(startTime, endTime);
        logger.info("================ 排重每日报表手动调度结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 五天预警调度
     */
    public void warningTask() {
        logger.info("检查自动预警开始，时间：{}", new Date());
        productZoneDeamon.doWarningTask();
        logger.info("检查自动预警结束，时间：{}", new Date());
    }

    /**
     * 上架变成待下架调度
     */
    public void doWaitoffsaleTask() {
        logger.info("检查自动待下架(未备案)开始，时间：{}", new Date());
        productZoneDeamon.doWaitoffsaleTask();
        logger.info("检查自动待下架(未备案)结束，时间：{}", new Date());
    }

    /**
     * 三天未备案自动消档
     */
    public void noArchiveTask() {
        logger.info("检查自动未备案开始，时间：{}", new Date());
        productZoneDeamon.noArchiveTask();
        logger.info("检查自动未备案结束，时间：{}", new Date());
    }

    /**
     * 待下架消档任务调度
     */
    public void disppearedTask() {
        logger.info("检查自动消档开始，时间：{}", new Date());
        productZoneDeamon.doDisppearedTask();
        logger.info("检查自动消档结束，时间：{}", new Date());
    }

    /**
     * 老erp品类同步
     */
    @Override
    public void oldErpCategoryNewTask() {
        logger.info("老ERP品类表同步开始，时间：{}", new Date());
        oldErpCategoryNewDeamon.oldErpCategoryNewTask();
        logger.info("老ERP品类表同步结束，时间：{}", new Date());
    }

    /**
     * 新erp同步老erp最后下单时间
     */
    public void lastOrderAtSync() {
        logger.info("====================== 同步最后下单时间开始 ============================");
        Future<Object> future = oldErpQuantitySyncService.pullLastOrderDate(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("最后下单时间线程返回: {}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步产品区域表最后下单时间时出现异常！", e);
            throw new RuntimeException("同步产品区域表最后下单时间时出现异常！", e);
        }
        logger.info("====================== 同步最后下单时间结束 ============================");
    }

    /**
     * 新erp同步老erp产品库存
     */
    public void stockSync() {
        logger.info("====================== 同步产品区域表库存开始 ============================");
        Future<Object> future = oldErpQuantitySyncService.pullHasStockByDate(LocalDateTime.now().plusDays(-1), LocalDateTime.now());
        try {
            Object object = future.get();
            logger.info("库存信息同步线程返回: {}", object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步产品区域表库存时出现异常！", e);
            throw new RuntimeException("同步产品区域表库存时出现异常！", e);
        }
        logger.info("====================== 同步产品区域表库存结束 ============================");
    }

}
