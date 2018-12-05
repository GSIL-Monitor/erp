package com.stosz.tms.model.russia;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrackStatus {

	@XmlAttribute(name = "Date")
	private String Date;// 动态日期 datetime √

	@XmlAttribute(name = "Code")
	private Integer Code;// 动态代码 (物流动态表格) number √

	@XmlAttribute(name = "Description")
	private String Description;// 动态描述 varchar(100) √

	@XmlAttribute(name = "CityCode")
	private Integer CityCode;// 所在城市ID number √

	@XmlAttribute(name = "CityName")
	private String CityName;// 所在城市名称 varchar(100) √

	@XmlElement(name = "State")
	private List<TrackState> trackStates;


	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Integer getCode() {
		return Code;
	}

	public void setCode(Integer code) {
		Code = code;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Integer getCityCode() {
		return CityCode;
	}

	public void setCityCode(Integer cityCode) {
		CityCode = cityCode;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public List<TrackState> getTrackStates() {
		return trackStates;
	}

	public void setTrackStates(List<TrackState> trackStates) {
		this.trackStates = trackStates;
	}

}
