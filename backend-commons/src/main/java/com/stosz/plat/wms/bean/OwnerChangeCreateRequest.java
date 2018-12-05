package com.stosz.plat.wms.bean;


/**
 * 货主变更单同步请求
 * @author lb
 *
 */
public class OwnerChangeCreateRequest {
	
	/**
	 * 货主变更单
	 */
	private OwnerChangeHeadInfoBean head;

	public OwnerChangeHeadInfoBean getHead() {
		return head;
	}

	public void setHead(OwnerChangeHeadInfoBean head) {
		this.head = head;
	}

}
