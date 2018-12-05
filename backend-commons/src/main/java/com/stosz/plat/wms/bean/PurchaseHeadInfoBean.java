package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 采购订单主信息实体bean
 * @author lb
 *
 */
public class PurchaseHeadInfoBean {
	/*
	 * 采购单号
	 */
	private String orderCode;	
	/*
	 * 合同号
	 */
	private String contractCode;
	/*
	 * 供应商编码
	 */
	private String supplierCode;
	/*
	 * 审核人编码
	 */
	private String operuserCode;
	/*
	 * 审核人名称
	 */
	private String operuserName;
	/*
	 * 审核日期(yyyy/MM/dd HH:mm:ss)
	 */
	private String operuserDate;	
	/*
	 * 仓库
	 */
	private String warehouseCode;
	/*
	 * 预计到货日期(yyyy/MM/dd HH:mm:ss)
	 */
	private String planDate;
	/*
	 * 货主
	 */
	private String goodsOwner;	
	/*
	 * 备注
	 */
	private String remark;
	/*
	 * 备用字段1
	 */
	private String gwf1;
	/*
	 * 备用字段2
	 */
	private String gwf2;
	/*
	 * 备用字段3
	 */
	private String gwf3;
	/*
	 * 备用字段4
	 */
	private String gwf4;
	/*
	 * 备用字段5
	 */
	private String gwf5;
	
	private List<PurchaseDetailInfoBean> detailList;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getOperuserCode() {
		return operuserCode;
	}

	public void setOperuserCode(String operuserCode) {
		this.operuserCode = operuserCode;
	}

	public String getOperuserName() {
		return operuserName;
	}

	public void setOperuserName(String operuserName) {
		this.operuserName = operuserName;
	}

	public String getOperuserDate() {
		return operuserDate;
	}

	public void setOperuserDate(String operuserDate) {
		this.operuserDate = operuserDate;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getGoodsOwner() {
		return goodsOwner;
	}

	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGwf1() {
		return gwf1;
	}

	public void setGwf1(String gwf1) {
		this.gwf1 = gwf1;
	}

	public String getGwf2() {
		return gwf2;
	}

	public void setGwf2(String gwf2) {
		this.gwf2 = gwf2;
	}

	public String getGwf3() {
		return gwf3;
	}

	public void setGwf3(String gwf3) {
		this.gwf3 = gwf3;
	}

	public String getGwf4() {
		return gwf4;
	}

	public void setGwf4(String gwf4) {
		this.gwf4 = gwf4;
	}

	public String getGwf5() {
		return gwf5;
	}

	public void setGwf5(String gwf5) {
		this.gwf5 = gwf5;
	}

	public List<PurchaseDetailInfoBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseDetailInfoBean> detailList) {
		this.detailList = detailList;
	}


}
