package com.stosz.product.service;

import com.stosz.product.ext.model.ProductAttributeValueRel;
import com.stosz.product.mapper.ProductAttributeValueRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品属性值关系表
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class ProductAttributeValueRelService {

	@Resource
	private ProductAttributeValueRelMapper mapper;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 产品属性值关系表的增加
	 */
	public void add(ProductAttributeValueRel param) {
		try {
			mapper.insert(param);
			logger.info("产品ID: {} 与属性值ID: {} 关联成功!", param.getProductId(),param.getAttributeValueId());
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("产品ID["+param.getProductId()+"]已经绑定了属性值ID["+param.getAttributeValueId()+"]");
		}
		
	}

    public void insertOld(ProductAttributeValueRel param) {
        try {
            mapper.insert(param);
            logger.info("产品ID: {} 与属性值ID: {} 关联成功!", param.getProductId(), param.getAttributeValueId());
        } catch (DuplicateKeyException e) {
            param = mapper.getByUnique(param.getProductAttributeId(), param.getAttributeValueId());
            logger.info("产品属性值关联表中，产品{}已经存在属性值{}", param.getProductId(), param.getAttributeValueId());
        }
    }

	/**
	 * 根据产品id删除 产品属性值关系表中的记录
	 */
	public void deleteRel(Integer productId) {
		mapper.deleteByProductId(productId);
		logger.info("根据产品ID: {} 删除产品与属性值关联关系成功!", productId);
	}

	/**
	 * 根据属性值id删除产品属性值关系记录
	 */
	public void deleteByAttributeValueId(Integer attributeValueId){
		mapper.deleteByAttributeValueId(attributeValueId);
		logger.info("根据属性值ID: {} 删除产品与属性值关联关系成功!", attributeValueId);
	}
	
	/**
	 * 根据产品属性关系表id删除绑定的属性值
	 */
	public void deleteByProductAttributeId(Integer productAttributeId) {
		mapper.deleteByProductAttributeId(productAttributeId);
		logger.info("根据产品属性关系表ID: {} 删除产品与属性值关联关系成功!", productAttributeId);
    }
	
	/**
	 * 属性值的解绑
	 */
	public void unBinding(ProductAttributeValueRel param){
		mapper.deleteByProductValueId(param);
		logger.info("根据产品ID: {} 和属性值ID: {} 删除(解绑)产品与属性值关联表成功", param.getProductId(),param.getAttributeValueId());
	}
	
	public int countByProductId(Integer productId){
		return mapper.countByProductId(productId);
	}
	
	public List<ProductAttributeValueRel> queryByProductId(Integer productId){
		return mapper.queryByProductId(productId);
	}

	public ProductAttributeValueRel getByUnique(Integer productAttributeId, Integer attributeValueId) {
		Assert.notNull(productAttributeId, "产品-属性id不能为空");
		Assert.notNull(attributeValueId, "产品属性值id不能为空");
		return mapper.getByUnique(productAttributeId, attributeValueId);
	}

	public List<ProductAttributeValueRel> findByDate(ProductAttributeValueRel param) {
		Assert.notNull(param.getMinCreateAt(), "起始时间不允许为空！");
		Assert.notNull(param.getMaxCreateAt(), "结束时间不允许为空！");
		return mapper.findByDate(param);
	}

	public int countByDate(LocalDateTime startTime, LocalDateTime endTime) {
		Assert.notNull(startTime, "起始时间不允许为空！");
		Assert.notNull(endTime, "结束时间不允许为空！");
		return mapper.countByDate(startTime, endTime);
	}

    public List<ProductAttributeValueRel> findByProductId(Integer productId) {
        Assert.notNull(productId, "产品id不允许为空！");
        return mapper.findByProductId(productId);
    }


    public void insertList(List<ProductAttributeValueRel> productAttributeValueRelList) {
        Assert.notNull(productAttributeValueRelList, "插入的产品属性值结合不能为空");
        mapper.insertList(0, productAttributeValueRelList);
    }

    public List<ProductAttributeValueRel> findByProductIds(Integer productId, List<Integer> idList){
		List<ProductAttributeValueRel> list = mapper.findByProductIds(productId, idList);
		return list;
	}

}
