package com.stosz.tms.model.api;

import com.stosz.plat.model.DBColumn;

public class KerryStatus {
    private String con_no;
    private String status_code;
    private String status_desc;
    private String update_date;
    private String ref_no;
    private String location;
    private String hashValue;

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }
    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }
    public String getCon_no() {
        return con_no;
    }

    public void setCon_no(String con_no) {
        this.con_no = con_no;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
