package com.stosz.plat.wms.bean;


/**
 * 客户档案实体bean
 * @author lb
 *
 */
public class CustomerInfoBean {
	/**
	 * 客户编码	String	50
	 */
	private String customerCode;
	/**
	 * 客户名称	String	50
	 */
	private String customerName;
	/**
	 * 客户简称	String	50
	 */
	private String customerAbbreviation;
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
	 * 地区名称	String	50
	 */
	private String area;
	/**
	 * 客户类型	String	50
	 */
	private String type;
	/**
	 * 状态1:有效  0:无效	String	50
	 */
	private String state;
	/**
	 * 更新时间	String	50
	 */
	private String updateDate;	
	/**
	 * 货主编码	String	50
	 */
	private String goodsOwner;
	/**
	 * 备注		String	50
	 */
	private String remark;
	/**
	 * 备用字段1	String	50
	 */
	private String gwf1;
	/**
	 * 备用字段2	String	50
	 */
	private String gwf2;
	/**
	 * 备用字段3	String	50
	 */
	private String gwf3;
	/**
	 * 备用字段4	String	50
	 */
	private String gwf4;
	/**
	 * 备用字段5	String	50
	 */
	private String gwf5;
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAbbreviation() {
		return customerAbbreviation;
	}
	public void setCustomerAbbreviation(String customerAbbreviation) {
		this.customerAbbreviation = customerAbbreviation;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getGoodsOwner() {
		return goodsOwner;
	}
	public void setGoodsOwner(String goodsOwner) {
		this.goodsOwner = goodsOwner;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
