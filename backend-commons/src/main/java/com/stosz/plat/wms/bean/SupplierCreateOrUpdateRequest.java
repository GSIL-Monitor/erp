package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 供应商资料同步请求
 * @author lb
 *
 */
public class SupplierCreateOrUpdateRequest {
	
	/**
	 * 供应商列表
	 */
	private List<SupplierInfoBean> items;

	public List<SupplierInfoBean> getItems() {
		return items;
	}

	public void setItems(List<SupplierInfoBean> items) {
		this.items = items;
	}

}
