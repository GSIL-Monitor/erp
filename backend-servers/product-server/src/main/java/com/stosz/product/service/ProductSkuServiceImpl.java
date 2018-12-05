package com.stosz.product.service;

import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.*;
import com.stosz.product.ext.service.IProductSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by csn on 2017/11/9.
 */
@Service
public class ProductSkuServiceImpl implements IProductSkuService {

	@Resource
	private ProductService productService;
	@Resource
	private ProductSkuService productSkuService;
	@Resource
	private ProductAttributeValueRelService productAttributeValueRelService;
	@Resource
	private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 根据sku返回属性值的组合
	 */
	@Override
	public Map<String, String> getAttrValueCombinations(List<String> skuList) {
		if (CollectionUtils.isNullOrEmpty(skuList)) {
			return null;
		}
		// 获取sku集合所属的产品id
		Set<Integer> productIds = productSkuService.findBySkus(skuList);
		if (CollectionUtils.isNullOrEmpty(productIds)) {
			return null;
		}
		Map<String, String> map = new HashMap<>();
		for (Integer id : productIds) {
			List<ProductSku> list = productService.productSkuList(id);
			for (ProductSku entity : list) {
				StringBuilder sb = new StringBuilder();
				for (AttributeValue av : entity.getAttributeValues()) {
					sb.append(av.getTitle() + "_");
				}
				if (sb.length() > 0) {
					sb.deleteCharAt(sb.length() - 1);
				}
				map.put(entity.getSku(), sb.toString());
			}
		}
		return map;
	}

	@Override
	public Map<String, String> findAttrValueTitleBySku(List<String> skuList) {
		List<ProductSku> list = productSkuService.findBySkuList(skuList);
		Map<String, String> map = new HashMap<>();
		if (list == null || list.size() == 0) {
			return map;
		}
		for (ProductSku productSku : list) {
			map.put(productSku.getSku(), productSku.getAttributeValueTitle());
		}
		return map;
	}

	@Override
	public String getAttrValueTitleBySku(String sku) {
		ProductSku productSku = productSkuService.getAttrValueTitleBySku(sku);
		if (productSku == null) {
			return "";
		}
		return productSku.getAttributeValueTitle();
	}

	@Override
	public String getSpuBySku(String sku) {
		ProductSku productSku = productSkuService.getAttrValueTitleBySku(sku);
		if (productSku == null) {
			return "";
		}
		return productSku.getSpu();
	}

	@Override
	public String getSkuByProductRelId(Integer id, List<Integer> relIds) {
		if (id == null) {
			logger.info("查询sku的时候,参数没有传入id,id为空");
			return null;
		}
		if (relIds == null || relIds.size() == 0){
			List<ProductSku> list = productSkuService.findByProductId(id);
			if (list == null || list.size() == 0) {
				logger.info("产品id: {} 没有绑定属性值,直接查询sku的时候,未查询到结果!", id);
				return null;
			}
			if (list.size() != 1) {
				logger.info("产品id: {} 没有绑定属性值,直接查询sku的时候,查询结果条数不等于1,结果集为: {} !", id, list);
				return null;
			}
			return list.get(0).getSku();
		}else {
			List<ProductAttributeValueRel> relList = productAttributeValueRelService.findByProductIds(id, relIds);
			if (relList == null || relList.size() != relIds.size()){
				logger.info("产品id: {} 有绑定的属性值,但是数据库中不存在,请检查参数: {}", id, relIds);
				return null;
			}
			List<Integer> valIds = relList.stream().map(ProductAttributeValueRel::getAttributeValueId).collect(Collectors.toList());
			String sku = getSkuByProductAttrVal(id, valIds);
			return sku;
		}
	}

	@Override
	public ProductSku getBySku(String sku) {
		if (sku == null || "".equals(sku)) return null;
		ProductSku productSku = productSkuService.getBySku(sku);
		return productSku;
	}

	@Override
	public List<ProductSku> findBySkuList(List<String> skuList) {
		if (skuList == null || skuList.size() == 0) return null;
		List<ProductSku> list = productSkuService.findBySkuList(skuList);
		return list;
	}

	@Override
	public List<ProductSku> findBySpu(String spu) {
		if (spu == null || "".equals(spu)) {
			logger.info("通过spu查询sku的时候,参数为空: {} ", spu);
			return null;
		}
		Product product = productService.getBySpu(spu);
		if (product == null){
			logger.info("通过spu: {} 查询产品,返回结果为: {}", spu, product);
			return null;
		}
		List<ProductSku> productSkuList = productSkuService.findByProductId(product.getId());
		return productSkuList;
	}

	@Override
	public String getSkuByProductAttrVal(Integer id, List<Integer> attValIds) {
		if (id == null) {
			logger.info("查询sku的时候,没有传入id,id为空");
			return null;
		}
		if (attValIds == null || attValIds.size() == 0){
			List<ProductSku> list = productSkuService.findByProductId(id);
			if (list == null || list.size() == 0) {
				logger.info("产品id: {} 没有绑定属性值,直接查询sku的时候,未查询到结果!", id);
				return null;
			}
			if (list.size() != 1) {
				logger.info("产品id: {} 没有绑定属性值,直接查询sku的时候,查询结果条数不等于1,结果集为: {} !", id, list);
				return null;
			}
			return list.get(0).getSku();
		}else {
			return productSkuAttributeValueRelService.getSkuByProductAttrVal(id, attValIds, attValIds.size());
		}
	}





}
