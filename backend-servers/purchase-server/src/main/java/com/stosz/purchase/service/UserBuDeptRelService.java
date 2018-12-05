package com.stosz.purchase.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.purchase.ext.model.UserBuDept;
import com.stosz.purchase.ext.model.UserProductRel;
import com.stosz.purchase.mapper.UserBuDeptMapper;
import com.stosz.purchase.mapper.UserProductRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 采购员与事业部的关系service
 * @author xiongchenyang
 * @version [1.0 , 2017/12/14]
 */
@Service
public class UserBuDeptRelService {

    @Resource
    private UserBuDeptMapper userBuDeptMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public void insert(UserBuDept userBuDept) {
        Assert.notNull(userBuDept,"要插入的关系不允许为空！");
        int i =  userBuDeptMapper.insert(userBuDept);
        Assert.isTrue(i==1,"插入用户事业部关系失败！");
        logger.info("用户事业部----------新增用户事业部关系{}成功！",userBuDept);
    }

    public void delete(Integer id) {
        UserBuDept userBuDept = getById(id);
        Assert.notNull(userBuDept,"要删除的用户事业部关系不存在！");
        int i = userBuDeptMapper.delete(id);
        Assert.isTrue(i==1,"删除用户事业部关系成功！");
        logger.info("用户事业部----------删除关系{}成功！",userBuDept);
    }

    public void updateState(Integer id,Integer enable) {
        UserBuDept userBuDept = getById(id);
        Assert.notNull(userBuDept,"要修改的用户事业部关系不存在！");
        int i =  userBuDeptMapper.update(id, enable);
        Assert.isTrue(i==1,"修改用户事业部关系失败！");
        logger.info("用户事业部--------修改关系{}的状态为{}成功！",userBuDept,enable);
    }

    public int count(UserBuDept userBuDept) {
        return userBuDeptMapper.count(userBuDept);
    }

    public UserBuDept getById(Integer id) {
        return userBuDeptMapper.getById(id);
    }


    public UserBuDept getByUnique(Integer buDeptId, Integer buyerId) {
        return userBuDeptMapper.getByUnique(buDeptId,buyerId);
    }

    public List<UserBuDept> find(UserBuDept userBuDept){
        return userBuDeptMapper.find(userBuDept);
    }

    /**
     * 根据当前采购员获取到拥有的部门
      * @param userId 用户id
     * @return 拥有的部门
     */
    public List<Department> findByCurrentUser(Integer userId){
        return userBuDeptMapper.findByCurrentUser(userId);
    }

    public List<User> findUser(String search,Integer buDeptId){
        return userBuDeptMapper.findUser(search,buDeptId);
    }
}
