package com.stosz.tms.vo;

public class ShippingZoneBackStoreExportVo {

    private String zoneId;

    private String shippingWayCode;

    private String wmsId;

    private String backWmsId;

    private String failMessage;

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getShippingWayCode() {
        return shippingWayCode;
    }

    public void setShippingWayCode(String shippingWayCode) {
        this.shippingWayCode = shippingWayCode;
    }

    public String getWmsId() {
        return wmsId;
    }

    public void setWmsId(String wmsId) {
        this.wmsId = wmsId;
    }

    public String getBackWmsId() {
        return backWmsId;
    }

    public void setBackWmsId(String backWmsId) {
        this.backWmsId = backWmsId;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }
}
