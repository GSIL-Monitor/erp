package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-16. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 实体类
 */
public class ErpWarehouse  extends AbstractParamEntity implements ITable, Serializable {

	/***/
	 @DBColumn
	private	Integer	idWarehouse	=	new Integer(0);
	/***/
	 @DBColumn
	private	Integer	idZone	=	new Integer(0);
	/**优先级:订单出货优先级(1~10)*/
	 @DBColumn
	private	Integer	priority	=	new Integer(0);
	/**仓库名称*/
	 @DBColumn
	private	String	title	=	"";
	/**仓库地址*/
	 @DBColumn
	private	String	address	=	"";
	/**是否投入使用*/
	 @DBColumn
	private	Integer	status	=	new Integer(0);
	/**建立日期*/
	 @DBColumn
	private	java.time.LocalDateTime	createdAt	=	java.time.LocalDateTime.now();
	/**更新日期*/
	 @DBColumn
	private	java.time.LocalDateTime	updatedAt	=	java.time.LocalDateTime.now();
	/**是否为转寄仓库*/
	 @DBColumn
	private	Integer	forward	=	new Integer(0);
	/**WMS仓库编码*/
	 @DBColumn
	private	String	warehouseWmsTitle	=	"";
	/**是否为wms仓库*/
	 @DBColumn
	private	Integer	wmswarehouse	=	0;

	public ErpWarehouse(){}

	public void setIdWarehouse(Integer	idWarehouse){
		this.idWarehouse = idWarehouse;
	}

	public Integer getIdWarehouse(){
		return idWarehouse;
	}

	public void setIdZone(Integer	idZone){
		this.idZone = idZone;
	}

	public Integer getIdZone(){
		return idZone;
	}

	public void setPriority(Integer	priority){
		this.priority = priority;
	}

	public Integer getPriority(){
		return priority;
	}

	public void setTitle(String	title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setAddress(String	address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setStatus(Integer	status){
		this.status = status;
	}

	public Integer getStatus(){
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

	public void setForward(Integer	forward){
		this.forward = forward;
	}

	public Integer getForward(){
		return forward;
	}

	public void setWarehouseWmsTitle(String	warehouseWmsTitle){
		this.warehouseWmsTitle = warehouseWmsTitle;
	}

	public String getWarehouseWmsTitle(){
		return warehouseWmsTitle;
	}

	public void setWmswarehouse(Integer	wmswarehouse){
		this.wmswarehouse = wmswarehouse;
	}

	public Integer getWmswarehouse(){
		return wmswarehouse;
	}

	@Override public String getTable() {
		return "erp_warehouse";
	}

	@Override
	public Integer getId() {
		return idWarehouse;
	}

	@Override
	public void setId(Integer id) {
		idWarehouse = id;
	}
}