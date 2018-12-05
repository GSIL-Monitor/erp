package com.stosz.tms.model.russia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackItem {

	@XmlAttribute(name = "WareKey")
	private String WareKey;// 条码 varchar(20) √

	@XmlAttribute(name = "DelivAmount")
	private Integer DelivAmount; // 货件数量 number √

	public String getWareKey() {
		return WareKey;
	}

	public void setWareKey(String wareKey) {
		WareKey = wareKey;
	}

	public Integer getDelivAmount() {
		return DelivAmount;
	}

	public void setDelivAmount(Integer delivAmount) {
		DelivAmount = delivAmount;
	}

}
