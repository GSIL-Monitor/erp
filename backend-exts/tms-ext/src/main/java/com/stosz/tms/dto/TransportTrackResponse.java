package com.stosz.tms.dto;

import java.io.Serializable;
import java.util.List;

import com.stosz.tms.model.TrackingTaskDetail;

public class TransportTrackResponse extends AbstractResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	List<TrackingTaskDetail> trackDetails;

	public List<TrackingTaskDetail> getTrackDetails() {
		return trackDetails;
	}

	public void setTrackDetails(List<TrackingTaskDetail> trackDetails) {
		this.trackDetails = trackDetails;
	}
	
	


}
