package com.stosz.tms.model.russia;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackPackage {

	@XmlAttribute(name = "Number")
	private String Number;// 包裹序号 varchar(20) √

	@XmlElement(name = "Item")
	private List<TrackItem> trackItem;

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public List<TrackItem> getTrackItem() {
		return trackItem;
	}

	public void setTrackItem(List<TrackItem> trackItem) {
		this.trackItem = trackItem;
	}

}
