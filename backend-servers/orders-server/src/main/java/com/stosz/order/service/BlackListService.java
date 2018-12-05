package com.stosz.order.service;

import com.stosz.order.ext.enums.BlackLevelEnum;
import com.stosz.order.ext.enums.BlackTypeEnum;
import com.stosz.order.ext.model.BlackList;
import com.stosz.order.mapper.order.BlackListMapper;
import com.stosz.plat.common.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author wangqian
 * create at 2017年10月31日
 * 黑名单
 */
@Service
public class BlackListService {

    private final static Logger logger = LoggerFactory.getLogger(BlackListService.class);

    @Autowired
    private BlackListMapper blackListMapper;


    /**
     * 查找黑名单
     * @param blackList
     */
    public RestResult findBlackList(BlackList blackList){


        blackList.setBlackTypeEnum(blackList.getBlackTypeEnum().equals(BlackTypeEnum.other)?null:blackList.getBlackTypeEnum());
        blackList.setBlackLevelEnum(blackList.getBlackLevelEnum().equals(BlackLevelEnum.other)?null:blackList.getBlackLevelEnum());

        blackList.setCreatorId(blackList.getCreatorId()==null||blackList.getCreatorId()==0?null:blackList.getCreatorId());


        RestResult rs = new RestResult();
        int count = blackListMapper.count(blackList);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<BlackList> lst = blackListMapper.find(blackList);
        rs.setItem(lst);
        rs.setDesc("黑名单列表查询成功");
        return rs;
    }


    /**
     * 查找黑名单
     * @param blackList
     */
    public  List<BlackList> findByBlackList(BlackList blackList){
        return blackListMapper.find(blackList);
    }





    /**
     * 查找所有的黑名单创建人
     * @return
     */
    public RestResult findBlackListCreator(){
        RestResult rs = new RestResult();
        BlackList blackList = new BlackList();
        List<BlackList> lst = blackListMapper.findDistinctCreator();
        rs.setTotal(lst.size());
        rs.setItem(lst);
        rs.setDesc("黑名单创建人列表查询成功");
        return rs;
    }



    /**
     * 单条插入黑名单
     * @param blackList
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int addBlackList(BlackList blackList){
        int count = blackListMapper.insert(blackList);
        logger.info("添加黑名单成功! 添加内容:{} ", blackList);
        return count;
    }


    /**
     * 批量插入黑名单
     * @param list
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addBatchBlackList(List<BlackList> list){
        int count = blackListMapper.insertBatch(list);
        logger.info("批量添加黑名单成功! 添加内容:{} ", list);
    }




    /**
     * 单条删除黑名单
     * @param id
     */
    @Transactional(value = "orderTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteBlackList(Integer id){
        BlackList blackList =blackListMapper.getById(id);
        Assert.notNull(blackList, "黑名单id:" + id + "在数据库中不存在！");
        int count = blackListMapper.delete(id);
        logger.info("删除黑名单成功! 添加内容:{} ", blackList);
    }


    public List<BlackList> findByContent(String content){
        return blackListMapper.findByContent(content);
    }







}
