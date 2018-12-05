package com.stosz.purchase.ext.model;

import java.io.Serializable;

public class OrderNotifyRequired implements Serializable{

	public static final String  MESSAGE_TYPE="qcOrderPurchase";

	private String spu;// spu

	private String sku;// 产品 SKU

	private Integer deptId;// 小组部门ID
	
	private Integer type;//1 需求订单从订单发起  2 需求订单从采购发起
	

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSpu() {
		return spu;
	}

	public void setSpu(String spu) {
		this.spu = spu;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
