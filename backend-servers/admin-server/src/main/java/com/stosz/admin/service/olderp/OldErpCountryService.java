package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpCountryMapper;
import com.stosz.olderp.ext.model.OldErpCountry;
import com.stosz.olderp.ext.service.IOldErpCountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
@Service
public class OldErpCountryService implements IOldErpCountryService {

    @Resource
    private OldErpCountryMapper oldErpCountryMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<OldErpCountry> findAll() {
        return oldErpCountryMapper.findAll();
    }

    @Override
    public void insert(OldErpCountry oldErpCountry) {
        Assert.notNull(oldErpCountry, "不允许往老erp数据库插入空的国家信息！");
        int i = oldErpCountryMapper.insert(oldErpCountry);
        Assert.isTrue(i == 1, "插入老erp数据库国家信息失败！");
        logger.info("将{}插入老erp数据库国家信息成功！", oldErpCountry);
    }

    @Override
    public void update(OldErpCountry oldErpCountry) {
        Assert.notNull(oldErpCountry, "不允许往老erp数据库插入空的国家信息！");
        OldErpCountry oldErpCountryDB = oldErpCountryMapper.getById(oldErpCountry.getId());
        Assert.notNull(oldErpCountryDB, "要修改的信息在数据库中不存在！");
        int i = oldErpCountryMapper.update(oldErpCountry);
        Assert.isTrue(i == 1, "插入老erp数据库国家信息失败！");
        logger.info("将{}插入老erp数据库国家信息成功！", oldErpCountry);
    }

    @Override
    public OldErpCountry getByUnique(String title) {
        Assert.notNull(title, "入参不允许为空！");
        return oldErpCountryMapper.getByUnique(title);
    }

    @Override
    public void deleteById(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        oldErpCountryMapper.deleteById(id);
    }

    @Override
    public OldErpCountry getById(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        return oldErpCountryMapper.getById(id);
    }
}
