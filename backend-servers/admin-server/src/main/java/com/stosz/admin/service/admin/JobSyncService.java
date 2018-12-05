package com.stosz.admin.service.admin;

import com.stosz.admin.ext.service.IJobSyncService;
import com.stosz.admin.service.sync.OaJobSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/11]
 */
@Service
public class JobSyncService implements IJobSyncService {

    @Resource
    private OaJobSyncService oaJobSyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Boolean pull() {
        return null;
    }

    @Override
    public void jobSync() {
        logger.info("======================== OA职位同步调度开始了！ ====================");
        oaJobSyncService.pull();
        logger.info("======================== OA职位同步调度完成了！ ====================");
    }

}
