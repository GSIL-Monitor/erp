package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单物流信息实体类
 */
public class OrderLogistic  extends AbstractParamEntity implements ITable, Serializable {

	/**自增主键*/
	 @DBColumn
	private	Long	id	=	new Long(0);
	/**订单id*/
	 @DBColumn
	private	Long	orderId	=	new Long(0);
	/**运单号*/
	 @DBColumn
	private	String	trackingNo	=	"";
	/**是否拒收*/
	 @DBColumn
	private	Integer	rejectStatusEnum	=	new Integer(0);
	/**运单状态*/
	 @DBColumn
	private	Integer	trackingStatus	=	new Integer(0);
	/**产品id*/
	 @DBColumn
	private	Integer	productId	=	new Integer(0);
	/**产品名称*/
	 @DBColumn
	private	String	productTitle	=	"";
	/**sku*/
	 @DBColumn
	private	String	sku	=	"";
	/**spu信息*/
	 @DBColumn
	private	String	spu	=	"";
	/**运单信息*/
	 @DBColumn
	private	String	trackingMemo	=	"";
	/**物流公司id*/
	 @DBColumn
	private	Integer	logisticsId	=	new Integer(0);
	/**物流公司名称*/
	@DBColumn
	private	String	logisticsName	=	"";
	/**创建时间*/
	 @DBColumn
	private	java.time.LocalDateTime	createAt	=	java.time.LocalDateTime.now();
	/**更新时间*/
	 @DBColumn
	private	java.time.LocalDateTime	updateAt	=	java.time.LocalDateTime.now();
	/**创建者id*/
	 @DBColumn
	private	Integer	creatorId	=	new Integer(0);
	/**创建人*/
	 @DBColumn
	private	String	creator	=	"";

	public OrderLogistic(){}

	public void setId(Integer	id){
		this.id = Long.valueOf(id);
	}

	public Integer getId(){
		return id.intValue();
	}

	public void setOrderId(Long	orderId){
		this.orderId = orderId;
	}

	public Long getOrderId(){
		return orderId;
	}

	public void setTrackingNo(String	trackingNo){
		this.trackingNo = trackingNo;
	}

	public String getTrackingNo(){
		return trackingNo;
	}

	public void setRejectStatusEnum(Integer	rejectStatusEnum){
		this.rejectStatusEnum = rejectStatusEnum;
	}

	public Integer getRejectStatusEnum(){
		return rejectStatusEnum;
	}

	public void setTrackingStatus(Integer	trackingStatus){
		this.trackingStatus = trackingStatus;
	}

	public Integer getTrackingStatus(){
		return trackingStatus;
	}

	public void setProductId(Integer	productId){
		this.productId = productId;
	}

	public Integer getProductId(){
		return productId;
	}

	public void setProductTitle(String	productTitle){
		this.productTitle = productTitle;
	}

	public String getProductTitle(){
		return productTitle;
	}

	public void setSku(String	sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	public void setSpu(String	spu){
		this.spu = spu;
	}

	public String getSpu(){
		return spu;
	}

	public void setTrackingMemo(String	trackingMemo){
		this.trackingMemo = trackingMemo;
	}

	public String getTrackingMemo(){
		return trackingMemo;
	}

	public void setLogisticsId(Integer	logisticsId){
		this.logisticsId = logisticsId;
	}

	public Integer getLogisticsId(){
		return logisticsId;
	}

	public void setCreateAt(java.time.LocalDateTime	createAt){
		this.createAt = createAt;
	}

	public java.time.LocalDateTime getCreateAt(){
		return createAt;
	}

	public void setUpdateAt(java.time.LocalDateTime	updateAt){
		this.updateAt = updateAt;
	}

	public java.time.LocalDateTime getUpdateAt(){
		return updateAt;
	}

	public void setCreatorId(Integer	creatorId){
		this.creatorId = creatorId;
	}

	public Integer getCreatorId(){
		return creatorId;
	}

	public void setCreator(String	creator){
		this.creator = creator;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getCreator(){
		return creator;
	}

		@Override
		public String getTable() {
		return "order_logistic";
	}

}