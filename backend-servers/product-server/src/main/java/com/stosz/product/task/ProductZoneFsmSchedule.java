//package com.stosz.product.task;
//
//import com.stosz.plat.common.MBox;
//import com.stosz.product.deamon.ProductZoneDeamon;
//import com.stosz.product.ext.model.ProductZone;
//import com.stosz.product.service.ProductZoneService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author liufeng
// * @version [1.0 , 2017/9/22]
// */
//@Component
//@EnableScheduling
//public class ProductZoneFsmSchedule {
//
//    @Resource
//    private ProductZoneService productZoneService;
//    @Resource
//    private ProductZoneDeamon productZoneDeamon;
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    /**
//     * 五天预警调度
//     */
//    @Scheduled(cron = "0 0 7 * * ? ")
//    public void warningTask() {
//        logger.info("检查自动预警开始，时间：{}", new Date());
//        productZoneDeamon.doWarningTask();
//        logger.info("检查自动预警结束，时间：{}", new Date());
//    }
//
//    /**
//     * 自动待下架调度
//     */
//    @Scheduled(cron = "0 0 8 * * ? ")
//    public void doWaitoffsaleTask() {
//        logger.info("检查自动待下架(未备案)开始，时间：{}", new Date());
//        productZoneDeamon.doWaitoffsaleTask();
//        logger.info("检查自动待下架(未备案)结束，时间：{}", new Date());
//    }
//
//    /**
//     * 待下架消档任务调度
//     */
//    @Scheduled(cron = "0 0 8 * * ? ")
//    public void disppearedTask() {
//        logger.info("检查自动消档开始，时间：{}", new Date());
//        productZoneDeamon.doDisppearedTask();
//        logger.info("检查自动消档结束，时间：{}", new Date());
//    }
//
//
//
//
//
//
//
//    /**
//     * 调度检查建档中的产品3天内是否有备案
//     * 每1小时检查一次
//     */
////    @Scheduled(cron = "0 0 */1 * * ?")
//    public void checkArchive() {
//        logger.info("开始检查建档中产品是否备案，时间：{}", new Date());
//        //查询三天未备案的
//        List<ProductZone> productZones = productZoneService.findNoArchiveForDisappeared(MBox.DISPPEARED_DAY_ARCHIVE, 20);
//        if (productZones == null || productZones.isEmpty()) {
//            logger.debug("本次没有找到3天未备案的产品区域信息！");
//            return;
//        }
//        logger.info("本次找到3天未备案的产品区域信息！共计：{} 条记录", productZones.size());
//        for (ProductZone productZone : productZones) {
//            productZoneService.processOffsaleEvent(productZone, "超过三天未备案，当前库存数:" + productZone.getStock());
//        }
//    }
//
//    /**
//     * 检查已上架产品在7天内是否出单
//     * 每2小时检查一次
//     */
////    @Scheduled(cron = "0 0 */2 * * ?")
//    public void checkOrder() {
//        logger.info("开始检查已上架产品是否出单，时间：{}", new Date());
//        //7天未出单
//        List<ProductZone> productZones = productZoneService.findNoOrderForDisappeared(MBox.DISPPEARED_DAY_ORDER, 20);
//        if (productZones == null || productZones.isEmpty()) {
//            logger.debug("本次没有找到7天未出单的产品区域信息！");
//            return;
//        }
//        logger.info("本次找到7天未出单的产品区域信息！共计：{} 条记录", productZones.size());
//        for (ProductZone productZone : productZones) {
//            productZoneService.processOffsaleEvent(productZone, "超过七天无订单，当前库存数:" + productZone.getStock());
//        }
//    }
//
//
//}
