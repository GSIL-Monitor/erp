package com.stosz.tms.model;

import java.util.Date;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class TrackingTaskBlackcatDetail extends AbstractParamEntity implements ITable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBColumn
	private Long id;
	@DBColumn
	private String trackNo;
	@DBColumn
	private String trackStatus;
	@DBColumn
	private Date trackTime;
	@DBColumn
	private String location;
	@DBColumn
	private String trackRecord;
	@DBColumn
	private Date createAt;
	@DBColumn
	private String fileName;
	
	public TrackingTaskBlackcatDetail() {
		
	}

	public TrackingTaskBlackcatDetail(String trackNo, String trackStatus, Date trackTime, String location, String trackRecord) {
		this.trackNo = trackNo;
		this.trackStatus = trackStatus;
		this.trackTime = trackTime;
		this.location = location;
		this.trackRecord = trackRecord;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Long getLongId() {
		return id;
	}

	public Integer getId() {
		if (id != null) {
			return id.intValue();
		}
		return null;
	}

	public void setId(Long id) {
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

	@Override
	public String getTable() {
		return "tracking_task_blackcat_detail";
	}

	@Override
	public void setId(Integer id) {
		this.id = id.longValue();
	}
}