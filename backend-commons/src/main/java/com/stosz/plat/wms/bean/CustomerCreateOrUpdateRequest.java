package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 客户档案同步请求
 * @author lb
 *
 */
public class CustomerCreateOrUpdateRequest {
	/*
	 * 客户信息列表
	 */
	private List<CustomerInfoBean> items;

	public List<CustomerInfoBean> getItems() {
		return items;
	}

	public void setItems(List<CustomerInfoBean> items) {
		this.items = items;
	}	
}
