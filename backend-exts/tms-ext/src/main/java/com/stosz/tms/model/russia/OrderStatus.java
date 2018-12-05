package com.stosz.tms.model.russia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderStatus {

	@XmlAttribute(name = "DispatchNumber")
	private String DispatchNumber;// *** CDEK运单号（10位数字的） number √

	@XmlAttribute(name = "Number")
	private String Number;// *** 客户跟踪号（被客户定的运单跟踪号） varchar(30) √

	@XmlAttribute(name = "Date")
	private java.util.Date Date;// *** 下单日期 date √

	public String getDispatchNumber() {
		return DispatchNumber;
	}

	public void setDispatchNumber(String dispatchNumber) {
		DispatchNumber = dispatchNumber;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public java.util.Date getDate() {
		return Date;
	}

	public void setDate(java.util.Date date) {
		Date = date;
	}

}
