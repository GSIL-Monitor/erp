package com.stosz.tms.model.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 裕丰 Content 实体
 * @author xiepengcheng
 * @version [1.0,2017年12月1日]
 */
public class YufengContent implements Serializable {

	private String order_code;// 运单号码
	private String orderno;// 订单号码
	private String ordertype;// 订货订单，换货订单,代取订单
	private String orderstr;// 子单号码
	private String tms_service_code;// 快递公司编号（上线时提供，最好是可配置）
	private BigDecimal total_amount;// 代收货款
	private Long cccharge;// 到付款
	private String receiver_name;// 收货人名称
	private String receiver_zip;// 邮编
	private String receiver_province;// 省份
	private String receiver_city;// 城市
	private String receiver_district;// 地区
	private String receiver_address;//地址
	private String receiver_mobile;// 手机
	private String receiver_phone;// 座机
	private String package_weight;// 包裹重量,单位是kg
	private String package_volume;// 包裹体积，单位是立方米
	private String sd_name;// 寄件人名称
	private String sd_mobile;// 寄件人手机
	private String sd_phone;// 寄件人座机
	private String descname;// 主单商品名称
	private int pcs;// 件数
	private String remark;// 备注
	private List desclist;// 子单及子单商品名称集合
	private String desname;// 子单商品名称
	private String childno;// 子单号码

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getOrderstr() {
		return orderstr;
	}

	public void setOrderstr(String orderstr) {
		this.orderstr = orderstr;
	}

	public String getTms_service_code() {
		return tms_service_code;
	}

	public void setTms_service_code(String tms_service_code) {
		this.tms_service_code = tms_service_code;
	}

	public BigDecimal getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}

	public Long getCccharge() {
		return cccharge;
	}

	public void setCccharge(Long cccharge) {
		this.cccharge = cccharge;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getReceiver_zip() {
		return receiver_zip;
	}

	public void setReceiver_zip(String receiver_zip) {
		this.receiver_zip = receiver_zip;
	}

	public String getReceiver_province() {
		return receiver_province;
	}

	public void setReceiver_province(String receiver_province) {
		this.receiver_province = receiver_province;
	}

	public String getReceiver_city() {
		return receiver_city;
	}

	public void setReceiver_city(String receiver_city) {
		this.receiver_city = receiver_city;
	}

	public String getReceiver_district() {
		return receiver_district;
	}

	public void setReceiver_district(String receiver_district) {
		this.receiver_district = receiver_district;
	}

	public String getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	public String getReceiver_phone() {
		return receiver_phone;
	}

	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}

	public String getPackage_weight() {
		return package_weight;
	}

	public void setPackage_weight(String package_weight) {
		this.package_weight = package_weight;
	}

	public String getPackage_volume() {
		return package_volume;
	}

	public void setPackage_volume(String package_volume) {
		this.package_volume = package_volume;
	}

	public String getSd_name() {
		return sd_name;
	}

	public void setSd_name(String sd_name) {
		this.sd_name = sd_name;
	}

	public String getSd_mobile() {
		return sd_mobile;
	}

	public void setSd_mobile(String sd_mobile) {
		this.sd_mobile = sd_mobile;
	}

	public String getSd_phone() {
		return sd_phone;
	}

	public void setSd_phone(String sd_phone) {
		this.sd_phone = sd_phone;
	}

	public String getDescname() {
		return descname;
	}

	public void setDescname(String descname) {
		this.descname = descname;
	}

	public int getPcs() {
		return pcs;
	}

	public void setPcs(int pcs) {
		this.pcs = pcs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List getDesclist() {
		return desclist;
	}

	public void setDesclist(List desclist) {
		this.desclist = desclist;
	}

	public String getDesname() {
		return desname;
	}

	public void setDesname(String desname) {
		this.desname = desname;
	}

	public String getChildno() {
		return childno;
	}

	public void setChildno(String childno) {
		this.childno = childno;
	}
}
