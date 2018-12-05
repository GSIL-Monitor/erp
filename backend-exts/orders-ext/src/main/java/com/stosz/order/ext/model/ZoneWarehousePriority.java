package com.stosz.order.ext.model;

import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 区域物流顺序配置实体类
 */
public class ZoneWarehousePriority  extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Integer	id;
    /**区域id*/
    @DBColumn
    private	Integer	zoneId;
    /**仓库id*/
    @DBColumn
    private	Integer	warehouseId;
    /**优先级*/
    @DBColumn
    private	Integer	priority;
    /**区域名称冗余*/
    @DBColumn
    private	String	zoneName;
    /**仓库名称冗余*/
    @DBColumn
    private	String	warehouseName;
    /**
     * 禁用的spu，多个以逗号隔开
     */
    @DBColumn
    private	String	forbidSpu;
    /**
     * 禁用的sku，多个以逗号隔开
     */
    @DBColumn
    private	String	forbidSku;
    @DBColumn
    private UseableEnum status;
    /**创建时间*/
    @DBColumn
    private	java.time.LocalDateTime	createAt;
    /**更新时间*/
    @DBColumn
    private	java.time.LocalDateTime	updateAt;
    /**创建者id*/
    @DBColumn
    private	Integer	creatorId;
    /**创建人*/
    @DBColumn
    private	String	creator;

    public ZoneWarehousePriority(){}

    public void setId(Integer	id){
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }

    public void setZoneId(Integer	zoneId){
        this.zoneId = zoneId;
    }

    public Integer getZoneId(){
        return zoneId;
    }

    public void setWarehouseId(Integer	warehouseId){
        this.warehouseId = warehouseId;
    }

    public Integer getWarehouseId(){
        return warehouseId;
    }

    public void setPriority(Integer	priority){
        this.priority = priority;
    }

    public Integer getPriority(){
        return priority;
    }

    public void setZoneName(String	zoneName){
        this.zoneName = zoneName;
    }

    public String getZoneName(){
        return zoneName;
    }

    public void setWarehouseName(String	warehouseName){
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName(){
        return warehouseName;
    }

    @Deprecated
    public String getForbidSku() {
        return forbidSku;
    }

    public void setForbidSku(String forbidSku) {
        this.forbidSku = forbidSku;
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

    public String getForbidSpu() {
        return forbidSpu;
    }

    public void setForbidSpu(String forbidSpu) {
        this.forbidSpu = forbidSpu;
    }

    public UseableEnum getStatus() {
        return status;
    }

    public void setStatus(UseableEnum status) {
        this.status = status;
    }

    @Override
    public String getTable() {
        return "zone_warehouse_priority";
    }

}