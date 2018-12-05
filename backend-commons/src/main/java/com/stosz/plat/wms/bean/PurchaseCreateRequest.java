package com.stosz.plat.wms.bean;


/**
 * 采购订单同步请求
 * @author lb
 *
 */
public class PurchaseCreateRequest {
	
	/**
	 * 采购订单
	 */
	private PurchaseHeadInfoBean head;

	public PurchaseHeadInfoBean getHead() {
		return head;
	}

	public void setHead(PurchaseHeadInfoBean head) {
		this.head = head;
	}

}
