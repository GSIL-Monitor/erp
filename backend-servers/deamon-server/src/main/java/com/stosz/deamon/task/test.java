package com.stosz.deamon.task;

import com.stosz.admin.ext.service.IDepartmentSyncService;
import com.stosz.crm.ext.service.ICustomerRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2018/1/9]
 */
@Component
public class test {


    @Resource
    private IDepartmentSyncService iDepartmentSyncService;


//    @Autowired
//    private ICustomerRateService customerRateService;


    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Scheduled(cron = "0 * * * * *")
    public void departmentSync(){
        logger.info("调度系统测试==============================================================================");
        logger.info("===========================================mamaipi======================================");
    }


//    @Scheduled(cron = "0 * * * * *")
    //每隔10个钟执行一次
//    @Scheduled(fixedRate = 1000 * 60 * 60 * 10)
//    public void customersRate(){
//        customerRateService.rate();
//    }
}  
