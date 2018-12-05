package com.stosz.plat.wms.bean;


/**
 * 供应商档案实体bean
 * @author lb
 *
 */
public class SupplierInfoBean {
	/**
	 * 供应商编码	String	50	√
	 */
	private String supplierCode;	
	/**
	 * 供应商名称	String	200
	 */
	private String supplierName;	
	/**
	 * 供应商简称	String	50
	 */
	private String supplierAbbreviation;	
	/**
	 * 联系电话	String	50
	 */
	private String phone;	
	/**
	 * 联系人	String	50
	 */
	private String linkman;	
	/**
	 * 地址	String	50
	 */
	private String address;	
	/**
	 * 邮箱	String	50
	 */
	private String email;	
	/**
	 * 供应商类型1:普通(默认)
	5:重要
	7:其他	String	50	√
	 */
	private String supplierType;	
	/**
	 * 状态Y:有效
	N:无效	String	10	√
	 */
	private String state;	
	/**
	 * 更新时间	String	50
	 */
	private String updateDate;	
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

	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
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
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierAbbreviation() {
		return supplierAbbreviation;
	}
	public void setSupplierAbbreviation(String supplierAbbreviation) {
		this.supplierAbbreviation = supplierAbbreviation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	

}

