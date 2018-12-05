package com.stosz.tms.model.russia;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.stosz.plat.utils.JaxbDateSerializer;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "order" })
@XmlRootElement(name = "DeliveryRequest")
public class DeliveryRequest {

	@XmlAttribute(name = "Number")
	private String Number;// 商户提供的传单号可以为1不重要的 varchar(20)

	@XmlAttribute(name = "Date")

	@XmlJavaTypeAdapter(value = JaxbDateSerializer.class)
	private Date Date; // 订单日期平时提交今天 Date

	@XmlAttribute(name = "Account")
	private String Account;// 商户对接的账号名 varchar(255)

	@XmlAttribute(name = "Secure")
	private String Secure; // 计算出来的密钥 varchar(255)

	@XmlAttribute(name = "OrderCount")
	private Integer OrderCount;// 运单数量 Number

	@XmlElement(name = "Order")
	private Order order;// 订单信息

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getSecure() {
		return Secure;
	}

	public void setSecure(String secure) {
		Secure = secure;
	}

	public Integer getOrderCount() {
		return OrderCount;
	}

	public void setOrderCount(Integer orderCount) {
		OrderCount = orderCount;
	}

}
