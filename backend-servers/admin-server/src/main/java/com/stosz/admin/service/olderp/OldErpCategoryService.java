package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpCategoryMapper;
import com.stosz.olderp.ext.model.OldErpCategory;
import com.stosz.olderp.ext.service.IOldErpCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 老erp的分类service
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Service
public class OldErpCategoryService implements IOldErpCategoryService {

    @Resource
    private OldErpCategoryMapper oldErpCategoryMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void insert(OldErpCategory oldErpCategory) {
        Assert.notNull(oldErpCategory, "新增的分类不允许为空！");
        int i = oldErpCategoryMapper.insert(oldErpCategory);
        Assert.isTrue(i == 1, "老erp新增分类失败");
        logger.info("新增老erp分类{}成功", oldErpCategory);
    }

    @Override
    public void update(OldErpCategory oldErpCategory) {
        Assert.notNull(oldErpCategory, "不允许将分类修改为空！");
        OldErpCategory oldErpCategoryDB = oldErpCategoryMapper.getById(oldErpCategory.getId());
        Assert.notNull(oldErpCategoryDB, "数据库中不存在该分类！");
        int i = oldErpCategoryMapper.update(oldErpCategory);
        Assert.isTrue(i == 1, "修改老erp分类失败！");
        logger.info("将老erp分类{}修改为{}成功！", oldErpCategoryDB, oldErpCategory);
    }

    @Override
    public void updateStatus(OldErpCategory oldErpCategory) {
        Assert.notNull(oldErpCategory, "不允许将分类修改为空！");
        OldErpCategory oldErpCategoryDB = oldErpCategoryMapper.getById(oldErpCategory.getId());
        Assert.notNull(oldErpCategoryDB, "数据库中不存在该分类！");
        int i = oldErpCategoryMapper.updateStatus(oldErpCategory);
        Assert.isTrue(i == 1, "修改老erp分类的状态失败！");
        logger.info("将老erp分类{}修改为{}成功！", oldErpCategoryDB, oldErpCategory);
    }

    @Override
    public List<OldErpCategory> findAll() {
        return oldErpCategoryMapper.findAll();
    }

    @Override
    public OldErpCategory getById(Integer id) {
        return oldErpCategoryMapper.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        Assert.notNull(id,"id不能为空");
        oldErpCategoryMapper.delete(id);
    }

    @Override
    public OldErpCategory getByName(String name) {
        Assert.notNull(name,"name不能为空");
        return oldErpCategoryMapper.getByName(name);
    }
}
