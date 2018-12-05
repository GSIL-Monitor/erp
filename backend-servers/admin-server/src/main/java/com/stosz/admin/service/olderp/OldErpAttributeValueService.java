package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpAttributeValueMapper;
import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.olderp.ext.service.IOldErpAttributeValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 老erp产品属性值的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/31]
 */
@Service
public class OldErpAttributeValueService implements IOldErpAttributeValueService {

    @Resource
    private OldErpAttributeValueMapper oldErpAttributeValueMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void insert(OldErpAttributeValue oldErpAttributeValue) {
        Assert.notNull(oldErpAttributeValue,"老erp的属性值为空");
        int i = oldErpAttributeValueMapper.insert(oldErpAttributeValue);
        Assert.isTrue(i == 1, "插入老erp产品属性值失败！");
        logger.info("将{}插入到老erp产品属性值表成功！", oldErpAttributeValue);
    }

    @Override
    public void delete(Integer id) {
        OldErpAttributeValue oldErpAttributeValueDB = oldErpAttributeValueMapper.getById(id);
        Assert.notNull(oldErpAttributeValueDB, "要删除的产品属性值在系统中不存在！");
        int i = oldErpAttributeValueMapper.delete(id);
        Assert.isTrue(i == 1, "删除产品属性值失败！");
        logger.info("将老erp产品属性值{}删除成功！", oldErpAttributeValueDB);
    }

    @Override
    public void update(OldErpAttributeValue oldErpAttributeValue) {
        Assert.notNull(oldErpAttributeValue,"老erp的属性值为空");
        OldErpAttributeValue oldErpAttributeValueDB = oldErpAttributeValueMapper.getById(oldErpAttributeValue.getId());
        Assert.notNull(oldErpAttributeValueDB, "要修改的产品属性值在系统中不存在！");
        int i = oldErpAttributeValueMapper.update(oldErpAttributeValue);
        Assert.isTrue(i == 1, "修改老erp产品属性值失败！");
        logger.info("将老erp产品属性值{}修改为{}成功！", oldErpAttributeValueDB, oldErpAttributeValue);
    }

    @Override
    public OldErpAttributeValue getById(Integer id) {
        return oldErpAttributeValueMapper.getById(id);
    }

    @Override
    public int count() {
        return oldErpAttributeValueMapper.count();
    }

    @Override
    public List<OldErpAttributeValue> findByLimit(Integer limit, Integer start) {
        Assert.notNull(limit, "限制条数不允许为空！");
        Assert.notNull(start, "起始位置不允许为空！");
        return oldErpAttributeValueMapper.findByLimit(limit, start);
    }

    @Override
    public OldErpAttributeValue getByUnique(Integer productId, Integer attributeId, String title) {
        return oldErpAttributeValueMapper.getByUinque(productId, attributeId, title);
    }

    @Override
    public List<OldErpAttributeValue> findByAttributeId(Integer productId, Integer attributeId) {
        return oldErpAttributeValueMapper.findByAttributeId(productId, attributeId);
    }

    @Override
    public List<OldErpAttributeValue> findCheckByAttributeId(Integer productId, Integer attributeId) {
        return oldErpAttributeValueMapper.findCheckByAttributeId(productId, attributeId);
    }

    @Override
    public List<OldErpAttributeValue> findByAttributeValue(String attributeValues) {
        Assert.hasLength(attributeValues,"属性值id组合不允许为空！");
        return oldErpAttributeValueMapper.findByAttributeValue(attributeValues);
    }
}
