package com.stosz.store.task;

import com.stosz.store.service.PlanRecordService;
import com.stosz.store.service.SettlementMonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author:yangqinghui
 * @Description:排程表生成定时任务
 * @Created Time:2017/11/27 14:55
 * @Update Time:2017/11/27 14:55
 */
@Component
@EnableScheduling
public class PlanRecordSchedule {

    @Resource
    private PlanRecordService planRecordService;
    @Resource
    private SettlementMonthService settlementMonthService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 0 0 * * ? ")
    public void productPlanRecord() {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonth().getValue();
        logger.info("================ 插入当月排程表[时间:" + year + "-" + month + "] ==================");
        planRecordService.insert(year, month);
        logger.info("================ 插入当月排程表完成[时间:" + new Date() + "] ==================");
        int lastYear = LocalDateTime.now().plusMonths(1).getYear();
        int lastMonth = LocalDateTime.now().plusMonths(1).getMonth().getValue();
        logger.info("================ 插入次月排程表[时间:" + lastYear + "-" + lastMonth + "] ==================");
        planRecordService.insert(lastYear, lastMonth);
        logger.info("================ 插入次月排程表完成[时间:" + new Date() + "] ==================");
    }

    @Scheduled(cron = "0 0 0 * * ? ")
    public void addSettlementMonth() {
        logger.info("================ 生成月结[时间:"+new Date()+"] ==================");
        settlementMonthService.addSettlementMonth();
        logger.info("================ 生成完成[时间:"+new Date()+"] ==================");
    }
}
