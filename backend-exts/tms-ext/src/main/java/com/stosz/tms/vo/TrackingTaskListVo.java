package com.stosz.tms.vo;

import com.stosz.plat.model.DBColumn;

import java.util.Date;

public class TrackingTaskListVo {

   
    private Long id;

    private Integer shippingParcelId;

    private Integer shippingWayId;

    private String shippingWayName;

    private String shippingCode;

    private Integer orderId;

    private String trackNo;
   
    private Integer fetchCount;
   
    private String trackStatus;
   
    private String classifyStatus;
   
    private String classifyCode;
   
    private Date trackStatusTime;
   
    private Date classifyStatusTime;
   
    private Date onlineTime;
   
    private Date pendingTime;
   
    private Date rejectTime;
   
    private Date receiveTime;
   
    private Date lastCaptureTime;
   
    private Date returnedTime;
   
    private Integer complete;
    
	private String routineCode;

	private String routineStatus;
	
	private Date routineStatusTime;


    public String getRoutineCode() {
		return routineCode;
	}

	public void setRoutineCode(String routineCode) {
		this.routineCode = routineCode;
	}

	public String getRoutineStatus() {
		return routineStatus;
	}

	public void setRoutineStatus(String routineStatus) {
		this.routineStatus = routineStatus;
	}

	public Date getRoutineStatusTime() {
		return routineStatusTime;
	}

	public void setRoutineStatusTime(Date routineStatusTime) {
		this.routineStatusTime = routineStatusTime;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShippingParcelId() {
        return shippingParcelId;
    }

    public void setShippingParcelId(Integer shippingParcelId) {
        this.shippingParcelId = shippingParcelId;
    }

    public Integer getShippingWayId() {
        return shippingWayId;
    }

    public void setShippingWayId(Integer shippingWayId) {
        this.shippingWayId = shippingWayId;
    }

    public String getShippingWayName() {
        return shippingWayName;
    }

    public void setShippingWayName(String shippingWayName) {
        this.shippingWayName = shippingWayName;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public Integer getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(Integer fetchCount) {
        this.fetchCount = fetchCount;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus;
    }

    public String getClassifyCode() {
        return classifyCode;
    }

    public void setClassifyCode(String classifyCode) {
        this.classifyCode = classifyCode;
    }

    public Date getTrackStatusTime() {
        return trackStatusTime;
    }

    public void setTrackStatusTime(Date trackStatusTime) {
        this.trackStatusTime = trackStatusTime;
    }

    public Date getClassifyStatusTime() {
        return classifyStatusTime;
    }

    public void setClassifyStatusTime(Date classifyStatusTime) {
        this.classifyStatusTime = classifyStatusTime;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getPendingTime() {
        return pendingTime;
    }

    public void setPendingTime(Date pendingTime) {
        this.pendingTime = pendingTime;
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getLastCaptureTime() {
        return lastCaptureTime;
    }

    public void setLastCaptureTime(Date lastCaptureTime) {
        this.lastCaptureTime = lastCaptureTime;
    }

    public Date getReturnedTime() {
        return returnedTime;
    }

    public void setReturnedTime(Date returnedTime) {
        this.returnedTime = returnedTime;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }
}
