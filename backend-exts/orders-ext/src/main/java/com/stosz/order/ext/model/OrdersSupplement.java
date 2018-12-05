package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 补货订单信息实体类
 */
public class OrdersSupplement  extends AbstractParamEntity implements ITable, Serializable {

	/**自增主键*/
	 @DBColumn
	private	Long	id	=	new Long(0);
	/**订单id*/
	 @DBColumn
	private	Long	orderId	=	new Long(0);
	/**补发原因*/
	 @DBColumn
	private	String	supplementReason	=	"";
	/**补发收取的费用，其它的可以忽略*/
	 @DBColumn
	private	java.math.BigDecimal	addAmount	=	new java.math.BigDecimal(0);
	/**取消备注*/
	 @DBColumn
	private	String	memo	=	"";
	/**订单总价*/
	 @DBColumn
	private	java.math.BigDecimal	orderAmount	=	new java.math.BigDecimal(0);
	/**退换货的状态*/
	 @DBColumn
	private	Integer	supplementStatusEnum	=	new Integer(0);
	/**补发的内部订单号*/
	 @DBColumn
	private	Long	interOrderId	=	new Long(0);
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

	public OrdersSupplement(){}

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

	public void setSupplementReason(String	supplementReason){
		this.supplementReason = supplementReason;
	}

	public String getSupplementReason(){
		return supplementReason;
	}

	public void setAddAmount(java.math.BigDecimal	addAmount){
		this.addAmount = addAmount;
	}

	public java.math.BigDecimal getAddAmount(){
		return addAmount;
	}

	public void setMemo(String	memo){
		this.memo = memo;
	}

	public String getMemo(){
		return memo;
	}

	public void setOrderAmount(java.math.BigDecimal	orderAmount){
		this.orderAmount = orderAmount;
	}

	public java.math.BigDecimal getOrderAmount(){
		return orderAmount;
	}

	public void setSupplementStatusEnum(Integer	supplementStatusEnum){
		this.supplementStatusEnum = supplementStatusEnum;
	}

	public Integer getSupplementStatusEnum(){
		return supplementStatusEnum;
	}

	public void setInterOrderId(Long	interOrderId){
		this.interOrderId = interOrderId;
	}

	public Long getInterOrderId(){
		return interOrderId;
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

	public String getCreator(){
		return creator;
	}

		@Override
		public String getTable() {
		return "orders_supplement";
	}

}