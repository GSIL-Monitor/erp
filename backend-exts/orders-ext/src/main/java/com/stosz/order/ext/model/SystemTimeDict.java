package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 增量同步配置表实体类
 */
public class SystemTimeDict  extends AbstractParamEntity implements ITable, Serializable {

	/**自增主键*/
	 @DBColumn
	private	Integer	id	=	new Integer(0);
	/**项目*/
	 @DBColumn
	private	String	system	=	"";
	/**类型*/
	 @DBColumn
	private	String	type	=	"";
	/**上次操作时间*/
	 @DBColumn
	private	java.time.LocalDateTime	lastTime	=	java.time.LocalDateTime.now();
	/***/
	 @DBColumn
	private	String	memo	=	"";

	public SystemTimeDict(){}

	public void setId(Integer	id){
		this.id = id;
	}

	public Integer getId(){
		return id.intValue();
	}

	public void setSystem(String	system){
		this.system = system;
	}

	public String getSystem(){
		return system;
	}

	public void setType(String	type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setLastTime(java.time.LocalDateTime	lastTime){
		this.lastTime = lastTime;
	}

	public java.time.LocalDateTime getLastTime(){
		return lastTime;
	}

	public void setMemo(String	memo){
		this.memo = memo;
	}

	public String getMemo(){
		return memo;
	}

		@Override
		public String getTable() {
		return "system_time_dict";
	}

}