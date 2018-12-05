package com.stosz.tms.model.api;

import java.util.List;

/**
 * times 实体
 * @author feiheping
 * @version [1.0,2017年12月4日]
 */
public class TimesContent {

	private String consigneeCompanyName;// 收货人公司 (string, optional) -
	private String consigneeContactName;// 收货人联络人 (string, optional) -
	private String consigneePhone;// 收货人电话 (string, required) -
	private String consigneeAddress;// 收货人地址 (string, optional) -
	private String consigneeSubdistrict;// 收货人分区份 (string, required) -
	private String consigneeDistrict;// 收货人区份 (string, required) -
	private String consigneeProvince;// 收货人省份 (string, required) -
	private String consigneeCountry;// 收货人国家 (string, required) - Full name of Consignee Country in English
									// (Thailand,
									// Taiwan, etc)
	private String consigneeCompanyNameLocale;//// 收货人公司(目的地官方语言) (string, optional) - In Destination official language
	private String consigneeContactNameLocale;//// 收货人联络人 (string, required) - In Destination official language
	private String consigneeIdCardNumber;// (string, optional/required) - Consignee ID card number. 如目的地为中国台湾,
											// 总货价大或等於3000当地货币必须

	private String consigneeAddressLocale;// 收货人地址 (目的地官方语言) (string, required) - In Destination official language
	private String consigneePostalCode;// 收货人邮编 (string, required) -
	private String shipperCompanyName;// 发货人公司 (string, required) -
	private String shipperContactName;// 发货人联络人 (string, required) -
	private String shipperPhone;// 发货人电话 (string, required) -
	private String shipperAddress;// 发货人地址(英语) (string, required) -
	private String shipperSubdistrict;// 发货人分区份(英语) (string, required) -
	private String shipperDistrict;// 发货人区份(英语) (string, required) -
	private String shipperProvince;// 发货人省份(英语) (string, required) -
	private String shipperCountry;// 发货人国家(英语) (string, required) -
	private String shipperPostalCode;// 发货人邮政编号 (string, required) -
	private String paymentMethod;// PREPAID 预支付 COD 到付
	private String parcelValue;// 包裹价值
	private String productType;// 寄货渠道Express = 专线; Postal = 邮政
	private String shipmentType;// 包裹类型: General Shipment = 普货; Sensitive Shipment = 带电池货(敏感货); Mobile & Tablet
								// = 手机及平板计算机
	private String salePlatformName;// 销售平台名称
	private String referenceNumber;// 客户参考编号, 客戶对包裹的唯一识别号
	
	private List<TimesDetail> items;//产品详情
	
	

	public List<TimesDetail> getItems() {
		return items;
	}

	public void setItems(List<TimesDetail> items) {
		this.items = items;
	}

	public String getConsigneeCompanyName() {
		return consigneeCompanyName;
	}

	public void setConsigneeCompanyName(String consigneeCompanyName) {
		this.consigneeCompanyName = consigneeCompanyName;
	}

	public String getConsigneeContactName() {
		return consigneeContactName;
	}

	public void setConsigneeContactName(String consigneeContactName) {
		this.consigneeContactName = consigneeContactName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeSubdistrict() {
		return consigneeSubdistrict;
	}

	public void setConsigneeSubdistrict(String consigneeSubdistrict) {
		this.consigneeSubdistrict = consigneeSubdistrict;
	}

	public String getConsigneeDistrict() {
		return consigneeDistrict;
	}

	public void setConsigneeDistrict(String consigneeDistrict) {
		this.consigneeDistrict = consigneeDistrict;
	}

	public String getConsigneeProvince() {
		return consigneeProvince;
	}

	public void setConsigneeProvince(String consigneeProvince) {
		this.consigneeProvince = consigneeProvince;
	}

	public String getConsigneeCountry() {
		return consigneeCountry;
	}

	public void setConsigneeCountry(String consigneeCountry) {
		this.consigneeCountry = consigneeCountry;
	}

	public String getConsigneeCompanyNameLocale() {
		return consigneeCompanyNameLocale;
	}

	public void setConsigneeCompanyNameLocale(String consigneeCompanyNameLocale) {
		this.consigneeCompanyNameLocale = consigneeCompanyNameLocale;
	}

	public String getConsigneeContactNameLocale() {
		return consigneeContactNameLocale;
	}

	public void setConsigneeContactNameLocale(String consigneeContactNameLocale) {
		this.consigneeContactNameLocale = consigneeContactNameLocale;
	}

	public String getConsigneeIdCardNumber() {
		return consigneeIdCardNumber;
	}

	public void setConsigneeIdCardNumber(String consigneeIdCardNumber) {
		this.consigneeIdCardNumber = consigneeIdCardNumber;
	}

	public String getConsigneeAddressLocale() {
		return consigneeAddressLocale;
	}

	public void setConsigneeAddressLocale(String consigneeAddressLocale) {
		this.consigneeAddressLocale = consigneeAddressLocale;
	}

	public String getConsigneePostalCode() {
		return consigneePostalCode;
	}

	public void setConsigneePostalCode(String consigneePostalCode) {
		this.consigneePostalCode = consigneePostalCode;
	}

	public String getShipperCompanyName() {
		return shipperCompanyName;
	}

	public void setShipperCompanyName(String shipperCompanyName) {
		this.shipperCompanyName = shipperCompanyName;
	}

	public String getShipperContactName() {
		return shipperContactName;
	}

	public void setShipperContactName(String shipperContactName) {
		this.shipperContactName = shipperContactName;
	}

	public String getShipperPhone() {
		return shipperPhone;
	}

	public void setShipperPhone(String shipperPhone) {
		this.shipperPhone = shipperPhone;
	}

	public String getShipperAddress() {
		return shipperAddress;
	}

	public void setShipperAddress(String shipperAddress) {
		this.shipperAddress = shipperAddress;
	}

	public String getShipperSubdistrict() {
		return shipperSubdistrict;
	}

	public void setShipperSubdistrict(String shipperSubdistrict) {
		this.shipperSubdistrict = shipperSubdistrict;
	}

	public String getShipperDistrict() {
		return shipperDistrict;
	}

	public void setShipperDistrict(String shipperDistrict) {
		this.shipperDistrict = shipperDistrict;
	}

	public String getShipperProvince() {
		return shipperProvince;
	}

	public void setShipperProvince(String shipperProvince) {
		this.shipperProvince = shipperProvince;
	}

	public String getShipperCountry() {
		return shipperCountry;
	}

	public void setShipperCountry(String shipperCountry) {
		this.shipperCountry = shipperCountry;
	}

	public String getShipperPostalCode() {
		return shipperPostalCode;
	}

	public void setShipperPostalCode(String shipperPostalCode) {
		this.shipperPostalCode = shipperPostalCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getParcelValue() {
		return parcelValue;
	}

	public void setParcelValue(String parcelValue) {
		this.parcelValue = parcelValue;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	public String getSalePlatformName() {
		return salePlatformName;
	}

	public void setSalePlatformName(String salePlatformName) {
		this.salePlatformName = salePlatformName;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

}
