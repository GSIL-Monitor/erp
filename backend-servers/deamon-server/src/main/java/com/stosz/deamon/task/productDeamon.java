package com.stosz.deamon.task;

import com.stosz.product.ext.service.IProductDeamonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/10]
 */
@Component
public class productDeamon {

    @Resource
    private IProductDeamonService iProductDeamonService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 排重报表调度
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 0 23 * * ? ")
    public void productNewReport(){
        logger.info("================ 调度系统调度==>排重每日报表开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.productNewReport();
        logger.info("================ 调度系统调度==>排重每日报表结束[时间:" + new Date() + "] ==================");
    }


    /**
     * 五天预警调度
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 0 7 * * ? ")
    public void warningTask(){
        logger.info("================ 调度系统调度==>五天无订单预警开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.warningTask();
        logger.info("================ 调度系统调度==>五天无订单预警结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 上架变成待下架调度
     */
//    @Scheduled(cron = "0 0/2 * * * ? ")
    @Scheduled(cron = "0 0 8 * * ? ")
    public void doWaitoffsaleTask(){
        logger.info("================ 调度系统调度==>上架变成待下架开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.doWaitoffsaleTask();
        logger.info("================ 调度系统调度==>上架变成待下架结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 三天未备案自动消档
     */
//    @Scheduled(cron = "0 0/2 * * * ? ")
    @Scheduled(cron = "0 0 8 * * ? ")
    public void noArchiveTask(){
        logger.info("================ 调度系统调度==>三天未备案自动消档开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.noArchiveTask();
        logger.info("================ 调度系统调度==>三天未备案自动消档结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 待下架消档任务调度
     */
//    @Scheduled(cron = "0 0/2 * * * ? ")
    @Scheduled(cron = "0 0 8 * * ? ")
    public void disppearedTask(){
        logger.info("================ 调度系统调度==>待下架消档任务开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.disppearedTask();
        logger.info("================ 调度系统调度==>待下架消档任务结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 新erp同步老erp最后下单时间
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 20 * * * ?")
    public void lastOrderAtSync(){
        logger.info("================ 调度系统调度==>同步老erp最后下单时间开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.lastOrderAtSync();
        logger.info("================ 调度系统调度==>同步老erp最后下单时间结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 新erp同步老erp产品库存
     */
//    @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 0 0 * * ?")
    public void stockSync(){
        logger.info("================ 调度系统调度==>同步老erp产品库存开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.stockSync();
        logger.info("================ 调度系统调度==>同步老erp产品库存结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 老ERP品类表同步
     */
//    @Scheduled(cron = "")
    public void oldErpCategoryNewSync(){
        logger.info("================ 调度系统调度==>同步老erp品类表开始[时间:" + new Date() + "] ==================");
        iProductDeamonService.oldErpCategoryNewTask();
        logger.info("================ 调度系统调度==>同步老erp品类表结束[时间:" + new Date() + "] ==================");
    }
}
