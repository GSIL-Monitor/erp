package com.stosz.tms.model.blackcat;

import java.io.Serializable;
import java.util.Date;

public class BlackCatTrackDetail implements Serializable{
    private String trackNo;
    private String location;
    private Date trackTime;
    private String trackStatus;
    private String trackRecord;

    public BlackCatTrackDetail(String trackNo, String location, Date trackTime, String trackStatus,String trackRecord) {
        this.trackNo = trackNo;
        this.location = location;
        this.trackTime = trackTime;
        this.trackStatus = trackStatus;
        this.trackRecord = trackRecord;
    }

    public String getTrackRecord() {
        return trackRecord;
    }

    public void setTrackRecord(String trackRecord) {
        this.trackRecord = trackRecord;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTrackTime() {
        return trackTime;
    }

    public void setTrackTime(Date trackTime) {
        this.trackTime = trackTime;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }
}
