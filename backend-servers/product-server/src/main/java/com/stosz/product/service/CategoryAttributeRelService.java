package com.stosz.product.service;

import com.stosz.product.ext.model.CategoryAttributeRel;
import com.stosz.product.mapper.CategoryAttributeRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 品类和属性关系表
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class CategoryAttributeRelService {

	@Resource
	private CategoryAttributeRelMapper mapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 品类与属性关系表 的添加
	 */
	public void insertRel(CategoryAttributeRel car) {
		try {
			mapper.insert(car);
			logger.info("品类: {} 与属性: {} 关系添加成功!", car.getCategoryId(), car.getAttributeId());
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("品类[" + car.getCategoryId() + "]下已经存在了[" + car.getAttributeId() + "]属性");
		}

	}

    /**
     * t同步时候用的，批量插入
     */
    public void insertList(List<CategoryAttributeRel> categoryAttributeRelList) {
        Assert.notNull(categoryAttributeRelList, "品类属性关系集合不允许为空！");
        mapper.insertList(0, categoryAttributeRelList);
        logger.info("批量插入品类属性成功！");
    }

	/**
	 * 根据联合主键品类与属性关系表 的删除(解绑)
	 */
	public void deleteByBind(CategoryAttributeRel car) {
		mapper.deleteByBind(car);
		logger.info("品类ID: {} 与属性ID: {} 解绑成功!", car.getCategoryId(),car.getAttributeId());
	}

	/**
	 * 根据属性id删除品类属性关系表
	 */
	public void deleteByAttributeId(Integer attributeId) {
		mapper.deleteByAttributeId(attributeId);
		logger.info("根据属性ID: {} 删除品类属性关系表成功!", attributeId);
	}

	/**
	 * 品类与属性关系表 的查询
	 * 
	 */
	public CategoryAttributeRel getRel(CategoryAttributeRel categoryAttributeRel) {
		return mapper.getBycategoryIdAndAttributeId(categoryAttributeRel);
	}

	/**
	 * 查询记录数
	 */
	public Integer count(CategoryAttributeRel rel) {
		return mapper.count(rel);
	}

	/**
	 * 通过品类id和属性id查询品类属性的关系 --产品属性的添加
	 */
	public int countByAttrCateID(Integer categoryId, Integer attributeId) {
		return mapper.countByAttrCateID(categoryId, attributeId);
	}

	public Integer countByProduct() {
		return mapper.countByProduct();
	}

	public List<CategoryAttributeRel> findByProduct(Integer limit, Integer start) {
		Assert.notNull(limit, "限制条数不允许为空！");
		Assert.notNull(start, "起始位置不允许为空！");
		return mapper.findByProduct(limit, start);
	}

	/**
	 * 通过产品表，绑定分类和属性的关系
	 */
    @Async("categoryAttributePool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> syncList(List<CategoryAttributeRel> categoryAttributeRelList) {
		Integer result = 0;
        List<CategoryAttributeRel> categoryAttributeRels = new ArrayList<>();
        for (CategoryAttributeRel categoryAttributeRel : categoryAttributeRelList) {
				categoryAttributeRel.setCreator("系统");
				categoryAttributeRel.setCreatorId(0);
            categoryAttributeRels.add(categoryAttributeRel);
            result++;
        }
        if (!categoryAttributeRels.isEmpty()) {
            mapper.insertList(0, categoryAttributeRelList);
        }
        logger.info("通过产品表，绑定分类和属性关系{}成功！", categoryAttributeRels);
        return new AsyncResult<>(result);
	}



}
