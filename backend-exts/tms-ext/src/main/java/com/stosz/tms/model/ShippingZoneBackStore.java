package com.stosz.tms.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.util.Date;
import java.util.List;

public class ShippingZoneBackStore extends AbstractParamEntity implements ITable {

    @DBColumn
    private Integer id;

    @DBColumn
    private Integer shippingWayId;

    private String shippingWayName;

    private String shippingWayCode;

    @DBColumn
    private Integer wmsId;

    @DBColumn
    private String wmsName;

    @DBColumn
    private Integer zoneId;

    @DBColumn
    private String zoneName;

    @DBColumn
    private Integer backWmsId;

    @DBColumn
    private String backWmsName;

    @DBColumn
    private Integer enable;

    @DBColumn
    private Date createAt;

    @DBColumn
    private Date updateAt;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    @DBColumn
    private String modifier;

    @DBColumn
    private Integer modifierId;

    private List<Integer> zoneIdList;

    private List<Integer> wmsIdList;

    private List<Integer> backWmsIdList;

    private String zoneIdStr;

    private String wmsIdStr;

    private String backWmsIdStr;

    public String getZoneIdStr() {
        return zoneIdStr;
    }

    public void setZoneIdStr(String zoneIdStr) {
        this.zoneIdStr = zoneIdStr;
    }

    public String getWmsIdStr() {
        return wmsIdStr;
    }

    public void setWmsIdStr(String wmsIdStr) {
        this.wmsIdStr = wmsIdStr;
    }

    public String getBackWmsIdStr() {
        return backWmsIdStr;
    }

    public void setBackWmsIdStr(String backWmsIdStr) {
        this.backWmsIdStr = backWmsIdStr;
    }

    public String getShippingWayCode() {
        return shippingWayCode;
    }

    public void setShippingWayCode(String shippingWayCode) {
        this.shippingWayCode = shippingWayCode;
    }

    public String getShippingWayName() {
        return shippingWayName;
    }

    public void setShippingWayName(String shippingWayName) {
        this.shippingWayName = shippingWayName;
    }

    public List<Integer> getZoneIdList() {
        return zoneIdList;
    }

    public void setZoneIdList(List<Integer> zoneIdList) {
        this.zoneIdList = zoneIdList;
    }

    public List<Integer> getWmsIdList() {
        return wmsIdList;
    }

    public void setWmsIdList(List<Integer> wmsIdList) {
        this.wmsIdList = wmsIdList;
    }

    public List<Integer> getBackWmsIdList() {
        return backWmsIdList;
    }

    public void setBackWmsIdList(List<Integer> backWmsIdList) {
        this.backWmsIdList = backWmsIdList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShippingWayId() {
        return shippingWayId;
    }

    public void setShippingWayId(Integer shippingWayId) {
        this.shippingWayId = shippingWayId;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName == null ? null : wmsName.trim();
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName == null ? null : zoneName.trim();
    }

    public Integer getBackWmsId() {
        return backWmsId;
    }

    public void setBackWmsId(Integer backWmsId) {
        this.backWmsId = backWmsId;
    }

    public String getBackWmsName() {
        return backWmsName;
    }

    public void setBackWmsName(String backWmsName) {
        this.backWmsName = backWmsName == null ? null : backWmsName.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    @Override
    public String getTable() {
        return "shipping_zone_back_store";
    }
}