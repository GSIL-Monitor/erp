//package com.stosz.product.task;
//
//import com.stosz.product.deamon.ProductNewReportDeamon;
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
//
////排重每日报表
//@Component
//@EnableScheduling
//public class ProductNewReportSchedule {
//
//    @Resource
//    private ProductNewReportDeamon productNewReportDeamon;
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Scheduled(cron = "0 0 23 * * ? ")
//    public void productNewReport() {
//        logger.info("================ 排重每日报表调度开始[时间:" + new Date() + "] ==================");
//        productNewReportDeamon.excludeRepeatReport();
//        logger.info("================ 排重每日报表调度结束[时间:" + new Date() + "] ==================");
//    }
//
//
//    public void productNewReport(List<Integer> startTime, List<Integer> endTime) {
//        logger.info("================ 排重每日报表手动调度开始[时间:" + new Date() + "] ==================");
//        productNewReportDeamon.excludeRepeatReport(startTime, endTime);
//        logger.info("================ 排重每日报表手动调度结束[时间:" + new Date() + "] ==================");
//    }
//}
