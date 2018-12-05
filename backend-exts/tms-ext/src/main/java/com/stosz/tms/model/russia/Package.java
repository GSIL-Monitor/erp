package com.stosz.tms.model.russia;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "itemList" })
public class Package {

	@XmlAttribute(name="Number")
	private String Number;// 包裹序号1、2、3等等 varchar(20) √
	
	@XmlAttribute(name="BarCode")
	private String BarCode;// 条码 varchar(20) √
	
	@XmlAttribute(name="Weight")
	private String Weight;// 毛重总重量克g Number √
	
	@XmlAttribute(name="SizeA")
	private String SizeA;// 包装长度厘米sm Number
	
	@XmlAttribute(name="SizeB")
	private String SizeB;// 包装宽度厘米sm Number
	
	@XmlAttribute(name="SizeC")
	private String SizeC;// 包装高度厘米sm Number

	@XmlElement(name = "Item")
	private List<Item> itemList;

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getBarCode() {
		return BarCode;
	}

	public void setBarCode(String barCode) {
		BarCode = barCode;
	}

	public String getWeight() {
		return Weight;
	}

	public void setWeight(String weight) {
		Weight = weight;
	}

	public String getSizeA() {
		return SizeA;
	}

	public void setSizeA(String sizeA) {
		SizeA = sizeA;
	}

	public String getSizeB() {
		return SizeB;
	}

	public void setSizeB(String sizeB) {
		SizeB = sizeB;
	}

	public String getSizeC() {
		return SizeC;
	}

	public void setSizeC(String sizeC) {
		SizeC = sizeC;
	}

}
