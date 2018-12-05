package com.stosz.store.task;

import com.stosz.store.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author:yangqinghui
 * @Description:每月生成库存记录到记录表 定时任务
 * @Created Time:2017/11/27 14:55
 * @Update Time:2017/11/27 14:55
 */
@Component
@EnableScheduling
public class MonthlyStockSchedule {

    @Resource
    private StockService stockService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 0 0 1 * ? ")
    public void addStock2RecordMonthly() {

        //todo
        logger.info("================ 开始执行库存记录 ==================");

        logger.info("================完成执行库存记录==================");
    }

}
