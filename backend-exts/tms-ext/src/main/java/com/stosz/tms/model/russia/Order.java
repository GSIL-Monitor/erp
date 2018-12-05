package com.stosz.tms.model.russia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "address", "packageItem" })
public class Order {

	@XmlAttribute(name = "Number")
	private String Number; // 商户提供的跟踪号不可以重复 varchar(20)

	@XmlAttribute(name = "SendCityCode")
	private Integer SendCityCode; // 始发城市ID (请看 "City_XXX_YYYYMMDD.xls"文件或者城市接口说明书) Number

	@XmlAttribute(name = "RecCityCode")
	private Integer RecCityCode; // 目的城市ID (请看 "City_XXX_YYYYMMDD.xls"文件城市接口说明书) Number

	@XmlAttribute(name = "SendCityPostCode")
	private String SendCityPostCode; // 始发城市邮编 varchar(6)

	@XmlAttribute(name = "RecCityPostCode")
	private String RecCityPostCode; // 目的城市邮编 varchar(6)

	@XmlAttribute(name = "RecipientName")
	private String RecipientName; // 收件人全名 varchar(128)

	@XmlAttribute(name = "RecipientEmail")
	private String RecipientEmail; // 收件人电子邮箱地址 varchar(255)

	@XmlAttribute(name = "Phone")
	private String Phone; // 收件人电话号码 varchar(50)

	@XmlAttribute(name = "TariffTypeCode")
	private Integer TariffTypeCode; // 运率代码 (请看附件表格 1) Number

	@XmlAttribute(name = "DeliveryRecipientCost")
	private String DeliveryRecipientCost; // 快递员从收件人收的运费 (RecipientCurrency的货币) varchar(255)

	@XmlAttribute(name = "RecipientCurrency")
	private String RecipientCurrency; // 收件人的货币代码: (请看附件表格 7). 默认值RUB俄罗斯的卢布 varchar(10)

	@XmlAttribute(name = "ItemsCurrency")
	private String ItemsCurrency; // 投保价的货币代码(请看附件表格 7). 默认值RUB俄罗斯的卢布 varchar(10)

	@XmlAttribute(name = "Comment")
	private String Comment; // 任何备注 varchar(255)

	@XmlAttribute(name = "SellerName")
	private String SellerName; // 卖家名称 打印货品明细时用的 varchar(255)

	@XmlAttribute(name = "Address")
	private  String addressAttr;
	
	@XmlElement(name = "Address")
	private Address address;

	@XmlElement(name = "Package")
	private Package packageItem;
	

	public String getAddressAttr() {
		return addressAttr;
	}

	public void setAddressAttr(String addressAttr) {
		this.addressAttr = addressAttr;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Package getPackageItem() {
		return packageItem;
	}

	public void setPackageItem(Package packageItem) {
		this.packageItem = packageItem;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public Integer getSendCityCode() {
		return SendCityCode;
	}

	public void setSendCityCode(Integer sendCityCode) {
		SendCityCode = sendCityCode;
	}

	public Integer getRecCityCode() {
		return RecCityCode;
	}

	public void setRecCityCode(Integer recCityCode) {
		RecCityCode = recCityCode;
	}

	public String getSendCityPostCode() {
		return SendCityPostCode;
	}

	public void setSendCityPostCode(String sendCityPostCode) {
		SendCityPostCode = sendCityPostCode;
	}

	public String getRecCityPostCode() {
		return RecCityPostCode;
	}

	public void setRecCityPostCode(String recCityPostCode) {
		RecCityPostCode = recCityPostCode;
	}

	public String getRecipientName() {
		return RecipientName;
	}

	public void setRecipientName(String recipientName) {
		RecipientName = recipientName;
	}

	public String getRecipientEmail() {
		return RecipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		RecipientEmail = recipientEmail;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public Integer getTariffTypeCode() {
		return TariffTypeCode;
	}

	public void setTariffTypeCode(Integer tariffTypeCode) {
		TariffTypeCode = tariffTypeCode;
	}


	public String getDeliveryRecipientCost() {
		return DeliveryRecipientCost;
	}

	public void setDeliveryRecipientCost(String deliveryRecipientCost) {
		DeliveryRecipientCost = deliveryRecipientCost;
	}

	public String getRecipientCurrency() {
		return RecipientCurrency;
	}

	public void setRecipientCurrency(String recipientCurrency) {
		RecipientCurrency = recipientCurrency;
	}

	public String getItemsCurrency() {
		return ItemsCurrency;
	}

	public void setItemsCurrency(String itemsCurrency) {
		ItemsCurrency = itemsCurrency;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public String getSellerName() {
		return SellerName;
	}

	public void setSellerName(String sellerName) {
		SellerName = sellerName;
	}

}
