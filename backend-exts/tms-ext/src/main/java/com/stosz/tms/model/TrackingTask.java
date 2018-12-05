package com.stosz.tms.model;

import java.util.Date;
import java.util.List;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class TrackingTask extends AbstractParamEntity implements ITable {

	@DBColumn
	private Long id;

	/**
	 * 包裹ID
	 */
	@DBColumn
	private Integer shippingParcelId;

	/**
	 * 物流方式ID
	 */
	@DBColumn
	private Integer shippingWayId;

	/**
	 * 物流公司CODE
	 */
	@DBColumn
	private String apiCode;

	/**
	 * 订单ID
	 */
	@DBColumn
	private Integer orderId;

	/**
	 * 运单号
	 */
	@DBColumn
	private String trackNo;

	/**
	 * 抓取次数
	 */
	@DBColumn
	private Integer fetchCount;

	/**
	 * 物流状态
	 */
	@DBColumn
	private String trackStatus;

	/**
	 * 归类状态
	 */
	@DBColumn
	private String classifyStatus;

	/**
	 * 归类状态 CODE
	 */
	@DBColumn
	private String classifyCode;

	/**
	 * 物流状态时间
	 */
	@DBColumn
	private Date trackStatusTime;

	/**
	 * 运单当前状态变更的时间
	 */
	@DBColumn
	private Date classifyStatusTime;

	/**
	 * 上线时间
	 */
	@DBColumn
	private Date onlineTime;


	/**
	 * 派单中的时间
	 */
	@DBColumn
	private Date pendingTime;

	/**
	 * 拒收时间
	 */
	@DBColumn
	private Date rejectTime;

	/**
	 * 签收时间
	 */
	@DBColumn
	private Date receiveTime;

	/**
	 * 最后一次抓取时间
	 */
	@DBColumn
	private Date lastCaptureTime;

	/**
	 * 退货时间
	 */
	@DBColumn
	private Date returnedTime;
	
	/**
	 * 派送失败的时间
	 */
	@DBColumn
	private Date deliveryFailureTime;

	/**
	 * 是否已经完成0  未完成 1完成
	 */
	@DBColumn
	private Integer complete;

	/**
	 * 程序归类状态code
	 */
	@DBColumn
	private String routineCode;

	/**
	 * 程序归类状态
	 */
	@DBColumn
	private String routineStatus;

	/**
	 * 程序归类状态时间
	 */
	@DBColumn
	private Date routineStatusTime;

	@DBColumn
	private Date createAt;

	/**
	 * 老ERP订单ID
	 */
	@DBColumn
	private Integer idOrderShipping;

	/**
	 * 老ERP物流商ID
	 */
	@DBColumn
	private Integer oldIdShipping;

	private Integer timeType;

	private Date startTime;

	private Date endTime;
	
	private Long maxRecordId;//最大的记录ID
	
	public Long getMaxRecordId() {
		return maxRecordId;
	}

	public void setMaxRecordId(Long maxRecordId) {
		this.maxRecordId = maxRecordId;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getIdOrderShipping() {
		return idOrderShipping;
	}

	public void setIdOrderShipping(Integer idOrderShipping) {
		this.idOrderShipping = idOrderShipping;
	}

	public Integer getOldIdShipping() {
		return oldIdShipping;
	}

	public void setOldIdShipping(Integer oldIdShipping) {
		this.oldIdShipping = oldIdShipping;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

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

	private List<Integer> parcelIdList;

	public List<Integer> getParcelIdList() {
		return parcelIdList;
	}

	public void setParcelIdList(List<Integer> parcelIdList) {
		this.parcelIdList = parcelIdList;
	}

	public Integer getShippingParcelId() {
		return shippingParcelId;
	}

	public void setShippingParcelId(Integer shippingParcelId) {
		this.shippingParcelId = shippingParcelId;
	}

	public String getClassifyCode() {
		return classifyCode;
	}

	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}

	public Date getReturnedTime() {
		return returnedTime;
	}

	public void setReturnedTime(Date returnedTime) {
		this.returnedTime = returnedTime;
	}

	public Integer getId() {
		if (id != null)
			return id.intValue();
		else
			return null;
	}

	public long getLongId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getShippingWayId() {
		return shippingWayId;
	}

	public void setShippingWayId(Integer shippingWayId) {
		this.shippingWayId = shippingWayId;
	}
	
	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
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
		this.trackNo = trackNo == null ? null : trackNo.trim();
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
		this.trackStatus = trackStatus == null ? null : trackStatus.trim();
	}

	public String getClassifyStatus() {
		return classifyStatus;
	}

	public void setClassifyStatus(String classifyStatus) {
		this.classifyStatus = classifyStatus == null ? null : classifyStatus.trim();
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

	public Integer getComplete() {
		return complete;
	}

	public void setComplete(Integer complete) {
		this.complete = complete;
	}

	@Override
	public String getTable() {
		return "tracking_task";
	}

	@Override
	public void setId(Integer id) {
		this.id = id.longValue();
	}

	public Date getDeliveryFailureTime() {
		return deliveryFailureTime;
	}

	public void setDeliveryFailureTime(Date deliveryFailureTime) {
		this.deliveryFailureTime = deliveryFailureTime;
	}
}