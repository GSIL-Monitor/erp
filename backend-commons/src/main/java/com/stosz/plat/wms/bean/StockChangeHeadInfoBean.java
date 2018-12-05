/**
 * 
 */
package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 库存异动查询主信息实体bean
 * @author Administrator
 *
 */
public class StockChangeHeadInfoBean {
	/*
	 *序号
	 */
	private String id;
	/*
	 *仓库编码
	 */
	private String warehouseCode;
	/*
	 *订单号
	 */
	private String orderCode;
	/*
	 * 模块类型
	 */
	private String moduleCode;
	/*
	 * 审核日期(yyyy/MM/dd HH:mm:ss)
	 */
	private String operuserDate;
	/*
	 * 审核人编码
	 */
	private String operuserCode;
	/*
	 * 审核人名称
	 */
	private String operuserName;
	/**
	 * 来源单号	String	50
	 */
	private String socoCode;
	/**
	 * 货主	String	50
	 */
	private String goodsOwner;
	/**
	 * 备注	String	500	
	 */
	private String remark;
	/**
	 * 备用字段1
	 */
	private String gwf1;
	/**
	 * 备用字段2
	 */
	private String gwf2;
	/**备用字段3
	 * 
	 */
	private String gwf3;
	/**
	 * 备用字段4
	 */
	private String gwf4;
	/**备用字段5
	 * 
	 */
	private String gwf5;
	
	private List<StockChangeDetailInfoBean> detailList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getOperuserDate() {
		return operuserDate;
	}

	public void setOperuserDate(String operuserDate) {
		this.operuserDate = operuserDate;
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

	public String getSocoCode() {
		return socoCode;
	}

	public void setSocoCode(String socoCode) {
		this.socoCode = socoCode;
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

	public List<StockChangeDetailInfoBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<StockChangeDetailInfoBean> detailList) {
		this.detailList = detailList;
	}
	
}
