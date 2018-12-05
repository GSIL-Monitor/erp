package com.stosz.tms.model;

import java.util.Date;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class TrackingTaskDetail extends AbstractParamEntity implements ITable {
	@DBColumn
	private Integer id;

	@Override
	public String toString() {
		return "TrackingTaskDetail{" +
				"id=" + id +
				", trackingTaskId=" + trackingTaskId +
				", trackNo='" + trackNo + '\'' +
				", trackStatus='" + trackStatus + '\'' +
				", classifyCode='" + classifyCode + '\'' +
				", classifyStatus='" + classifyStatus + '\'' +
				", trackTime=" + trackTime +
				", cityName='" + cityName + '\'' +
				", trackReason='" + trackReason + '\'' +
				", hashValue='" + hashValue + '\'' +
				", trackRecord='" + trackRecord + '\'' +
				", createAt=" + createAt +
				", fileName='" + fileName + '\'' +
				", apiCode='" + apiCode + '\'' +
				'}';
	}

	/**
	 * 物流轨迹主表ID
	 */
	@DBColumn
	private Long trackingTaskId;

	/**
	 * 运单号
	 */
	@DBColumn
	private String trackNo;

	/**
	 * 运单状态
	 */
	@DBColumn
	private String trackStatus;

	/**
	 * 归类状态Code
	 */
	@DBColumn
	private String classifyCode;

	/**
	 * 归类状态
	 */
	@DBColumn
	private String classifyStatus;

	/**
	 * 状态时间
	 */
	@DBColumn
	private Date trackTime;

	/**
	 * 城市名称
	 */
	@DBColumn
	private String cityName;

	/**
	 * 状态备注
	 */
	@DBColumn
	private String trackReason;
	@DBColumn
	private String hashValue;
	@DBColumn
	private String trackRecord;
	@DBColumn
	private Date createAt;
	
	private String fileName;
	
	private String apiCode;

	public TrackingTaskDetail(String trackNo, String trackStatus, Date trackTime, String cityName, String trackRecord) {
		this.trackNo = trackNo;
		this.trackStatus = trackStatus;
		this.trackTime = trackTime;
		this.cityName = cityName;
		this.trackRecord = trackRecord;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String shippingCode) {
		this.apiCode = shippingCode;
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

	public TrackingTaskDetail() {

	}

	public TrackingTaskDetail(String trackinfo, String trackStatus, Date trackTime) {
		this.trackRecord = trackinfo;
		this.trackStatus = trackStatus;
		this.trackTime = trackTime;
	}

	public String getClassifyCode() {
		return classifyCode;
	}

	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTrackingTaskId() {
		return trackingTaskId;
	}

	public void setTrackingTaskId(Long trackingTaskId) {
		this.trackingTaskId = trackingTaskId;
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
		this.trackStatus = trackStatus == null ? null : trackStatus.trim();
	}

	public String getClassifyStatus() {
		return classifyStatus;
	}

	public void setClassifyStatus(String classifyStatus) {
		this.classifyStatus = classifyStatus == null ? null : classifyStatus.trim();
	}

	public Date getTrackTime() {
		return trackTime;
	}

	public void setTrackTime(Date trackTime) {
		this.trackTime = trackTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName == null ? null : cityName.trim();
	}

	public String getTrackReason() {
		return trackReason;
	}

	public void setTrackReason(String trackReason) {
		this.trackReason = trackReason == null ? null : trackReason.trim();
	}

	
	public String getHashValue() {
		return hashValue;
	}

	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public String getTrackRecord() {
		return trackRecord;
	}

	public void setTrackRecord(String trackRecord) {
		this.trackRecord = trackRecord == null ? null : trackRecord.trim();
	}

	@Override
	public String getTable() {
		return "tracking_task_detail";
	}
}