//package com.stosz.order.service.crm.task;
//
//import com.stosz.order.service.crm.CustomerRateService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
///**
// * @author
// */
//@Component
//public class CustomerRateSchedule {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private CustomerRateService customerRateService;
//
//    private static AtomicBoolean atomicBoolean = new AtomicBoolean(true);
//
//
//
//    //每隔10个钟执行一次
//    @Scheduled(fixedRate = 1000 * 60 * 60 * 10)
//    public void rate(){
//        if(!atomicBoolean.get()){
//            return ;
//        }
//        try {
//            customerRateService.rate();
//        }catch (Exception e){
//            logger.error("==========================>更新用户评级异常，请立即处理！");
//        }
//
//        atomicBoolean.set(true);
//    }
//
//
//}
