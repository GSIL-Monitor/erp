package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpCurrencyMapper;
import com.stosz.olderp.ext.model.OldErpCurrency;
import com.stosz.olderp.ext.service.IOldErpCurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author carter
 * @version [1.0 , 2017/9/11]
 */
@Service
public class OldErpCurrencyService implements IOldErpCurrencyService {

    @Resource
    private OldErpCurrencyMapper oldErpCurrencyMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<OldErpCurrency> findAll() {
        return oldErpCurrencyMapper.findAll();
    }

    @Override
    public void insert(OldErpCurrency OldErpCurrency) {
        Assert.notNull(OldErpCurrency, "不允许往老erp数据库插入空的币种信息！");
        int i = oldErpCurrencyMapper.insert(OldErpCurrency);
        Assert.isTrue(i == 1, "插入老erp数据库币种信息失败！");
        logger.info("将{}插入老erp数据库币种信息成功！", OldErpCurrency);
    }

    @Override
    public void update(OldErpCurrency OldErpCurrency) {
        Assert.notNull(OldErpCurrency, "不允许往老erp数据库插入空的币种信息！");
        com.stosz.olderp.ext.model.OldErpCurrency OldErpCurrencyDB = oldErpCurrencyMapper.getById(OldErpCurrency.getId());
        Assert.notNull(OldErpCurrencyDB, "要修改的信息在数据库中不存在！");
        int i = oldErpCurrencyMapper.update(OldErpCurrency);
        Assert.isTrue(i == 1, "插入老erp数据库币种信息失败！");
        logger.info("将{}插入老erp数据库币种信息成功！", OldErpCurrency);
    }

    @Override
    public OldErpCurrency getById(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        return oldErpCurrencyMapper.getById(id);
    }

    @Override
    public OldErpCurrency getByUnique(String title) {
        Assert.notNull(title, "入参不允许为空！");
        return oldErpCurrencyMapper.getByUnique(title);
    }

    @Override
    public OldErpCurrency getByCode(String code) {
        Assert.notNull(code, "入参不允许为空！");
        return oldErpCurrencyMapper.getByCode(code);
    }

    @Override
    public void deleteById(Integer id) {
        Assert.notNull(id, "id不允许为空！");
        oldErpCurrencyMapper.deleteById(id);
    }
}
