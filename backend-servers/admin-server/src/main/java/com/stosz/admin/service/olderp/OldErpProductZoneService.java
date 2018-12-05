package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpProductZoneMapper;
import com.stosz.olderp.ext.model.OldErpProductZone;
import com.stosz.olderp.ext.service.IOldErpProductZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/6]
 */
@Service
public class OldErpProductZoneService implements IOldErpProductZoneService {

    @Resource
    private OldErpProductZoneMapper oldErpProductZoneMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void insert(OldErpProductZone oldErpProductZone) {
        Assert.notNull(oldErpProductZone, "不允许添加空的产品区域信息！");
        oldErpProductZoneMapper.insert(oldErpProductZone);
        logger.info("添加老erp产品区域信息{} 成功！", oldErpProductZone);
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id, "传入的id不允许为空！");
        OldErpProductZone oldErpProductZone = oldErpProductZoneMapper.getById(id);
        Assert.notNull(oldErpProductZone, "要删除的产品区域关系在数据库中不存在");
        int i = oldErpProductZoneMapper.delete(id);
        Assert.isTrue(i == 1, "删除产品区域关系失败！");
        logger.info("删除产品区域关系{}成功！", oldErpProductZone);
    }

    @Override
    public void update(OldErpProductZone oldErpProductZone) {
        Assert.notNull(oldErpProductZone, "不允许添加空的产品区域信息！");
        OldErpProductZone oldErpProductZoneDB = oldErpProductZoneMapper.getById(oldErpProductZone.getId());
        Assert.notNull(oldErpProductZoneDB, "要修改的产品区域关系在数据库中不存在！");
        int i = oldErpProductZoneMapper.update(oldErpProductZone);
        Assert.isTrue(i == 1, "老erp修改产品区域信息失败！");
        logger.info("将老erp产品区域信息{}修改为{} 成功！", oldErpProductZoneDB, oldErpProductZone);
    }

    @Override
    public OldErpProductZone getById(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        return oldErpProductZoneMapper.getById(id);
    }

    @Override
    public Integer count() {
        return oldErpProductZoneMapper.count();
    }

    @Override
    public List<OldErpProductZone> findByLimit(Integer limit, Integer start) {
        Assert.notNull(limit, "限制条数limit不允许为空！");
        Assert.notNull(start, "起始位置start不允许为空！");
        return oldErpProductZoneMapper.findByLimit(limit, start);
    }

    @Override
    public List<OldErpProductZone> findByDate(Date startTime, Date endTime) {
        Assert.notNull(startTime, "起始时间startTime不允许为空！");
        Assert.notNull(endTime, "结束时间endTime不允许为空！");
        return oldErpProductZoneMapper.findByDate(startTime, endTime);
    }

    @Override
    public List<OldErpProductZone> findByProductId(Integer productId) {
        Assert.notNull(productId, "产品Id不能为空！");
        return oldErpProductZoneMapper.findByProductId(productId);

    }

    @Override
    public List<OldErpProductZone> getByUnique(Integer productId, Integer departmentId, Integer zoneId) {
        Assert.notNull(productId, "产品Id不能为空！");
        return oldErpProductZoneMapper.getByUnique(productId, departmentId, zoneId);
    }

}
