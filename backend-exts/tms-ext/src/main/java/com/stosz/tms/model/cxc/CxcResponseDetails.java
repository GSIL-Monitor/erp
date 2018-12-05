package com.stosz.tms.model.cxc;

import java.io.Serializable;
import java.util.Date;

public class CxcResponseDetails implements Serializable{
    private String bdBptypecode;//节点状态
    private String billCode;//运单号
    private String description;//描述
    private String emp;//节点人员
    private String empPhone;//节点人员电话
    private Date eventDate;//节点时间
    private String eventLocation;//节点地点
    private String proType;
    private String scanMan;//扫描人
    private String scanType;//扫描类型

    public String getBdBptypecode() {
        return bdBptypecode;
    }

    public void setBdBptypecode(String bdBptypecode) {
        this.bdBptypecode = bdBptypecode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getEmpPhone() {
        return empPhone;
    }

    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getScanMan() {
        return scanMan;
    }

    public void setScanMan(String scanMan) {
        this.scanMan = scanMan;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }
}
