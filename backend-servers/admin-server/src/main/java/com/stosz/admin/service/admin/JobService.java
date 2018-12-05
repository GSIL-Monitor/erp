package com.stosz.admin.service.admin;

import com.stosz.admin.ext.model.Job;
import com.stosz.admin.mapper.admin.JobMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XCY on 2017/8/16.
 * desc:
 */
//@com.alibaba.dubbo.config.annotation.Service
    @Service
public class JobService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private JobMapper jobMapper;

    /**
     * 批量插入职位信息，仅在导入职位信息时使用
     */
    public void insertBatch(List<Job> jobList) {
        Assert.notNull(jobList, "要批量插入的职位集合不允许为空！");
        Assert.notEmpty(jobList, "要批量插入的职位集合不允许为空！");
        Integer i = jobMapper.insertBatch(0, jobList);
        Assert.isTrue(i > 0, "批量插入职位信息出现异常！");
        logger.info("批量插入职位信息{}成功！", jobList);
    }

    public void insert(Job job) {
        Assert.notNull(job, "插入的职位信息不允许为空！");
        Integer i = jobMapper.insert(job);
        Assert.isTrue(i == 1, "插入职位信息失败！");
        logger.info("插入职位信息{}成功！", job);
    }

    /**
     * 截断表，仅全量导入职位信息时使用，其余地方慎用！！！
     */
    public void truncate() {
        jobMapper.truncate();
    }

    /**
     * 有选择性的更新job， 如果字段为空就不更新该字段
     */
    public void update(Job job) {
        Assert.notNull(job, "不允许将职位信息修改为null");
        Job jobDB = jobMapper.getById(job.getId());
        Assert.notNull(jobDB, "数据库中不存在对应的职位信息！");
        Integer i = jobMapper.update(job);
        Assert.isTrue(i == 1, "更新职位信息失败！");
        logger.info("将职位信息{}修改为{}成功！", jobDB, job);
    }

    /**
     * 更新这个用户的菜单权限
     */
    @Deprecated
    public void updateJobMenu(Integer jobId, List<Integer> menuIdLists) {

        jobMapper.deleteJobMenu(jobId);
        if (menuIdLists.size() > 0) {
            jobMapper.insertJobMenu(0, jobId, menuIdLists);
        }
        logger.info("批量修改id为{}职位的菜单权限成功，添加的菜单集合为{}", jobId, menuIdLists);

    }

    public void updateJobMenu(Integer jobId, Integer menuId, Boolean isChecked) {
        try {
            if (isChecked) {
                //增加
                jobMapper.insertOneJobMenu(0, jobId, menuId);
            } else {
                //删除
                jobMapper.deleteOneJobMenu(jobId, menuId);
            }
            logger.info("修改职位id为{}的菜单权限成功，菜单id为{}，是否选中{}", jobId, menuId, isChecked);
        } catch (DuplicateKeyException e) {
            logger.info("修改职位id为{}的权限菜单id为{}时发生冲突，重复授权！", jobId, menuId);
            throw new RuntimeException("不允许重复授权！");
        }


    }

    public List<Job> findByUserId(Integer userId) {
        if (userId != null) {
            return jobMapper.findByUserId(userId);
        }
        return null;
    }

    public List<Job> findByParma(Map<String, Object> productMap) {
        return jobMapper.findByParam(productMap);
    }

    public Job getById(Integer id) {
        return jobMapper.getById(id);
    }

    /**
     * 根据搜索的关键字，模糊匹配关键词
     *
     * @param search 搜索关键字
     * @return 返回模糊匹配的关键词
     */
    public List<Job> findNameBySearch(String search) {
        try {
            return jobMapper.findNameBySearch(search);
        } catch (Exception e) {
            logger.error("模糊查询时，出现未知错误，内容{}", search, e);
            throw new RuntimeException("模糊查询时，出现未知错误");
        }
    }


    /**
     * 获取职位总数
     *
     * @return 职位总数
     */
    public int count(Job job) {
        return jobMapper.count(job);
    }

    public List<Job> query(Job job) {
        job = job == null ? new Job() : job;
        //获取开始位置
        Integer start = (job.getStart() == null || job.getStart() < 0) ? 0 : job.getStart();
        //获取需要显示的条数
        Integer limit = (job.getLimit() == null || job.getLimit() < 20) ? 10 : job.getLimit();
        Map<String, Object> productMap = new HashMap<String, Object>();
        productMap.put("start", start);
        productMap.put("limit", limit);
        String jobName = job.getName();
        if (StringUtils.isNotBlank(jobName)) {
            productMap.put("name", jobName);
        }
        //获取当前页面需要显示的数据
        List<Job> productList = findByParma(productMap);
        Assert.notNull(productList, "未查询到对应的职位列表！");
        return productList;
    }

    /**
     * 更新用户的菜单，元素权限
     *
     * @param jobId
     * @param menuId
     * @param isChecked
     */
    public void updateJobMenuElement(int jobId, int menuId, Integer elementId, boolean isChecked) {
        try {
            if (isChecked) {
                //增加,判断elementId是否存在,存在则分配到元素权限，不存在则只是分配到菜单权限
                if (elementId == null) {
                    jobMapper.insertOneJobMenu(0, jobId, menuId);
                } else {
                    jobMapper.insertOneJobMenuElement(0, jobId, menuId, elementId);
                }
            } else {
                //删除
                if (elementId != null) {
                    jobMapper.deleteOneJobMenuElement(jobId, menuId, elementId);
                } else {
                    jobMapper.deleteOneJobMenu(jobId, menuId);
                }
            }
            logger.info("修改职位id为{}的菜单权限成功，菜单id为{}，是否选中{}", jobId, menuId, isChecked);
        } catch (DuplicateKeyException e) {
            logger.info("修改职位id为{}的权限菜单id为{}时发生冲突，重复授权！", jobId, menuId);
            throw new RuntimeException("不允许重复授权！");
        }
    }

    /**
     * 获取赋权的元素id
     *
     * @param jobId
     * @param menuId
     * @return
     */
    public List<Integer> findElementIds(Integer jobId, Integer menuId) {
        return jobMapper.findElementIdFromJobMenuElement(jobId, menuId);
    }

}
