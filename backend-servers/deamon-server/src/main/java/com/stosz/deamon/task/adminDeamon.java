package com.stosz.deamon.task;

import com.stosz.admin.ext.service.IJobSyncService;
import com.stosz.admin.ext.service.IUserSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户中心调度
 * @author he_guitang
 * @version [1.0 , 2018/1/11]
 */
public class adminDeamon {

    @Resource
    private IJobSyncService iJobSyncService;
    @Resource
    private IUserSyncService iUserSyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 用户调度
     */
    @Scheduled(cron = "0 2/15 * * * ? ")
    private void userSync() {
        logger.info("================ 调度系统调度==>同步OA用户开始[时间:" + new Date() + "] ==================");
        iUserSyncService.userSync();
        logger.info("================ 调度系统调度==>同步OA用户结束[时间:" + new Date() + "] ==================");
    }

    /**
     * 职位调度
     */
    @Scheduled(cron = "0 0/15 * * * ? ")
    private void jobSync() {
        logger.info("================ 调度系统调度==>同步OA职位开始[时间:" + new Date() + "] ==================");
        iJobSyncService.jobSync();
        logger.info("================ 调度系统调度==>同步OA职位结束[时间:" + new Date() + "] ==================");
    }
}
