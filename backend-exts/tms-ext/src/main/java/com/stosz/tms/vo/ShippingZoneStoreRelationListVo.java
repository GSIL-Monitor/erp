package com.stosz.tms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stosz.plat.model.DBColumn;

import java.util.Date;

public class ShippingZoneStoreRelationListVo {

    private Integer id;

    private Integer shippingWayId;

    private String zoneName;

    private Integer zoneId;

    private Integer allowedProductType;

    private Integer wmsId;

    private String wmsName;

    private Integer enable;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createAt;

    private String creator;

    private Integer creatorId;

    private String shippingWayCode;

    private String shippingWayName;


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

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getAllowedProductType() {
        return allowedProductType;
    }

    public void setAllowedProductType(Integer allowedProductType) {
        this.allowedProductType = allowedProductType;
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
        this.wmsName = wmsName;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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
}
