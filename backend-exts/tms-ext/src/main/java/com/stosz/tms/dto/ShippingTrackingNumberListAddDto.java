package com.stosz.tms.dto;

import java.util.List;

public class ShippingTrackingNumberListAddDto {

    private Integer shippingWayId;

    private Integer productType;

    private Integer wmsId;

    private String wmsName;

    private String creator;

    private Integer creatorId;

    private String trackNumbers;

    private List<String> trackNumberList;


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

    public String getTrackNumbers() {
        return trackNumbers;
    }

    public void setTrackNumbers(String trackNumbers) {
        this.trackNumbers = trackNumbers;
    }

    public List<String> getTrackNumberList() {
        return trackNumberList;
    }

    public void setTrackNumberList(List<String> trackNumberList) {
        this.trackNumberList = trackNumberList;
    }
}
