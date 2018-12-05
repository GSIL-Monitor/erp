package com.stosz.plat.wms.bean;


/**
 * 销售退货计划单同步请求
 * @author lb
 *
 */
public class SaleReturnPlanCreateRequest {
	
	/**
	 * 销售退货计划单
	 */
	private SaleReturnPlanHeadInfoBean head;

	public SaleReturnPlanHeadInfoBean getHead() {
		return head;
	}

	public void setHead(SaleReturnPlanHeadInfoBean head) {
		this.head = head;
	}

}
