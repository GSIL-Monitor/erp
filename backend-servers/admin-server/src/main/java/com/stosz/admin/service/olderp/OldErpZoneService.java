package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpZoneMapper;
import com.stosz.olderp.ext.model.OldErpZone;
import com.stosz.olderp.ext.service.IOldErpZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 老erp区域表的service实现
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/12]
 */
@Service
public class OldErpZoneService implements IOldErpZoneService {

    @Resource
    private OldErpZoneMapper oldErpZoneMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void insert(OldErpZone oldErpZone) {
        Assert.notNull(oldErpZone, "不允许添加空的区域！");
        int i = oldErpZoneMapper.insert(oldErpZone);
        Assert.isTrue(i == 1, "添加老erp区域信息失败！");
        logger.info("添加老erp区域信息{}成功！", oldErpZone);
    }

    @Override
    public void update(OldErpZone oldErpZone) {
        Assert.notNull(oldErpZone, "不允许添加空的区域！");
        OldErpZone oldErpZoneDB = oldErpZoneMapper.getById(oldErpZone.getId());
        Assert.notNull(oldErpZoneDB, "要修改的区域在数据库中不存在！");
        int i = oldErpZoneMapper.update(oldErpZone);
        Assert.isTrue(i == 1, "修改老erp区域信息失败！");
        logger.info("修改老erp区域信息{}为{}成功！", oldErpZoneDB, oldErpZone);
    }

    @Override
    public OldErpZone getById(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        return oldErpZoneMapper.getById(id);
    }

    @Override
    public List<OldErpZone> findAll() {
        return oldErpZoneMapper.findAll();
    }

    @Override
    public void deleteByIdZone(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        oldErpZoneMapper.deleteByIdZone(id);
    }
}
