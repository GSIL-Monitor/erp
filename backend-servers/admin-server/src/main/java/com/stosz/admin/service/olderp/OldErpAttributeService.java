package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpAttributeMapper;
import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 老erp产品属性的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Service
public class OldErpAttributeService implements IOldErpAttributeService {

    @Resource
    private OldErpAttributeMapper oldErpAttributeMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void insert(OldErpAttribute oldErpAttribute) {
        Assert.notNull(oldErpAttribute, "插入老erp的属性信息不允许为空！");
        int i = oldErpAttributeMapper.insert(oldErpAttribute);
        Assert.isTrue(i == 1, "插入老erp的属性信息失败！");
        logger.info("插入老erp的属性信息{}成功！", oldErpAttribute);
    }

    @Override
    public void delete(Integer id) {
        OldErpAttribute oldErpAttributeDB = oldErpAttributeMapper.getById(id);
        int i = oldErpAttributeMapper.delete(id);
        Assert.isTrue(i == 1, "删除老erp的属性信息失败！");
        logger.info("删除老erp的属性{}成功！", oldErpAttributeDB);
    }


    @Override
    public void update(OldErpAttribute oldErpAttribute) {
        Assert.notNull(oldErpAttribute, "不允许将老erp的属性信息修改为空！");
        OldErpAttribute oldErpAttributeDB = oldErpAttributeMapper.getById(oldErpAttribute.getId());
        Assert.notNull(oldErpAttributeDB, "数据库中不存在该属性记录！");
        int i = oldErpAttributeMapper.update(oldErpAttribute);
        Assert.isTrue(i == 1, "更新老erp的属性信息失败！");
        logger.info("将老erp的属性信息{}修改为{}成功！", oldErpAttributeDB, oldErpAttribute);
    }

    @Override
    public List<OldErpAttribute> findByLimit(Integer limit, Integer start) {
        limit = limit == null ? 2000 : limit;
        start = start == null ? 0 : start;
        List<OldErpAttribute> oldErpAttributeList = oldErpAttributeMapper.find(limit, start);
        Assert.notNull(oldErpAttributeList, "获取老erp属性失败！");
        return oldErpAttributeList;
    }

    @Override
    public OldErpAttribute getById(Integer id) {
        return oldErpAttributeMapper.getById(id);
    }

    @Override
    public OldErpAttribute getByUnique(Integer productId, String title) {
        Assert.notNull(productId, "产品id不允许为空！");
        Assert.notNull(title, "title不允许为空！");
        return oldErpAttributeMapper.getByUnique(productId, title.trim());
    }

    @Override
    public Integer count() {
        return oldErpAttributeMapper.count();
    }

    @Override
    public Integer countByTitle(Integer productId, String title) {
        return oldErpAttributeMapper.countByTitle(productId, title);
    }

    @Override
    public List<OldErpAttribute> findByProductId(Integer productId) {
        return oldErpAttributeMapper.findByProductId(productId);
    }

    @Override
    public List<OldErpAttribute> findCheckByProductId(Integer productId) {
        return oldErpAttributeMapper.findCheckByProductId(productId);
    }


}
