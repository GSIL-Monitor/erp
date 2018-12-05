package com.stosz.admin.service.admin;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.model.UserJobRel;
import com.stosz.admin.mapper.admin.UserJobRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户-职位关联表的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/7]
 */
//@com.alibaba.dubbo.config.annotation.Service
    @Service
public class UserJobRelService {

    @Resource
    private UserJobRelMapper userJobRelMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 目前只用在用户-职位表为空的时候插入
     * 批量插入用户-职位映射
     */
    public void insertBatch(List<User> userList) {
        Assert.notNull(userList, "批量插入用户职位信息的用户集合不允许为空！");
        try {
            userJobRelMapper.insertBatch(0, userList);
        } catch (DuplicateKeyException ignored) {
            logger.info("批量插入用户职位关系时，发生主键冲突，冲突的内容{}", userList);
        }
        logger.info("批量插入用户-职位关系成功，用户列表{}", userList);
    }

    public void insert(UserJobRel userJobRel) {
        Assert.notNull(userJobRel, "插入用户职位信息不允许为空！");
        try {
            int i = userJobRelMapper.insert(userJobRel);
            Assert.isTrue(i > 0, "更新失败");
        } catch (DuplicateKeyException ignored) {
            logger.info("该用户职位信息{}在数据库中已经存在，重复插入", userJobRel);
        }
        logger.info("插入用户-职位关系成功，用户列表{}", userJobRel);
    }

    public void delete(UserJobRel userJobRel) {
        Assert.notNull(userJobRel, "要删除的用户职位信息不允许为空！");
        UserJobRel userJobRelDB = userJobRelMapper.getById(userJobRel.getId());
        Assert.notNull(userJobRelDB, "要删除的用户职位信息在数据库中不存在！");
        int i = userJobRelMapper.delete(userJobRel);
        Assert.isTrue(i == 1, "删除职位信息出错！");
        logger.info("删除职位-用户信息{}成功！", userJobRel);
    }

    /**
     * 截断表，目前仅在全量导入用户信息时用到，其余地方慎用！！！
     */
    public void truncate() {
        userJobRelMapper.truncate();
    }

    public UserJobRel getByUnique(Integer userId, Integer jobId) {
        Assert.notNull(userId, "用户id不允许为空！");
        Assert.notNull(userId, "职位id不允许为空！");
        return userJobRelMapper.getByUnique(userId, jobId);
    }


}
