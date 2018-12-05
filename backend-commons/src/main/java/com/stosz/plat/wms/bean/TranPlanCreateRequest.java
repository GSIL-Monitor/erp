package com.stosz.plat.wms.bean;


/**
 * 调拨计划单同步请求
 * @author lb
 *
 */
public class TranPlanCreateRequest {
	
	/**
	 * 调拨计划单
	 */
	private TranPlanHeadInfoBean head;

	public TranPlanHeadInfoBean getHead() {
		return head;
	}

	public void setHead(TranPlanHeadInfoBean head) {
		this.head = head;
	}

}
