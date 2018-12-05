package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpProductMapper;
import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.olderp.ext.service.IOldErpProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品全量同步
 *
 * @author he_guitang
 * @version [1.0, 2017年8月22日]
 */
@Service
public class OldErpProductService implements IOldErpProductService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OldErpProductMapper oldErpProductMapper;

    @Override
    public void insert(OldErpProduct oldErpProduct) {
        try {
            Assert.notNull(oldErpProduct, "新增的老erp产品不允许为null！");
            int i = oldErpProductMapper.insert(oldErpProduct);
            Assert.isTrue(i == 1, "新增老erp产品失败！");
            logger.info("新增老erp产品{}成功！");
        } catch (DuplicateKeyException e) {
            logger.info("该产品已经存在！");
        }

    }

    @Override
    public void updateStatus(OldErpProduct oldErpProduct) {
        Assert.notNull(oldErpProduct, "修改的老erp产品不允许为null！");
        OldErpProduct oldErpProductDB = oldErpProductMapper.getById(oldErpProduct.getId());
        Assert.notNull(oldErpProduct, "老erp数据库中不存在要修改的产品");
        int i = oldErpProductMapper.updateStatus(oldErpProduct);
        Assert.isTrue(i == 1, "修改老erp产品状态失败！");
        logger.info("老erp将产品{}修改为{} 成功！", oldErpProductDB, oldErpProduct);
    }

    @Override
    public void update(OldErpProduct oldErpProduct) {
        Assert.notNull(oldErpProduct, "修改的老erp产品不允许为null！");
        OldErpProduct oldErpProductDB = oldErpProductMapper.getById(oldErpProduct.getId());
        Assert.notNull(oldErpProduct, "老erp数据库中不存在要修改的产品");
        int i = oldErpProductMapper.update(oldErpProduct);
        Assert.isTrue(i == 1, "修改老erp产品状态失败！");
        logger.info("老erp将产品{}修改为{} 成功！", oldErpProductDB, oldErpProduct);
    }

    @Override
    public OldErpProduct getById(Integer id) {
        Assert.notNull(id, "查询的产品id不允许为空！");
        return oldErpProductMapper.getById(id);
    }

    @Override
    public List<OldErpProduct> findByLimit(Integer limit, Integer start) {
        limit = limit == null ? 2000 : limit;
        start = start == null ? 0 : start;
        return oldErpProductMapper.findByLimit(limit, start);
    }

    @Override
    public List<Integer> findAllId() {
        return oldErpProductMapper.findAllId();
    }

    @Override
    public Integer count() {
        return oldErpProductMapper.count();
    }

    @Override
    public Integer getTotalStockById(Integer productId) {
        return oldErpProductMapper.getTotalStockById(productId);
    }

    @Override
    public List<OldErpProduct> findCheckById(Integer productId) {
        return oldErpProductMapper.findByProductId(productId);
    }
}
