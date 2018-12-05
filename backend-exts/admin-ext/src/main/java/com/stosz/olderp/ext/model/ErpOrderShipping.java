package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 实体类
 */
public class ErpOrderShipping  extends AbstractParamEntity implements ITable, Serializable {

	/***/
	 @DBColumn
	private	Integer	idOrderShipping	=	0;
	/**物流ID*/
	 @DBColumn
	private	Integer	idShipping	=	new Integer(0);
	/**订单ID*/
	 @DBColumn
	private	Integer	idOrder	=	new Integer(0);
	/**抓取次数*/
	 @DBColumn
	private	Integer	fetchCount	=	0;
	/**是否发送快递邮件*/
	 @DBColumn
	private	Integer	isEmail	=	new Integer(0);
	/**物流名字，方便不需连表查询*/
	 @DBColumn
	private	String	shippingName	=	"";
	/**快递单号*/
	 @DBColumn
	private	String	trackNumber	=	"";
	/**状态标签*/
	 @DBColumn
	private	String	statusLabel	=	"";
	/**状态汇总*/
	 @DBColumn
	private	String	summaryStatusLabel	=	"";
	/**在黑猫系统上线时间*/
	 @DBColumn
	private	java.time.LocalDateTime	dateOnline	=	java.time.LocalDateTime.now();
	/**退货时间*/
	 @DBColumn
	private	java.time.LocalDateTime	dateReturn	=	java.time.LocalDateTime.now();
	/**配送日期与订单的date_delivery一样*/
	 @DBColumn
	private	java.time.LocalDateTime	dateDelivery	=	java.time.LocalDateTime.now();
	/**签收日期*/
	 @DBColumn
	private	java.time.LocalDateTime	dateSigned	=	java.time.LocalDateTime.now();
	/**运单号状态:0正常,1消单*/
	 @DBColumn
	private	Integer	status	=	new Integer(0);
	/**备注*/
	 @DBColumn
	private	String	remark	=	"";
	/**运单号是否已结款*/
	 @DBColumn
	private	Integer	isSettlemented	=	new Integer(0);
	/**建立时间*/
	 @DBColumn
	private	java.time.LocalDateTime	createdAt	=	java.time.LocalDateTime.now();
	/**更新日期*/
	 @DBColumn
	private	java.time.LocalDateTime	updatedAt	=	java.time.LocalDateTime.now();
	/**运费*/
	 @DBColumn
	private	BigDecimal	freight	=	new BigDecimal(0);
	/**手续费*/
	 @DBColumn
	private	BigDecimal	formalitiesFee	=	new BigDecimal(0);
	/***/
	 @DBColumn
	private	BigDecimal	weight	=	new BigDecimal(0);
	/**备用字段*/
	 @DBColumn
	private	String	orderIdSa	=	"";
	/**是否已将运单发送给物流*/
	 @DBColumn
	private	boolean	hasSent	=	false;
	/***/
	 @DBColumn
	private	String	idOrderSa	=	"";

	public ErpOrderShipping(){}

	public void setIdOrderShipping(Integer	idOrderShipping){
		this.idOrderShipping = idOrderShipping;
	}

	public Integer getIdOrderShipping(){
		return idOrderShipping;
	}

	public void setIdShipping(Integer	idShipping){
		this.idShipping = idShipping;
	}

	public Integer getIdShipping(){
		return idShipping;
	}

	public void setIdOrder(Integer	idOrder){
		this.idOrder = idOrder;
	}

	public Integer getIdOrder(){
		return idOrder;
	}

	public void setFetchCount(Integer	fetchCount){
		this.fetchCount = fetchCount;
	}

	public Integer getFetchCount(){
		return fetchCount;
	}

	public void setIsEmail(Integer	isEmail){
		this.isEmail = isEmail;
	}

	public Integer getIsEmail(){
		return isEmail;
	}

	public void setShippingName(String	shippingName){
		this.shippingName = shippingName;
	}

	public String getShippingName(){
		return shippingName;
	}

	public void setTrackNumber(String	trackNumber){
		this.trackNumber = trackNumber;
	}

	public String getTrackNumber(){
		return trackNumber;
	}

	public void setStatusLabel(String	statusLabel){
		this.statusLabel = statusLabel;
	}

	public String getStatusLabel(){
		return statusLabel;
	}

	public void setSummaryStatusLabel(String	summaryStatusLabel){
		this.summaryStatusLabel = summaryStatusLabel;
	}

	public String getSummaryStatusLabel(){
		return summaryStatusLabel;
	}

	public void setDateOnline(java.time.LocalDateTime	dateOnline){
		this.dateOnline = dateOnline;
	}

	public java.time.LocalDateTime getDateOnline(){
		return dateOnline;
	}

	public void setDateReturn(java.time.LocalDateTime	dateReturn){
		this.dateReturn = dateReturn;
	}

	public java.time.LocalDateTime getDateReturn(){
		return dateReturn;
	}

	public void setDateDelivery(java.time.LocalDateTime	dateDelivery){
		this.dateDelivery = dateDelivery;
	}

	public java.time.LocalDateTime getDateDelivery(){
		return dateDelivery;
	}

	public void setDateSigned(java.time.LocalDateTime	dateSigned){
		this.dateSigned = dateSigned;
	}

	public java.time.LocalDateTime getDateSigned(){
		return dateSigned;
	}

	public void setStatus(Integer	status){
		this.status = status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setRemark(String	remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setIsSettlemented(Integer	isSettlemented){
		this.isSettlemented = isSettlemented;
	}

	public Integer getIsSettlemented(){
		return isSettlemented;
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

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getFormalitiesFee() {
		return formalitiesFee;
	}

	public void setFormalitiesFee(BigDecimal formalitiesFee) {
		this.formalitiesFee = formalitiesFee;
	}

	public void setWeight(BigDecimal	weight){
		this.weight = weight;
	}

	public BigDecimal getWeight(){
		return weight;
	}

	public void setOrderIdSa(String	orderIdSa){
		this.orderIdSa = orderIdSa;
	}

	public String getOrderIdSa(){
		return orderIdSa;
	}

	public void setHasSent(boolean	hasSent){
		this.hasSent = hasSent;
	}

	public boolean getHasSent(){
		return hasSent;
	}

	public void setIdOrderSa(String	idOrderSa){
		this.idOrderSa = idOrderSa;
	}

	public String getIdOrderSa(){
		return idOrderSa;
	}

		@Override
		public String getTable() {
		return "erp_order_shipping";
	}

	@Override
	public Integer getId() {
		return idOrderShipping;
	}

	@Override
	public void setId(Integer id) {
		idOrderShipping = id;
	}
}