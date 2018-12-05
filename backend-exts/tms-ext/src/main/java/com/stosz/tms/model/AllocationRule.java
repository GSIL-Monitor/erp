package com.stosz.tms.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AllocationRule {

	@XmlAttribute(name = "express")
	private String express;

	@XmlAttribute(name = "code")
	private String code;

	@XmlAttribute(name = "sort")
	private Integer sort;

	@XmlAttribute(name = "wmsZoneId")
	private Integer wmsZoneId;

	@XmlAttribute(name = "wmsZoneName")
	private String wmsZoneName;

	@XmlAttribute(name = "zoneId")
	private Integer zoneId;

	@XmlAttribute(name = "zoneName")
	private String zoneName;

	@XmlAttribute(name = "chain")
	private boolean chain;// true 规则匹配成功后，但是没有找到对应的物流方式，则交给后面的handler处理, false 规则匹配成功后，没有找到对应的物流方式，就直接指派失败

	public String getExpress() {
		return express;
	}

	public boolean isChain() {
		return chain;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getWmsZoneId() {
		return wmsZoneId;
	}

	public void setWmsZoneId(Integer wmsZoneId) {
		this.wmsZoneId = wmsZoneId;
	}

	public String getWmsZoneName() {
		return wmsZoneName;
	}

	public void setWmsZoneName(String wmsZoneName) {
		this.wmsZoneName = wmsZoneName;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

}
