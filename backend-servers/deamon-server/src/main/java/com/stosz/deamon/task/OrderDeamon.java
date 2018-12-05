package com.stosz.deamon.task;

import com.stosz.crm.ext.service.ICustomerRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderDeamon {


    @Autowired
    private ICustomerRateService customerRateService;


    private final Logger logger = LoggerFactory.getLogger(getClass());


    //    每隔10个钟执行一次
//    @Scheduled(fixedRate = 1000 * 60 )
    @Scheduled(fixedRate = 1000 * 60 * 60 * 10)
    public void customersRate(){
        logger.info("===========================================rate begin======================================");
        customerRateService.rate();
        logger.info("===========================================rate end======================================");
    }
}
