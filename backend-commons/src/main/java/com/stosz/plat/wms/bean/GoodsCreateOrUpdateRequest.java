package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 商品档案同步请求
 * @author lb
 *
 */
public class GoodsCreateOrUpdateRequest {


	/*
	 * 商品列表集合
	 */
	private List<SkuInfoBean> items;

	public List<SkuInfoBean> getItems() {
		return items;
	}

	public void setItems(List<SkuInfoBean> items) {
		this.items = items;
	}
	
}
