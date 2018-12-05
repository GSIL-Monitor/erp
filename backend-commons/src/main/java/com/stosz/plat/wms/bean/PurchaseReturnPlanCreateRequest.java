package com.stosz.plat.wms.bean;


/**
 * 采购退货计划单同步请求
 * @author lb
 *
 */
public class PurchaseReturnPlanCreateRequest {
	
	/**
	 * 采购退货计划单
	 */
	private PurchaseReturnPlanHeadInfoBean head;

	public PurchaseReturnPlanHeadInfoBean getHead() {
		return head;
	}

	public void setHead(PurchaseReturnPlanHeadInfoBean head) {
		this.head = head;
	}

}
