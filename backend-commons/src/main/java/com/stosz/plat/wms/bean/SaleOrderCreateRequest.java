package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 销售订单同步请求
 * @author lb
 *
 */
public class SaleOrderCreateRequest {
	
	/**
	 * 销售订单列表
	 */
	private List<SaleOrderHeadInfoBean> headList;

	public List<SaleOrderHeadInfoBean> getHeadList() {
		return headList;
	}

	public void setHeadList(List<SaleOrderHeadInfoBean> headList) {
		this.headList = headList;
	}

}
