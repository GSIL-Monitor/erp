package com.stosz.product.service;

import com.stosz.product.ext.model.ProductSkuAttributeValueRel;
import com.stosz.product.mapper.ProductSkuAttributeValueRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 产品sku与属性,属性值的关系
 *
 * @author he_guitang
 *
 */
@Service
public class ProductSkuAttributeValueRelService {
	@Resource
	private ProductSkuAttributeValueRelMapper mapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 产品sku和属性值关联表的插入
	 */
	public void insert(ProductSkuAttributeValueRel param) {
		try {
			mapper.insert(param);
			logger.info("产品sku和属性值关联表插入成功: {} ", param);
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("sku[" + param.getSku() + "]和属性[ID" + param.getAttributeId() + "]的关系已经存在,不能重复生成!");
		}
	}
	
	/**
	 * 批量插入
	 */
	public void insertList(List<ProductSkuAttributeValueRel> param) {
		Assert.notNull(param, "不允许插入空的SKU属性集合！");
		try{
			mapper.insertList(0, param);
			logger.info("产品sku和属性值的关系表: {} 插入成功!", param);
		} catch (DuplicateKeyException e) {
			logger.info("sku与属性的关系已经存在: {} ,不能重复添加!", param);
        	throw new IllegalArgumentException("sku和属性的关系已经存在,不能重复生成!");
        } catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	public int deleteByProductId(Integer productId) {
		int count = mapper.deleteByProduct(productId);
		logger.info("根据产品ID: {} 删除产品sku属性值关系表成功,共删除: {} 条", productId, count);
		return count;
	}

	/**
	 * 同步老erp的SKu时用到
	 *
	 * @author xiongchenyang 2017/9/12
	 */
	public void insertOld(ProductSkuAttributeValueRel param) {
		Assert.notNull(param, "不允许插入空的产品SKu属性值关联关系！");
		try {
			mapper.insert(param);
		} catch (DuplicateKeyException e) {
			// param = productSkuAttributeValueRelMapper.getByUnique(param.getAttributeId(),param.getSku());
			logger.info("产品sku和产品属性值关联关系中，sku{}已经存在了属性{}", param.getSku(), param.getAttributeId());
		}
	}

	/**
	 * 根据属性查询sku
	 */
	public int countByAttributeId(Integer attributeId) {
		Assert.notNull(attributeId, "请输入属性id");
		return mapper.countByAttributeId(attributeId);
	}

	/**
	 * 根据属性值查询sku
	 */
	public int countByAttributeValueId(Integer attributeValueId) {
		Assert.notNull(attributeValueId, "请输入属性值id");
		return mapper.countByAttributeValueId(attributeValueId);
	}
	
	/**
	 * 查询某产品下的属性值是否生成了sku
	 */
	public int countByPcAttrValId(Integer productId,Integer attributeValueId) {
		Assert.notNull(productId, "请输入产品id");
		Assert.notNull(attributeValueId, "请输入属性值id");
		return mapper.countByPcAttrValId(productId, attributeValueId);
	}
	
	/**
	 * 根据id统计产品sku
	 */
	public int countById(ProductSkuAttributeValueRel param){
		return mapper.countById(param);
	}
	
	/**
	 * 根据产品查询sku
	 */
	public int countByProductId(Integer productId) {
		Assert.notNull(productId, "请输入产品id");
		return mapper.countByProductId(productId);
	}

	/**
	 * 根据产品id获取所有的sku
	 */
	public List<ProductSkuAttributeValueRel> findByProductId(Integer productId) {
		return mapper.findByProductId(productId);
	}

	/**
	 * 统计sku的条数
	 */
	public int countSku(Integer productId, List<Integer> attributeIds) {
		return mapper.countSku(productId, attributeIds);
	}

	/**
	 * 根据属性值id查询sku
	 */
	public ProductSkuAttributeValueRel getSkuByAttrValueIds(Integer productId, List<Integer> attributeIds) {
		return mapper.getSkuByAttrValueIds(productId, attributeIds);
	}

	/**
	 * 获取该产品最大的sku
	 */
	public String maxSkuByProductId(Integer productId) {
		return mapper.maxSkuByProductId(productId);
	}
	
	/**
	 * 查询某产品的属性值组合是否已经有了sku
	 */
	public List<ProductSkuAttributeValueRel> findByProductIdAttrValueIds(ProductSkuAttributeValueRel param){
		return mapper.findByProductIdAttrValueIds(param);
	}
	
	public ProductSkuAttributeValueRel getByUnique(Integer attributeId, String sku) {
		return mapper.getByUnique(attributeId, sku);
	}

	public List<ProductSkuAttributeValueRel> findBySku(String sku) {
		return mapper.findBySku(sku);
	}

	public String getSkuByProductAttrVal(Integer productId, List<Integer> attValIds, Integer attValNumber){
		if (attValIds == null || attValIds.size() ==0) return null;
		return mapper.getSkuByProductAttrVal(productId, attValIds, attValNumber);
	}

}
