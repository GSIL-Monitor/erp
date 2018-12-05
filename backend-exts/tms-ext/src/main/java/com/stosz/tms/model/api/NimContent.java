package com.stosz.tms.model.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Nim content 实体
 * @author xiepengcheng
 * @version [1.0,2017年12月6日]
 */
public class NimContent implements Serializable{

	private String user_code;

	private String order_no;

	private String rec_name;//TODO ?

	private String address;

	private Long zipcode;

	private String lat;//纬度

	private String longitude;//文档中字段为long 不需要经纬度

	private String mobile;

	private String remark;

	private BigDecimal amount_cod;//订单总金额

	private List<NimDetail> listProduct;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getRec_name() {
		return rec_name;
	}

	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getZipcode() {
		return zipcode;
	}

	public void setZipcode(Long zipcode) {
		this.zipcode = zipcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmount_cod() {
		return amount_cod;
	}

	public void setAmount_cod(BigDecimal amount_cod) {
		this.amount_cod = amount_cod;
	}

	public List<NimDetail> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<NimDetail> listProduct) {
		this.listProduct = listProduct;
	}
}
