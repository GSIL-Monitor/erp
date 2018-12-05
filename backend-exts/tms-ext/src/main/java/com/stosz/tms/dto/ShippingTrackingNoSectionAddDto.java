package com.stosz.tms.dto;

import com.stosz.plat.model.DBColumn;

import java.util.Date;

public class ShippingTrackingNoSectionAddDto {

    private Integer id;

    private Integer shippingWayId;

    private Integer productType;

    private Integer wmsId;

    private String wmsName;

    private String creator;

    private Integer creatorId;

    private String startTrackingNo;

    private String endTrackingNo;

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

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
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

    public String getStartTrackingNo() {
        return startTrackingNo;
    }

    public void setStartTrackingNo(String startTrackingNo) {
        this.startTrackingNo = startTrackingNo;
    }

    public String getEndTrackingNo() {
        return endTrackingNo;
    }

    public void setEndTrackingNo(String endTrackingNo) {
        this.endTrackingNo = endTrackingNo;
    }
}
