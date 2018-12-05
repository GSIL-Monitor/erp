package com.stosz.tms.model.api;

import java.io.Serializable;
import java.util.List;

/**
 * 亿泰 content 实体
 * @author xiepengcheng
 * @version [1.0,2017年12月5日]
 */
public class YiTaiContent implements Serializable{

	private String buyerid;//TODO ？

	private String consignee_address;// 收件地址街道，必填

	private String consignee_city;// 城市代码,由亿泰提供文档

	private String consignee_mobile;// 收件人手机号

	private String order_returnsign;//TODO ？

	private String consignee_name;// 收件人,必填

	private Integer trade_type;//TODO ?

	private Double consignee_postcode;//邮编

	private Double consignee_state;//州/省

	private Double consignee_telephone;//收件电话，必填

	private Double country;//收件国家二字代码，必填

	private String customer_id;//客户ID，必填

	private String customer_userid;//登录人ID，必填

	private List<YiTaiDetail> orderInvoiceParam;// DETAIL

	private String order_customerinvoicecode;//原单号，必填

	private String product_id;//运输方式ID，必填

	private Double weight;//总重，选填

	private String product_imagepath;//图片地址，多图片地址用分号隔开

	private String shipper_name;//发件人姓名,选填

	private String shipper_companyname;//发件人公司名,选填

	private String shipper_address1;//发件人地址1，选填

	private String shipper_address2;//发件人地址2，选填

	private String shipper_address3;//发件人地址3，选填

	private String shipper_city;//发件人城市，选填

	private String shipper_state;//发件人州，选填

	private String shipper_postcode;//发件人邮编,选填

	private String shipper_country;//发件人国家，选填

	private String shipper_telephone;//发件人电话，选填

	private String order_transactionurl;// 产品销售地址

	private String consignee_email;//info@etsct.com

	private String consignee_companyname;//收件公司名,如有最好填写

	private String order_codamount;//代收金额

	private String order_codcurrency;//币种，标准三字代码

	public String getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}

	public String getConsignee_address() {
		return consignee_address;
	}

	public void setConsignee_address(String consignee_address) {
		this.consignee_address = consignee_address;
	}

	public String getConsignee_city() {
		return consignee_city;
	}

	public void setConsignee_city(String consignee_city) {
		this.consignee_city = consignee_city;
	}

	public String getConsignee_mobile() {
		return consignee_mobile;
	}

	public void setConsignee_mobile(String consignee_mobile) {
		this.consignee_mobile = consignee_mobile;
	}

	public String getOrder_returnsign() {
		return order_returnsign;
	}

	public void setOrder_returnsign(String order_returnsign) {
		this.order_returnsign = order_returnsign;
	}

	public String getConsignee_name() {
		return consignee_name;
	}

	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public Integer getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(Integer trade_type) {
		this.trade_type = trade_type;
	}

	public Double getConsignee_postcode() {
		return consignee_postcode;
	}

	public void setConsignee_postcode(Double consignee_postcode) {
		this.consignee_postcode = consignee_postcode;
	}

	public Double getConsignee_state() {
		return consignee_state;
	}

	public void setConsignee_state(Double consignee_state) {
		this.consignee_state = consignee_state;
	}

	public Double getConsignee_telephone() {
		return consignee_telephone;
	}

	public void setConsignee_telephone(Double consignee_telephone) {
		this.consignee_telephone = consignee_telephone;
	}

	public Double getCountry() {
		return country;
	}

	public void setCountry(Double country) {
		this.country = country;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_userid() {
		return customer_userid;
	}

	public void setCustomer_userid(String customer_userid) {
		this.customer_userid = customer_userid;
	}

	public List<YiTaiDetail> getOrderInvoiceParam() {
		return orderInvoiceParam;
	}

	public void setOrderInvoiceParam(List<YiTaiDetail> orderInvoiceParam) {
		this.orderInvoiceParam = orderInvoiceParam;
	}

	public String getOrder_customerinvoicecode() {
		return order_customerinvoicecode;
	}

	public void setOrder_customerinvoicecode(String order_customerinvoicecode) {
		this.order_customerinvoicecode = order_customerinvoicecode;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getProduct_imagepath() {
		return product_imagepath;
	}

	public void setProduct_imagepath(String product_imagepath) {
		this.product_imagepath = product_imagepath;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	public String getShipper_companyname() {
		return shipper_companyname;
	}

	public void setShipper_companyname(String shipper_companyname) {
		this.shipper_companyname = shipper_companyname;
	}

	public String getShipper_address1() {
		return shipper_address1;
	}

	public void setShipper_address1(String shipper_address1) {
		this.shipper_address1 = shipper_address1;
	}

	public String getShipper_address2() {
		return shipper_address2;
	}

	public void setShipper_address2(String shipper_address2) {
		this.shipper_address2 = shipper_address2;
	}

	public String getShipper_address3() {
		return shipper_address3;
	}

	public void setShipper_address3(String shipper_address3) {
		this.shipper_address3 = shipper_address3;
	}

	public String getShipper_city() {
		return shipper_city;
	}

	public void setShipper_city(String shipper_city) {
		this.shipper_city = shipper_city;
	}

	public String getShipper_state() {
		return shipper_state;
	}

	public void setShipper_state(String shipper_state) {
		this.shipper_state = shipper_state;
	}

	public String getShipper_postcode() {
		return shipper_postcode;
	}

	public void setShipper_postcode(String shipper_postcode) {
		this.shipper_postcode = shipper_postcode;
	}

	public String getShipper_country() {
		return shipper_country;
	}

	public void setShipper_country(String shipper_country) {
		this.shipper_country = shipper_country;
	}

	public String getShipper_telephone() {
		return shipper_telephone;
	}

	public void setShipper_telephone(String shipper_telephone) {
		this.shipper_telephone = shipper_telephone;
	}

	public String getOrder_transactionurl() {
		return order_transactionurl;
	}

	public void setOrder_transactionurl(String order_transactionurl) {
		this.order_transactionurl = order_transactionurl;
	}

	public String getConsignee_email() {
		return consignee_email;
	}

	public void setConsignee_email(String consignee_email) {
		this.consignee_email = consignee_email;
	}

	public String getConsignee_companyname() {
		return consignee_companyname;
	}

	public void setConsignee_companyname(String consignee_companyname) {
		this.consignee_companyname = consignee_companyname;
	}

	public String getOrder_codamount() {
		return order_codamount;
	}

	public void setOrder_codamount(String order_codamount) {
		this.order_codamount = order_codamount;
	}

	public String getOrder_codcurrency() {
		return order_codcurrency;
	}

	public void setOrder_codcurrency(String order_codcurrency) {
		this.order_codcurrency = order_codcurrency;
	}
}
