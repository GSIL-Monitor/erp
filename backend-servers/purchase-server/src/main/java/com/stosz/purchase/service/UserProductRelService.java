package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.UserProductRel;
import com.stosz.purchase.mapper.UserProductRelMapper;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/14]
 */
@Service
public class UserProductRelService {

    private final Logger logger = getLogger(getClass());

    @Resource
    private UserProductRelMapper userProductRelMapper;

    /**
     * 新增用户与产品关系
     * @param userProductRel 用户与产品关系
     */
    public void insert(UserProductRel userProductRel) {
        Assert.notNull(userProductRel,"要新增的用户与产品关系不能为空！");
        int i = 0;
        try {
            userProductRel.setSpu(userProductRel.getSpu().trim());
            i = userProductRelMapper.insert(userProductRel);
        } catch (DuplicateKeyException e) {
            logger.error("不允许插入重复数据，{}",userProductRel,e);
            throw new RuntimeException("不允许插入重复数据！");
        }
        Assert.isTrue(i==1,"新增用户与产品关系失败！");
        logger.info("用户产品关系--------新增{}成功！",userProductRel);
    }

    public void delete(Integer id) {
        UserProductRel userProductRelDB = userProductRelMapper.getById(id);
        Assert.notNull(userProductRelDB,"要删除的关系在数据库中不存在！");
        int i = userProductRelMapper.delete(id);
        Assert.isTrue(i==1,"用户产品关系删除失败！");
        logger.info("用户产品关系--------删除{}成功！",userProductRelDB);
    }

    public void updateState(Integer id, Integer enable) {
        UserProductRel userProductRelDB = userProductRelMapper.getById(id);
        Assert.notNull(userProductRelDB,"要删除的用户产品关系不存在！");
        int i =  userProductRelMapper.update(id, enable);
        Assert.isTrue(i==1,"删除用户产品关系失败！");
        logger.info("用户产品关系-------修改关系{}的状态为{}成功！",userProductRelDB,enable);
    }

    public int count(UserProductRel userProductRel) {
        return userProductRelMapper.count(userProductRel);
    }

    public List<UserProductRel> find(UserProductRel userProductRel) {
        return userProductRelMapper.find(userProductRel);
    }

    public UserProductRel getById(Integer id) {
        return userProductRelMapper.getById(id);
    }


    /**
     * 根据spu 事业部门ID 查询采购员
     */
    public UserProductRel getBySpuAndDeptId(String spu, Integer buDepartmentId) {
        return userProductRelMapper.getBySpuAndDeptId(spu, buDepartmentId);
    }


    /**
     * 查询最后一次设置的采购员
     */
    public UserProductRel getByIdSet(Set<Integer> idSet) {
        return userProductRelMapper.getByIdSet(idSet);
    }


    public UserProductRel getBySpuAndId(String spu, Integer buyerId) {
        return userProductRelMapper.getBySpuAndUserId(spu, buyerId);
    }

}
