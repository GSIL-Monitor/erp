package com.stosz.tms.dto;

import com.stosz.tms.model.TrackingTask;

public class TrackRequest {

	private String trackingNo;//运单号

	private TrackingTask trackingTask;
	
	public TrackRequest() {
		
	}
	
	public TrackRequest(String trackingNo) {
		this.trackingNo=trackingNo;
	}
	
	

	public TrackRequest(String trackingNo, TrackingTask trackingTask) {
		this.trackingNo = trackingNo;
		this.trackingTask = trackingTask;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public TrackingTask getTrackingTask() {
		return trackingTask;
	}

	public void setTrackingTask(TrackingTask trackingTask) {
		this.trackingTask = trackingTask;
	}
	
	

}
