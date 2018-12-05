package com.stosz.tms.model.russia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Address {

	@XmlAttribute(name="Street")
	private String Street;// 街名 varchar(50) √
	
	@XmlAttribute(name="House")
	private String House;// 楼号 varchar(30) √
	
	@XmlAttribute(name="Flat")
	private String Flat;// 门号 varchar(10)
	
	@XmlAttribute(name="PvzCode")
	private String PvzCode;// 网点代号 (请看"网点报文"). 只派送模式到库时需要填并且没有”在目的城市送货”服务 (AddService = "17")的话 varchar(10)
							// √
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getHouse() {
		return House;
	}
	public void setHouse(String house) {
		House = house;
	}
	public String getFlat() {
		return Flat;
	}
	public void setFlat(String flat) {
		Flat = flat;
	}
	public String getPvzCode() {
		return PvzCode;
	}
	public void setPvzCode(String pvzCode) {
		PvzCode = pvzCode;
	}
	
	
	
	
}
