package com.stosz.plat.wms.bean;

import java.util.List;


/**
 * 调拨计划单主信息实体bean
 * @author lb
 *
 */
public class TranPlanHeadInfoBean {
	/*
	 * 调拨计划单号
	 */
	private String orderCode;	
	/*
	 * 来源单号
	 */
	private String sourceOrderNo;
	/*
	 * 来源类型不能为空，默认为(01)
	 * 01：普通调拨(出、入仓库必需传)
	 * 02:调拨入库
	 * 03:调拨出库
	 */
	private String sourceType ;
	/*
	 * 是否是唯品的单子
	 */
	private String isVip;
	/*
	 * 第三方入库单号(如唯品的出仓单号)
	 */
	private String outOrderNo;
	/*
	 * 调出仓库编码
	 */
	private String fromWhcode;
	/*
	 * 调入仓库编码
	 */
	private String targetWhcode;
	/*
	 * 创建人编码
	 */
	private String createUserCode;
	/*
	 * 创建人名称
	 */
	private String createUserName;
	/*
	 * 创建时间
	 */
	private String createDate;
	/*
	 * 物流公司编码
	 */
	private String logisticsCompanyCode;
	/*
	 * 快递单号
	 */
	private String expressNo;
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
	
	private List<TranPlanDetailInfoBean> detailList;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public String getFromWhcode() {
		return fromWhcode;
	}

	public void setFromWhcode(String fromWhcode) {
		this.fromWhcode = fromWhcode;
	}

	public String getTargetWhcode() {
		return targetWhcode;
	}

	public void setTargetWhcode(String targetWhcode) {
		this.targetWhcode = targetWhcode;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLogisticsCompanyCode() {
		return logisticsCompanyCode;
	}

	public void setLogisticsCompanyCode(String logisticsCompanyCode) {
		this.logisticsCompanyCode = logisticsCompanyCode;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
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

	public List<TranPlanDetailInfoBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<TranPlanDetailInfoBean> detailList) {
		this.detailList = detailList;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

}
