package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 活动实体类
 */
public class Activity  extends AbstractParamEntity implements ITable, Serializable {

	/**自增主键*/
	@DBColumn
	private	Long	id	=	new Long(0);
	/**商户id,即广告人员id*/
	@DBColumn
	private	Integer	merchantId	=	new Integer(0);
	/**活动名称*/
	@DBColumn
	private	String	name	=	"";
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

	public Activity(){}

	public void setId(Integer	id){
		this.id = Long.valueOf(id);
	}

	public Integer getId(){
		return id.intValue();
	}

	public void setMerchantId(Integer	merchantId){
		this.merchantId = merchantId;
	}

	public Integer getMerchantId(){
		return merchantId;
	}

	public void setName(String	name){
		this.name = name;
	}

	public String getName(){
		return name;
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
		return "activity";
	}

}