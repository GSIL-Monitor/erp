//package com.stosz.product.task;
//
//import com.stosz.product.deamon.ProductZoneDeamon;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author xiongchenyang
// * @version [1.0 , 2017/9/13]
// */
//@Component
//public class ProductZoneSchedule {
//
//    @Resource
//    private ProductZoneDeamon productZoneDeamon;
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    public void productZone() {
//        logger.info("================ 产品销档任务开始 ==================");
//        productZoneDeamon.doDisppearedTask();
//        logger.info("================ 产品销档任务结束 ==================");
//    }
//}
