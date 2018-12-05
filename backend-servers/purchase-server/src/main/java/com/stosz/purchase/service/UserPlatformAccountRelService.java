package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.UserPlatformAccountRel;
import com.stosz.purchase.mapper.UserPlatformAccountRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserPlatformAccountRelService {

    @Resource
    private UserPlatformAccountRelMapper userPlatformAccountRelMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());


    public List<UserPlatformAccountRel> find(UserPlatformAccountRel userPlatformAccountRel) {
        return userPlatformAccountRelMapper.find(userPlatformAccountRel);
    }

    public int count(UserPlatformAccountRel userPlatformAccountRel) {
        return  userPlatformAccountRelMapper.count(userPlatformAccountRel);
    }


    public void add(UserPlatformAccountRel userPlatformAccountRel) {
        Integer i=userPlatformAccountRelMapper.add(userPlatformAccountRel);
        Assert.isTrue(i==1,"添加账号失败");
        logger.info("增加用户--平台账号成功  {}",userPlatformAccountRel.getId());
    }



    public void delete(Integer id) {
        UserPlatformAccountRel u=getById(id);
        Assert.notNull(u,"用户和平台账号关系不存在");
       int i= userPlatformAccountRelMapper.delete(id);
       Assert.isTrue(i==1,"更新用户平台账号失败");
        logger.info("删除用户平台{}账号成功",id);
    }


    public List<UserPlatformAccountRel> findByPlatAndBuyer(Integer platId, Integer buyerId){
        Assert.notNull(platId,"要查询的平台id不允许为空！");
        Assert.notNull(buyerId,"要查询的采购员id不允许为空！");
        return userPlatformAccountRelMapper.findByPlatAndBuyer(platId,buyerId);
    }

    public void updateState(Integer id, Integer enable) {
        UserPlatformAccountRel userPlatformAccountRel=getById(id);
        Assert.notNull(userPlatformAccountRel,"要修改的用户事业部关系不存在！");
        int i=userPlatformAccountRelMapper.update(id,enable);
        Assert.isTrue(i==1,"修改用户事业部关系失败！");
        logger.info("用户平台账号--------修改关系{}的状态为{}成功！",userPlatformAccountRel,enable);
    }

    public UserPlatformAccountRel getById(Integer id) {
        return userPlatformAccountRelMapper.getById(id);
    }

    public void queryByAccount(UserPlatformAccountRel userPlatformAccountRel) {
//        Integer ii= platformAccountMapper.findPlatIdAccount(userPlatformAccountRel.getPlatId(),account);
        Assert.notNull(userPlatformAccountRel.getBuyer(),"买手为空");
        Assert.notNull(userPlatformAccountRel.getBuyerId(),"买手Id为空");
        Assert.notNull(userPlatformAccountRel.getPlatAccountId(),"平台账号id为空");
        Assert.notNull(userPlatformAccountRel.getPlatId(),"平台id为空");
        Integer i=userPlatformAccountRelMapper.queryByAccount(userPlatformAccountRel);
        Assert.isTrue(i==0,"关联关系已经存在");
        logger.info("查询关联关系Ok");
    }

    public List<String> getAccountByBuyer(UserPlatformAccountRel userPlatformAccountRel) {
        List<String> list=userPlatformAccountRelMapper.getAccountByBuyer(userPlatformAccountRel);
        logger.info("更具采购员查询买手账号{}",list);
        return  list;
    }

    public Integer getAccountCountByBuyer(UserPlatformAccountRel userPlatformAccountRel) {
        return  userPlatformAccountRelMapper.getAccountCountByBuyer(userPlatformAccountRel);
    }
}
