package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 取消订单实体类
 */
public class OrdersCancel  extends AbstractParamEntity implements ITable, Serializable {

	/**自增主键*/
	 @DBColumn
	private	Long	id	=	new Long(0);
	/**订单id*/
	 @DBColumn
	private	Long	orderId	=	new Long(0);
	/**取消原因*/
	 @DBColumn
	private	String	cancelReason	=	"";
	/**取消备注*/
	 @DBColumn
	private	String	memo	=	"";
	/**取消状态*/
	 @DBColumn
	private	Integer	cancelStatusEnum	=	new Integer(0);
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

	public OrdersCancel(){}

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

	public void setCancelReason(String	cancelReason){
		this.cancelReason = cancelReason;
	}

	public String getCancelReason(){
		return cancelReason;
	}

	public void setMemo(String	memo){
		this.memo = memo;
	}

	public String getMemo(){
		return memo;
	}

	public void setCancelStatusEnum(Integer	cancelStatusEnum){
		this.cancelStatusEnum = cancelStatusEnum;
	}

	public Integer getCancelStatusEnum(){
		return cancelStatusEnum;
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
		return "orders_cancel";
	}

}