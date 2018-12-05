package com.stosz.tms.model.russia;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

	@XmlAttribute(name = "WareKey")
	private String WareKey;// 网点或销售平台产品的SKU或产品ID varchar(20) √

	@XmlAttribute(name = "Cost")
	private BigDecimal Cost;// 货件投保价值 (单价ItemsCurrency的货币) Real √

	@XmlAttribute(name = "Payment")
	private BigDecimal Payment;// 到付金额 (RecipientCurrency的货币)到付的话>0预付的话=0 Real √

	@XmlAttribute(name = "Weight")
	private Integer Weight;// 每一件的净重克g Number √

	@XmlAttribute(name = "Amount")
	private Integer Amount;// 货件数量 Number √

	@XmlAttribute(name = "Comment")
	private String Comment;// 货品描述用收件人的语言 varchar(255)

	public String getWareKey() {
		return WareKey;
	}

	public void setWareKey(String wareKey) {
		WareKey = wareKey;
	}

	public BigDecimal getCost() {
		return Cost;
	}

	public void setCost(BigDecimal cost) {
		Cost = cost;
	}

	public BigDecimal getPayment() {
		return Payment;
	}

	public void setPayment(BigDecimal payment) {
		Payment = payment;
	}

	public Integer getWeight() {
		return Weight;
	}

	public void setWeight(Integer weight) {
		Weight = weight;
	}

	public Integer getAmount() {
		return Amount;
	}

	public void setAmount(Integer amount) {
		Amount = amount;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

}
