package com.stosz.tms.model.russia;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "StatusReport")
public class TrackStatusReport {

	@XmlAttribute(name = "DateFirst")
	private Date DateFirst;// 开始时间 datetime √

	@XmlAttribute(name = "DateLast")
	private Date DateLast; // 结束时间 datetime √

	@XmlElement(name = "Order")
	private TrackOrder order;

	public Date getDateFirst() {
		return DateFirst;
	}

	public void setDateFirst(Date dateFirst) {
		DateFirst = dateFirst;
	}

	public Date getDateLast() {
		return DateLast;
	}

	public void setDateLast(Date dateLast) {
		DateLast = dateLast;
	}

	public TrackOrder getOrder() {
		return order;
	}

	public void setOrder(TrackOrder order) {
		this.order = order;
	}

	
	
}
