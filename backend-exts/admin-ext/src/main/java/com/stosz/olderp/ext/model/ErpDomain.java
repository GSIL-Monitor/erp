package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 实体类
 */
public class ErpDomain  extends AbstractParamEntity implements ITable, Serializable {

	/**域名id*/
	 @DBColumn
	private	Integer	idDomain	= new	Integer(0);
	/**部门id*/
	 @DBColumn
	private	Integer	idDepartment	=new	Integer(0);
	/**域名名称*/
	 @DBColumn
	private	String	name	=	"";
	/**投放地址*/
	 @DBColumn
	private	String	realAddress	=	"";
	/**IP*/
	 @DBColumn
	private	String	ip	=	"";
	/**仿制的网址--可能有多个参考网站*/
	 @DBColumn
	private	String	copyUrl	=	"";
	/**SMTP主机*/
	 @DBColumn
	private	String	smtpHost	=	"";
	/**SMTP用户*/
	 @DBColumn
	private	String	smtpUser	=	"";
	/**SMTP密码*/
	 @DBColumn
	private	String	smtpPwd	=	"";
	/**SMTP端口*/
	 @DBColumn
	private	Integer	smtpPort	=	new Integer(0);
	/**SMTP是否加密*/
	 @DBColumn
	private	boolean	smtpSsl	=	false;
	/**状态，1可用，0不可用*/
	 @DBColumn
	private	boolean	status	=	false;
	/**建立日期*/
	 @DBColumn
	private	java.time.LocalDateTime	createdAt	=	java.time.LocalDateTime.now();
	/**更新日期*/
	 @DBColumn
	private	java.time.LocalDateTime	updatedAt	=	java.time.LocalDateTime.now();

	public ErpDomain(){}

	public void setIdDomain(Integer	idDomain){
		this.idDomain = idDomain;
	}

	public Integer getIdDomain(){
		return idDomain;
	}

	public void setIdDepartment(Integer	idDepartment){
		this.idDepartment = idDepartment;
	}

	public Integer getIdDepartment(){
		return idDepartment;
	}

	public void setName(String	name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setRealAddress(String	realAddress){
		this.realAddress = realAddress;
	}

	public String getRealAddress(){
		return realAddress;
	}

	public void setIp(String	ip){
		this.ip = ip;
	}

	public String getIp(){
		return ip;
	}

	public void setCopyUrl(String	copyUrl){
		this.copyUrl = copyUrl;
	}

	public String getCopyUrl(){
		return copyUrl;
	}

	public void setSmtpHost(String	smtpHost){
		this.smtpHost = smtpHost;
	}

	public String getSmtpHost(){
		return smtpHost;
	}

	public void setSmtpUser(String	smtpUser){
		this.smtpUser = smtpUser;
	}

	public String getSmtpUser(){
		return smtpUser;
	}

	public void setSmtpPwd(String	smtpPwd){
		this.smtpPwd = smtpPwd;
	}

	public String getSmtpPwd(){
		return smtpPwd;
	}

	public void setSmtpPort(Integer	smtpPort){
		this.smtpPort = smtpPort;
	}

	public Integer getSmtpPort(){
		return smtpPort;
	}

	public void setSmtpSsl(boolean	smtpSsl){
		this.smtpSsl = smtpSsl;
	}

	public boolean getSmtpSsl(){
		return smtpSsl;
	}

	public void setStatus(boolean	status){
		this.status = status;
	}

	public boolean getStatus(){
		return status;
	}

	public void setCreatedAt(java.time.LocalDateTime	createdAt){
		this.createdAt = createdAt;
	}

	public java.time.LocalDateTime getCreatedAt(){
		return createdAt;
	}

	public void setUpdatedAt(java.time.LocalDateTime	updatedAt){
		this.updatedAt = updatedAt;
	}

	public java.time.LocalDateTime getUpdatedAt(){
		return updatedAt;
	}

		@Override
		public String getTable() {
		return "erp_domain";
	}

	@Override
	public Integer getId() {
		return idDomain;
	}

	@Override
	public void setId(Integer id) {
		idDomain = id;
	}
}