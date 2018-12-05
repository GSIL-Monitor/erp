package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单ip信息实体类
 */
public class ErpOrderInfo  extends AbstractParamEntity implements ITable, Serializable {

	/***/
	 @DBColumn
	private	Integer	idOrderInfo	=	new Integer(0);
	/***/
	 @DBColumn
	private	Integer	idOrder	=	new Integer(0);
	/***/
	 @DBColumn
	private	String	ip	=	"";
	/***/
	 @DBColumn
	private	String	userAgent	=	"";
	/**IP中文地址*/
	 @DBColumn
	private	String	ipAddress	=	"";
	/**黑名单等级*/
	 @DBColumn
	private	String	blacklistLevel	=	"";
	/**配到黑名单字段*/
	 @DBColumn
	private	String	blacklistField	=	"";
	//验证码类型
	private	 Integer verifyStatus;
	/***/
	 @DBColumn
	private	java.time.LocalDateTime	createdAt	=	java.time.LocalDateTime.now();

	public ErpOrderInfo(){}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public void setIdOrderInfo(Integer	idOrderInfo){
		this.idOrderInfo = idOrderInfo;
	}

	public Integer getIdOrderInfo(){
		return idOrderInfo;
	}

	public void setIdOrder(Integer	idOrder){
		this.idOrder = idOrder;
	}

	public Integer getIdOrder(){
		return idOrder;
	}

	public void setIp(String	ip){
		this.ip = ip;
	}

	public String getIp(){
		return ip;
	}

	public void setUserAgent(String	userAgent){
		this.userAgent = userAgent;
	}

	public String getUserAgent(){
		return userAgent;
	}

	public void setIpAddress(String	ipAddress){
		this.ipAddress = ipAddress;
	}

	public String getIpAddress(){
		return ipAddress;
	}

	public void setBlacklistLevel(String	blacklistLevel){
		this.blacklistLevel = blacklistLevel;
	}

	public String getBlacklistLevel(){
		return blacklistLevel;
	}

	public void setBlacklistField(String	blacklistField){
		this.blacklistField = blacklistField;
	}

	public String getBlacklistField(){
		return blacklistField;
	}

	public void setCreatedAt(java.time.LocalDateTime	createdAt){
		this.createdAt = createdAt;
	}

	public java.time.LocalDateTime getCreatedAt(){
		return createdAt;
	}

		@Override
		public String getTable() {
		return "erp_order_info";
	}

	@Override
	public Integer getId() {
		return idOrderInfo;
	}

	@Override
	public void setId(Integer id) {
		this.idOrderInfo = id;
	}
}