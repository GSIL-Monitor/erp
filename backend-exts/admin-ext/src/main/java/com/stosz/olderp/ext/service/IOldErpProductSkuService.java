package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpProductSku;

import java.util.List;

public interface IOldErpProductSkuService {

	String url = "/admin/remote/IOldErpProductSkuService";
	List<OldErpProductSku> findList(Integer limit, Integer offset);
	
	int countList();

    List<OldErpProductSku> getByAttValueId(List<String> optionValues);

	OldErpProductSku getBySku(String sku);

	void insert(OldErpProductSku oldErpProductSku);

	void update(OldErpProductSku oldErpProductSku);

    List<OldErpProductSku> findByProductId(Integer productId);

	/**
	 * 根据产品id删除对应的sku
	 */
	Integer deleteByProductId(Integer productId);

	/**
	 * 获取产品sku对应的属性值组合
	 */
	String getOptionValueByProduct(Integer productId);
}
