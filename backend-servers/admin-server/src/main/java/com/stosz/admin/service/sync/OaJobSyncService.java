package com.stosz.admin.service.sync;

import com.stosz.admin.ext.model.Job;
import com.stosz.admin.mapper.oa.OaJobMapper;
import com.stosz.admin.service.admin.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by XCY on 2017/8/18.
 * desc: OA 与 新erp 的职位信息同步
 */
@Service
@EnableTransactionManagement
public class OaJobSyncService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OaJobMapper oaJobMapper;

    @Resource
    private JobService jobService;

    /**
     * 新erp与oa 职位的导入
     */
    @Async
    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullAll() {
        // 查出oa所有职位保存到list
        List<Job> jobList = oaJobMapper.findAll();
        Assert.notNull(jobList, "获取oa职位信息失败，获取到的集合为空！");
        //先截断表
        jobService.truncate();
        jobService.insertBatch(jobList);
        return new AsyncResult<>("导入职位信息成功！");
    }

    /**
     * 职位信息的同步
     */
    @Async
    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pull() {
        // 查出oa所有职位保存到list
        List<Job> jobList = oaJobMapper.findAll();
        Assert.notNull(jobList, "获取oa职位信息失败，获取到的集合为空！");
        oaJobSync(jobList);
        return new AsyncResult<>("同步职位信息成功！");
    }


    private void oaJobSync(List<Job> jobList) {
        for (Job job : jobList) {
            Integer jobId = job.getId();
            Job jobDB = jobService.getById(jobId);
            if (jobDB == null) {
                jobService.insert(job);
            } else {
                jobService.update(job);
            }
        }
        logger.info("职位信息同步完成，同步内容为{}", jobList);
    }

}
