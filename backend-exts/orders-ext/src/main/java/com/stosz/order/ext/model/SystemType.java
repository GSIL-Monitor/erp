package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 系统类型实体类
 */
public class SystemType  extends AbstractParamEntity implements ITable, Serializable {

	/**自增主键*/
	@DBColumn
	private	Long	id	=	new Long(0);
	/**第一层级的parent_id=0*/
	@DBColumn
	private	Long	parentId	=	new Long(0);
	/**类型的key*/
	@DBColumn
	private	String	typeKey	=	"";
	/**类型的值*/
	@DBColumn
	private	String	typeValue	=	"";
	/**类型的说明*/
	@DBColumn
	private	String	typeDesc	=	"";
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

	public SystemType(){}

	public void setId(Integer	id){
		this.id = Long.valueOf(id);
	}

	public Integer getId(){
		return id.intValue();
	}

	public void setParentId(Long	parentId){
		this.parentId = parentId;
	}

	public Long getParentId(){
		return parentId;
	}

	public void setTypeKey(String	typeKey){
		this.typeKey = typeKey;
	}

	public String getTypeKey(){
		return typeKey;
	}

	public void setTypeValue(String	typeValue){
		this.typeValue = typeValue;
	}

	public String getTypeValue(){
		return typeValue;
	}

	public void setTypeDesc(String	typeDesc){
		this.typeDesc = typeDesc;
	}

	public String getTypeDesc(){
		return typeDesc;
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
		return "system_type";
	}

}