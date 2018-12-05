package com.stosz.tms.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ShippingTrackingNoList extends AbstractParamEntity implements ITable {
    @DBColumn
    private Integer id;

    @DBColumn
    private Integer shippingWayId;

    @DBColumn
    private String trackNumber;

    @DBColumn
    private Integer trackStatus;

    @DBColumn
    private Integer productType;

    @DBColumn
    private Integer wmsId;

    @DBColumn
    private String wmsName;

    @DBColumn
    private Date createAt;

    @DBColumn
    private Date updateAt;

    @DBColumn
    private Integer version;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    @DBColumn
    private String modifier;

    @DBColumn
    private Integer modifierId;

    private List<Integer> shippingWayIdList;

    public List<Integer> getShippingWayIdList() {
        return shippingWayIdList;
    }

    public void setShippingWayIdList(List<Integer> shippingWayIdList) {
        this.shippingWayIdList = shippingWayIdList;
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
        this.creator = creator;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    public Integer getShippingWayId() {
        return shippingWayId;
    }

    public void setShippingWayId(Integer shippingWayId) {
        this.shippingWayId = shippingWayId;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber == null ? null : trackNumber.trim();
    }

    public Integer getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(Integer trackStatus) {
        this.trackStatus = trackStatus;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public String getTable() {
        return "shipping_tracking_no_list";
    }
}