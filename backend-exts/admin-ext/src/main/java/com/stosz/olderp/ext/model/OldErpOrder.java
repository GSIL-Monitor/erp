package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 实体类
 */
public class OldErpOrder extends AbstractParamEntity implements ITable, Serializable {

	/***/
	 @DBColumn
	private	Integer	idOrder	=	new Integer(0);
	/**仓库*/
	 @DBColumn
	private	Integer	idWarehouse	=	new Integer(0);
	/**部门*/
	 @DBColumn
	private	Integer	idDepartment	=	new Integer(0);
	/**用户ID，应该是广告手ID*/
	 @DBColumn
	private	Integer	idUsers	=	new Integer(0);
	/**物流ID*/
	 @DBColumn
	private	Integer	idShipping	=	new Integer(0);
	/***/
	 @DBColumn
	private	Integer	idZone	=	new Integer(0);
	/**订单状态*/
	 @DBColumn
	private	Integer	idOrderStatus	=	new Integer(0);
	/**域名ID*/
	 @DBColumn
	private	Integer	idDomain	=	new Integer(0);
	/**订单号*/
	 @DBColumn
	private	Long	idIncrement	=	new Long(0);
	/**黑名单等级*/
	 @DBColumn
	private	Integer	blacklistLevel	=	new Integer(0);
	/**广告投放链接带的参数*/
	 @DBColumn
	private	Long	identify	=	new Long(0);
	/**访问来源*/
	 @DBColumn
	private	String	httpReferer	=	"";
	/**名字*/
	 @DBColumn
	private	String	firstName	=	"";
	/**姓*/
	 @DBColumn
	private	String	lastName	=	"";
	/**国家*/
	 @DBColumn
	private	String	country	=	"";
	/**电话*/
	 @DBColumn
	private	String	tel	=	"";
	/**备用电话*/
	 @DBColumn
	private	String	reserveNumber	=	"";
	/**邮箱*/
	 @DBColumn
	private	String	email	=	"";
	/**省/洲*/
	 @DBColumn
	private	String	province	=	"";
	/**城市*/
	 @DBColumn
	private	String	city	=	"";
	/**区域*/
	 @DBColumn
	private	String	area	=	"";
	/**详细地址*/
	 @DBColumn
	private	String	address	=	"";
	/**邮编*/
	 @DBColumn
	private	String	zipcode	=	"";
	/**客户留言*/
	 @DBColumn
	private	String	remark	=	"";
	/**同样的数据下单数,重复数*/
	 @DBColumn
	private	Integer	orderRepeat	=	new Integer(0);
	/**用户购买总次数*/
	 @DBColumn
	private	Integer	orderCount	=	new Integer(0);
	/**每个订单购买数量*/
	 @DBColumn
	private	Integer	totalQtyOrdered	=	new Integer(0);
	/**订单总价格*/
	 @DBColumn
	private	java.math.BigDecimal	priceTotal	=	new java.math.BigDecimal(0);
	/**货币代码*/
	 @DBColumn
	private	String	currencyCode	=	"";
	/**支付方式*/
	 @DBColumn
	private	String	paymentMethod	=	"";
	/**支付状态*/
	 @DBColumn
	private	String	paymentStatus	=	"";
	/**支付内容*/
	 @DBColumn
	private	String	paymentDetails	=	"";
	/**orderNo*/
	 @DBColumn
	private	String	paymentOrderNo	=	"";
	/**merchOrderNo*/
	 @DBColumn
	private	String	paymentMerchOrderNo	=	"";
	/**内部备注*/
	 @DBColumn
	private	String	comment	=	"";
	/**是否拒签*/
	 @DBColumn
	private	Integer	refusedToSign	=	new Integer(0);
	/**前台下单时间*/
	 @DBColumn
	private	java.time.LocalDateTime	datePurchase	=	java.time.LocalDateTime.now();
	/**ERP接收到订单的时间*/
	 @DBColumn
	private	java.time.LocalDateTime	createdAt	=	java.time.LocalDateTime.now();
	/**更新日期*/
	 @DBColumn
	private	java.time.LocalDateTime	updatedAt	=	java.time.LocalDateTime.now();
	/**发货时间*/
	 @DBColumn
	private	java.time.LocalDateTime	dateDelivery	=	java.time.LocalDateTime.now();
	/**订单问题件名称*/
	 @DBColumn
	private	String	problemName	=	"";
	/**1:未联系；2:约时间中;3:确定时间*/
	 @DBColumn
	private	Integer	confirmationStatus	=	new Integer(0);
	/**建站网站信息*/
	 @DBColumn
	private	String	webInfo	=	"";
	/***/
	 @DBColumn
	private	Integer	idDepartmentMaster	=	new Integer(0);
	/***/
	 @DBColumn
	private	Integer	isForward	=	new Integer(0);
	/**着陆码*/
	 @DBColumn
	private	String	zoneCode	=	"";

	public OldErpOrder(){}

	public void setIdOrder(Integer	idOrder){
		this.idOrder = idOrder;
	}

	public Integer getIdOrder(){
		return idOrder;
	}

	public void setIdWarehouse(Integer	idWarehouse){
		this.idWarehouse = idWarehouse;
	}

	public Integer getIdWarehouse(){
		return idWarehouse;
	}

	public void setIdDepartment(Integer	idDepartment){
		this.idDepartment = idDepartment;
	}

	public Integer getIdDepartment(){
		return idDepartment;
	}

	public void setIdUsers(Integer	idUsers){
		this.idUsers = idUsers;
	}

	public Integer getIdUsers(){
		return idUsers;
	}

	public void setIdShipping(Integer	idShipping){
		this.idShipping = idShipping;
	}

	public Integer getIdShipping(){
		return idShipping;
	}

	public void setIdZone(Integer	idZone){
		this.idZone = idZone;
	}

	public Integer getIdZone(){
		return idZone;
	}

	public void setIdOrderStatus(Integer	idOrderStatus){
		this.idOrderStatus = idOrderStatus;
	}

	public Integer getIdOrderStatus(){
		return idOrderStatus;
	}

	public void setIdDomain(Integer	idDomain){
		this.idDomain = idDomain;
	}

	public Integer getIdDomain(){
		return idDomain;
	}

	public void setIdIncrement(Long	idIncrement){
		this.idIncrement = idIncrement;
	}

	public Long getIdIncrement(){
		return idIncrement;
	}

	public void setBlacklistLevel(Integer	blacklistLevel){
		this.blacklistLevel = blacklistLevel;
	}

	public Integer getBlacklistLevel(){
		return blacklistLevel;
	}

	public void setIdentify(Long	identify){
		this.identify = identify;
	}

	public Long getIdentify(){
		return identify;
	}

	public void setHttpReferer(String	httpReferer){
		this.httpReferer = httpReferer;
	}

	public String getHttpReferer(){
		return httpReferer;
	}

	public void setFirstName(String	firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setLastName(String	lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCountry(String	country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setTel(String	tel){
		this.tel = tel;
	}

	public String getTel(){
		return tel;
	}

	public void setReserveNumber(String	reserveNumber){
		this.reserveNumber = reserveNumber;
	}

	public String getReserveNumber(){
		return reserveNumber;
	}

	public void setEmail(String	email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setProvince(String	province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setCity(String	city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setArea(String	area){
		this.area = area;
	}

	public String getArea(){
		return area;
	}

	public void setAddress(String	address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setZipcode(String	zipcode){
		this.zipcode = zipcode;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setRemark(String	remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public Integer getOrderRepeat() {
		return orderRepeat;
	}

	public void setOrderRepeat(Integer orderRepeat) {
		this.orderRepeat = orderRepeat;
	}

	public void setOrderCount(Integer	orderCount){
		this.orderCount = orderCount;
	}

	public Integer getOrderCount(){
		return orderCount;
	}

	public void setTotalQtyOrdered(Integer	totalQtyOrdered){
		this.totalQtyOrdered = totalQtyOrdered;
	}

	public Integer getTotalQtyOrdered(){
		return totalQtyOrdered;
	}

	public void setPriceTotal(java.math.BigDecimal	priceTotal){
		this.priceTotal = priceTotal;
	}

	public java.math.BigDecimal getPriceTotal(){
		return priceTotal;
	}

	public void setCurrencyCode(String	currencyCode){
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode(){
		return currencyCode;
	}

	public void setPaymentMethod(String	paymentMethod){
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethod(){
		return paymentMethod;
	}

	public void setPaymentStatus(String	paymentStatus){
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentStatus(){
		return paymentStatus;
	}

	public void setPaymentDetails(String	paymentDetails){
		this.paymentDetails = paymentDetails;
	}

	public String getPaymentDetails(){
		return paymentDetails;
	}

	public void setPaymentOrderNo(String	paymentOrderNo){
		this.paymentOrderNo = paymentOrderNo;
	}

	public String getPaymentOrderNo(){
		return paymentOrderNo;
	}

	public void setPaymentMerchOrderNo(String	paymentMerchOrderNo){
		this.paymentMerchOrderNo = paymentMerchOrderNo;
	}

	public String getPaymentMerchOrderNo(){
		return paymentMerchOrderNo;
	}

	public void setComment(String	comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setRefusedToSign(Integer	refusedToSign){
		this.refusedToSign = refusedToSign;
	}

	public Integer getRefusedToSign(){
		return refusedToSign;
	}

	public void setDatePurchase(java.time.LocalDateTime	datePurchase){
		this.datePurchase = datePurchase;
	}

	public java.time.LocalDateTime getDatePurchase(){
		return datePurchase;
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

	public void setDateDelivery(java.time.LocalDateTime	dateDelivery){
		this.dateDelivery = dateDelivery;
	}

	public java.time.LocalDateTime getDateDelivery(){
		return dateDelivery;
	}

	public void setProblemName(String	problemName){
		this.problemName = problemName;
	}

	public String getProblemName(){
		return problemName;
	}

	public void setConfirmationStatus(Integer	confirmationStatus){
		this.confirmationStatus = confirmationStatus;
	}

	public Integer getConfirmationStatus(){
		return confirmationStatus;
	}

	public void setWebInfo(String	webInfo){
		this.webInfo = webInfo;
	}

	public String getWebInfo(){
		return webInfo;
	}

	public void setIdDepartmentMaster(Integer	idDepartmentMaster){
		this.idDepartmentMaster = idDepartmentMaster;
	}

	public Integer getIdDepartmentMaster(){
		return idDepartmentMaster;
	}

	public void setIsForward(Integer	isForward){
		this.isForward = isForward;
	}

	public Integer getIsForward(){
		return isForward;
	}

	public void setZoneCode(String	zoneCode){
		this.zoneCode = zoneCode;
	}

	public String getZoneCode(){
		return zoneCode;
	}

		@Override
		public String getTable() {
		return "erp_order";
	}

	@Override
	public Integer getId() {
		return idOrder;
	}

	@Override
	public void setId(Integer id) {
		this.idOrder = id;
	}
}