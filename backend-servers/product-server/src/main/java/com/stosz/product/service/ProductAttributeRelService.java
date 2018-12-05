package com.stosz.product.service;

import com.stosz.product.ext.model.CategoryAttributeRel;
import com.stosz.product.ext.model.ProductAttributeRel;
import com.stosz.product.mapper.ProductAttributeRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品属性关系表
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class ProductAttributeRelService {

    @Resource
    private ProductAttributeRelMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据产品id删除 产品属性关系表中的记录
     */
    public void deleteByProductId(Integer productId) {
        mapper.deleteByProductId(productId);
        logger.info("根据产品id: {} 删除产品属性关系表中的记录成功", productId);
    }

    /**
     * 根据属性id删除产品属性关系表
     */
    public void deleteByAttributeId(Integer attributeId) {
        mapper.deleteByAttributeId(attributeId);
        logger.info("根据属性id: {} 删除产品属性关系表成功", attributeId);
    }

    /**
     * 根据产品属性id删除产品属性关系表 --产品属性的解绑
     */
    public void deleteByUnBind(ProductAttributeRel param) {
        mapper.deleteByUnBind(param);
        logger.info("产品属性关系表中的产品ID: {} 与属性ID: {} 的关系删除(解绑)成功", param.getProductId(), param.getAttributeId());
    }

    public void insert(ProductAttributeRel param) {
        Assert.notNull(param, "产品-属性关联表不允许插入空值！");
        try {
            int i = mapper.insert(param);
            Assert.isTrue(i == 1, "插入产品属性关联表失败！");
            logger.info("插入产品属性关联关系: {} 成功！", param);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("产品[" + param.getProductId() + "]下已经存在了[" + param.getAttributeId() + "]属性");
        }
    }

    //----------------------同步用的-------------------

    /**
     * @author xiongchenyang 2017/9/12
     * 同步时插入的逻辑
     */
    public void insertOld(ProductAttributeRel param) {
        Assert.notNull(param, "产品-属性关联表不允许插入空值！");
        int i = mapper.insert(param);
        Assert.isTrue(i == 1, "插入产品属性关联表失败！");
        logger.info("插入产品属性关联关系: {} 成功！", param);

    }

    public void insertList(List<ProductAttributeRel> productAttributeRelList) {
        Assert.notNull(productAttributeRelList, "不允许插入空的产品属性关系集合！");
        mapper.insertList(0, productAttributeRelList);
        logger.info("插入产品属性关联成功-----------------集合{}", productAttributeRelList);
    }
    //--------------------------------------------------

    public void update(ProductAttributeRel param) {
        Assert.notNull(param, "不允许将产品属性关联关系修改为空！");
        ProductAttributeRel productAttributeRelDB = mapper.getById(param.getId());
        Assert.notNull(productAttributeRelDB, "要修改的关联关系在数据库中不存在！");
        try {
            int i = mapper.update(param);
            Assert.isTrue(i == 1, "修改产品属性关联关系失败！");
            logger.info("将产品属性关联: {} 修改为: {} 成功！", productAttributeRelDB, param);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("产品[" + param.getProductId() + "]下已经存在了[" + param.getAttributeId() + "]属性");
        }
    }

    public ProductAttributeRel getById(Integer id) {
        return mapper.getById(id);
    }

//    @Cacheable(value = "getProductAttributeRel", unless = "#result == null")
    public ProductAttributeRel getByAttribute(Integer attributeId, Integer productId) {
        Assert.notNull(attributeId, "属性ID不允许为空！");
        Assert.notNull(productId, "产品ID不允许为空！");
        return mapper.getByAttrProductId(attributeId, productId);
    }
    
    public ProductAttributeRel getByAttrProductId(Integer attributeId, Integer productId) {
        Assert.notNull(attributeId, "属性ID不允许为空！");
        Assert.notNull(productId, "产品ID不允许为空！");
        return mapper.getByAttrProductId(attributeId, productId);
    }
    
    public List<ProductAttributeRel> findByDate(ProductAttributeRel param) {
        Assert.notNull(param.getMinCreateAt(), "起始时间不允许为空！");
        Assert.notNull(param.getMaxCreateAt(), "结束时间不允许为空！");
        return mapper.findByDate(param);
    }

    public int countByDate(LocalDateTime startTime, LocalDateTime endTime) {
        return mapper.countByDate(startTime, endTime);
    }

    /**
     * 根据产品id和属性id查询出关联表rel的id
     */
    public ProductAttributeRel findRelId(Integer productId, Integer attributeId) {
        return mapper.findRelId(productId, attributeId);
    }

    /**
     * 根据属性查询是否有spu 属性id==>产品==spu
     */
    public int countByAttributeId(Integer attributeId) {
        return mapper.countByAttributeId(attributeId);
    }
    
    /**
     * 统计某品类下的产品是否绑定了属性
     */
    public int countByCategoryAttributeId(CategoryAttributeRel param){
    	return mapper.countByCategoryAttributeId(param);
    }
    
    public int countByProductId(Integer productId){
    	return mapper.countByProductId(productId);
    }
    
    public List<ProductAttributeRel> findByProductId(Integer productId) {
        Assert.notNull(productId, "产品id不允许为空！");
        return mapper.findByProductId(productId);
    }

}
