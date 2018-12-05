package com.stosz.tms.vo;

public class TrackingTaskExportVo {

    private String orderNo;

    private String trackNo;

    private String trackStatus;

    private String trackStatusTime;

    private String failMessage;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public String getTrackStatusTime() {
        return trackStatusTime;
    }

    public void setTrackStatusTime(String trackStatusTime) {
        this.trackStatusTime = trackStatusTime;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }
}
