package com.stosz.tms.model.russia;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.stosz.plat.utils.JaxbDateSerializer;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "StatusReport")
public class StatusReport {

	@XmlAttribute(name = "Date")
	@XmlJavaTypeAdapter(value = JaxbDateSerializer.class)
	private Date Date;// 日期 date √

	@XmlAttribute(name = "Account")
	private String Account;// CDEK提供的对接账号 varchar(255) √

	@XmlAttribute(name = "Secure")
	private String Secure;// 计算出来的秘钥 varchar(255) √

	@XmlAttribute(name = "ShowHistory")
	private Integer ShowHistory;// 是否需要返回动态历史记录(1-是, 0-否) number

	@XmlAttribute(name = "ShowReturnOrder")
	private Integer ShowReturnOrder;// 是否需要返回回单信息 (1-是, 0-否) number

	@XmlAttribute(name = "ShowReturnOrderHistory")
	private Integer ShowReturnOrderHistory;// 是否需要返回回单动态历史记录 (1-是, 0-否) number

	@XmlElement(name = "Order")
	private OrderStatus order;

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

	public Integer getShowHistory() {
		return ShowHistory;
	}

	public void setShowHistory(Integer showHistory) {
		ShowHistory = showHistory;
	}

	public Integer getShowReturnOrder() {
		return ShowReturnOrder;
	}

	public void setShowReturnOrder(Integer showReturnOrder) {
		ShowReturnOrder = showReturnOrder;
	}

	public Integer getShowReturnOrderHistory() {
		return ShowReturnOrderHistory;
	}

	public void setShowReturnOrderHistory(Integer showReturnOrderHistory) {
		ShowReturnOrderHistory = showReturnOrderHistory;
	}

	public OrderStatus getOrder() {
		return order;
	}

	public void setOrder(OrderStatus order) {
		this.order = order;
	}
	
	
	
}
