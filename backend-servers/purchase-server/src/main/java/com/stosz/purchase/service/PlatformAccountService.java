package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.Platform;
import com.stosz.purchase.ext.model.PlatformAccount;
import com.stosz.purchase.mapper.PlatformAccountMapper;
import com.stosz.purchase.mapper.PlatformMapper;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class PlatformAccountService {
    @Autowired
    private PlatformAccountMapper platformAccountMapper;
    @Autowired
    private PlatformMapper platformMapper;

    private  final Logger logger = LoggerFactory.getLogger(PlatformAccountService.class);




    public List<PlatformAccount> find(PlatformAccount platformAccount) {
        return platformAccountMapper.find(platformAccount);
    }

    public int countPlatform(PlatformAccount platformAccount) {
        return platformAccountMapper.count(platformAccount);
    }

    public void insert(PlatformAccount platformAccount) {
        Assert.notNull(platformAccount.getAccount(), "账号不能为空");
        int a= platformAccountMapper.insert(platformAccount);
        Assert.isTrue(a==1,"添加失败");
        logger.info("平台账号----------新增用户关系{}成功！",platformAccount);

    }

    public void delete(Integer id) {
        Assert.notNull(id, "ID不能为空");
        logger.info("平台账号----------删除用户关系{}成功！",id);
    platformAccountMapper.delete(id);

    }
 /*   public List<PlatformAccount> queryPlatformAccountList(PlatformAccount PlatformAccount) {
        return platformAccountMapper.queryPlatformAccountList(PlatformAccount);
    }*/

    public int count(PlatformAccount platformAccount) {
        return  platformAccountMapper.count(platformAccount);
    }

/*    public void update(PlatformAccount platformAccount) {
        platformAccountMapper.update(platformAccount);
    }*/

/*    public void operateAccount(PlatformAccount platformAccount) {
        Assert.notNull(platformAccount.getId(), "ID不能为空");
        Assert.notNull(platformAccount.getState(), "状态不能为空");
        platformAccountMapper.update(platformAccount);
        logger.info("平台账号----------更新用户关系{}成功！",platformAccount);

    }*/

    public PlatformAccount getById(Integer id) {
        return  platformAccountMapper.getById(id);
    }

    public void updateState(Integer id, Integer enable) {
        PlatformAccount platformAccount=getById(id);
        Assert.notNull(platformAccount,"要修改的用户事业部关系不存在！");
        int i=platformAccountMapper.update(id,enable);
        Assert.isTrue(i==1,"更新平台账号失败");
        logger.info("更新关系{}的状态{}成功");
    }

/*    public void findPlatIdAccount(PlatformAccount platformAccount) {
       Integer i= platformAccountMapper.findPlatIdAccount(platformAccount);
       Assert.isTrue(i==0,"此买手账号已经存在");
       logger.info("更具买手账号和平台查看ok");
    }*/

    public void findPlatIdAccount(Integer platId, String account) {
        Integer i= platformAccountMapper.findPlatIdAccount(platId,account);
        Assert.isTrue(i==0,"此买手账号已经存在");
        logger.info("更具买手账号和平台查看ok");
    }

    public List<String> getAccountByPlatId(Integer platId) {
          List<String> list=platformAccountMapper.getAccountByPlatId(platId);
        logger.info("查找买手账号ok{}",list);
        return list;
    }

    public Integer getAccountCountByPlatId(Integer platId) {
       return platformAccountMapper.getAccountCountByPlatId(platId);
    }
}
