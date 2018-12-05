package com.stosz.tms.model.russia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackOrder {

	@XmlAttribute(name = "ActNumber")
	private String ActNumber;// 提交数据交易序号 varchar(30) √

	@XmlAttribute(name = "Number")
	private String Number;// 客户跟踪号 varchar(30) √

	@XmlAttribute(name = "DispatchNumber")
	private String DispatchNumber;// CDEK运单号 number √

	@XmlAttribute(name = "DeliveryDate")
	private String DeliveryDate;// 派送日期 datetime

	@XmlAttribute(name = "RecipientName")
	private String RecipientName;// 收件人姓名 varchar(50)

	@XmlAttribute(name = "ReturnDispatchNumber")
	private String ReturnDispatchNumber;// 退回时候的回单号 number
	
	@XmlElement(name="Status")
	private TrackStatus trackStatus;
	
	@XmlElement(name="Package")
	private TrackPackage packageItem;
	

	public TrackPackage getPackageItem() {
		return packageItem;
	}

	public void setPackageItem(TrackPackage packageItem) {
		this.packageItem = packageItem;
	}

	public String getActNumber() {
		return ActNumber;
	}

	public void setActNumber(String actNumber) {
		ActNumber = actNumber;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getDispatchNumber() {
		return DispatchNumber;
	}

	public void setDispatchNumber(String dispatchNumber) {
		DispatchNumber = dispatchNumber;
	}


	public String getRecipientName() {
		return RecipientName;
	}

	public void setRecipientName(String recipientName) {
		RecipientName = recipientName;
	}

	public String getReturnDispatchNumber() {
		return ReturnDispatchNumber;
	}

	public void setReturnDispatchNumber(String returnDispatchNumber) {
		ReturnDispatchNumber = returnDispatchNumber;
	}

	public String getDeliveryDate() {
		return DeliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}

	public TrackStatus getTrackStatus() {
		return trackStatus;
	}

	public void setTrackStatus(TrackStatus trackStatus) {
		this.trackStatus = trackStatus;
	}
	
	
	
}
