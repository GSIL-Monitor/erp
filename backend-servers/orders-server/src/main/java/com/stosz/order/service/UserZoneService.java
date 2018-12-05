package com.stosz.order.service;

import com.stosz.admin.ext.service.IUserService;
import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.order.ext.model.UserZone;
import com.stosz.order.mapper.order.UserZoneMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.service.IZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangqian
 * 客服与地区
 */
@Service
public class UserZoneService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserZoneMapper userZoneMapper;

    @Autowired
    private IZoneService zoneService;

    @Autowired
    private IUserService userService;


    /**
     * 查找客服与地区
     * @param userZone
     */
    public RestResult findUserZone(UserZone userZone){
        RestResult rs = new RestResult();
        int count = userZoneMapper.count(userZone);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<UserZone> lst = userZoneMapper.find(userZone);
        rs.setItem(lst);
        rs.setDesc("客服与地区列表查询成功");
        return rs;
    }

    public UserZone findById(Integer id){
        return userZoneMapper.getById(id);
    }

    public int update(UserZone userZone){
        userZoneMapper.update(userZone);
        logger.info("客服区域[id={}]修改成功",userZone.getId());
        return 1;
    }

    public int delete(Integer id){
        userZoneMapper.delete(id);
        logger.info("客服区域[id={}]删除成功",id);
        return 1;
    }

    public int add(UserZone userZone){
        try {
            userZoneMapper.insert(userZone);
            logger.info("客服区域: {} 添加成功", userZone);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("客服区域["+userZone.getZoneName()+ " " + userZone.getUserName()+"]已存在");
        }
        return 1;
    }

    public int add(List<UserZone> userZoneList){
        int count = 0;
        for(UserZone userZone : userZoneList){
            try {
                userZoneMapper.insert(userZone);
                count++;
                logger.info("客服区域: {} 添加成功", userZone);
            } catch (DuplicateKeyException e) {
                throw new IllegalArgumentException("客服区域["+userZone.getZoneName()+ " " + userZone.getUserName()+"]已存在");
            }
        }
        return count;
    }

    public List<UserZone> findEnableUserZoneByUserId(Integer userId){
        return userZoneMapper.findByUserId(userId, UseableEnum.use.ordinal());
    }
}
