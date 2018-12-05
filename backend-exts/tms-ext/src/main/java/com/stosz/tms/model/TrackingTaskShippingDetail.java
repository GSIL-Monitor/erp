package com.stosz.tms.model;

import java.util.Date;

public class TrackingTaskShippingDetail {
    private Integer id;

    private String trackNo;

    private String trackStatus;

    private Date trackTime;

    private String location;

    private String trackRecord;

    private Date createAt;

    private String apiCode;

    private String hashValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo == null ? null : trackNo.trim();
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus == null ? null : trackStatus.trim();
    }

    public Date getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Date trackTime) {
        this.trackTime = trackTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(String trackRecord) {
        this.trackRecord = trackRecord == null ? null : trackRecord.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String shippingCode) {
        this.apiCode = shippingCode == null ? null : shippingCode.trim();
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue == null ? null : hashValue.trim();
    }
}