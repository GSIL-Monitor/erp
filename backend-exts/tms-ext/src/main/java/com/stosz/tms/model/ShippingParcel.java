package com.stosz.tms.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShippingParcel extends AbstractParamEntity implements ITable {

	@DBColumn
	private Integer id;

	@DBColumn
	private Integer shippingWayId;

	@DBColumn
	private String parcelNo;

	@DBColumn
	private Integer orderId;

	@DBColumn
	private String orderNo;

	@DBColumn
	private BigDecimal orderAmount;

	@DBColumn
	private BigDecimal orderRealAmount;

	@DBColumn
	private Integer goodsQty;

	@DBColumn
	private Integer orderType;

	@DBColumn
	private Integer payState;

	@DBColumn
	private Date orderDate;

	@DBColumn
	private String currencyCode;

	@DBColumn
	private Integer warehouseId;

	@DBColumn
	private Integer zoneId;

	@DBColumn
	private String firstName;

	@DBColumn
	private String lastName;

	@DBColumn
	private String telphone;

	@DBColumn
	private String email;

	@DBColumn
	private String province;

	@DBColumn
	private String city;

	@DBColumn
	private String address;

	@DBColumn
	private String zipcode;

	@DBColumn
	private String countryCode;

	@DBColumn
	private String country;

	@DBColumn
	private Long customerId;

	@DBColumn
	private String area;

	@DBColumn
	private Date syncTime;

	@DBColumn
	private Date lastSyncTime;

	@DBColumn
	private Integer syncStatus;

	@DBColumn
	private String syncInfo;

	@DBColumn
	private String code;

	@DBColumn
	private String trackNo;

	@DBColumn
	private BigDecimal weight;

	@DBColumn
	private Integer isSettlemented;

	@DBColumn
	private BigDecimal freightFee;

	@DBColumn
	private BigDecimal formalitiesFee;

	@DBColumn
	private String trackStatus;

	@DBColumn
	private String classifyStatus;

	@DBColumn
	private Date lastTrackTime;

	@DBColumn
	private String orderRemark;

	@DBColumn
	private String serviceRemark;

	@DBColumn
	private Date createAt;

	@DBColumn
	private Date updateAt;

	@DBColumn
	private Integer state;

	@DBColumn
	private String memo;

	@DBColumn
	private Date shipmentsTime;

	@DBColumn
	private Integer scheduleId;

	@DBColumn
	private String shippingAliasCode;

	@DBColumn
	private String shippingAliasName;

	@DBColumn
	private String assignErrorMsg;

	@DBColumn
	private Integer parcelState;
	
	@DBColumn
	private Integer productType;
	
	@DBColumn
	private String responseData;

	private String shippingWayIdStr;

	private List<Integer> shippingWayIdList;

	
	
	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getShippingWayIdStr() {
		return shippingWayIdStr;
	}

	public void setShippingWayIdStr(String shippingWayIdStr) {
		this.shippingWayIdStr = shippingWayIdStr;
	}

	public List<Integer> getShippingWayIdList() {
		return shippingWayIdList;
	}

	public void setShippingWayIdList(List<Integer> shippingWayIdList) {
		this.shippingWayIdList = shippingWayIdList;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	private List<ShippingParcelDetail> parcelDetails;

	private List<String> orderNoList;

	private List<String> trackNoList;

	public String getShippingAliasCode() {
		return shippingAliasCode;
	}

	public void setShippingAliasCode(String shippingAliasCode) {
		this.shippingAliasCode = shippingAliasCode;
	}

	public String getShippingAliasName() {
		return shippingAliasName;
	}

	public void setShippingAliasName(String shippingAliasName) {
		this.shippingAliasName = shippingAliasName;
	}

	public Integer getParcelState() {
		return parcelState;
	}

	public void setParcelState(Integer parcelState) {
		this.parcelState = parcelState;
	}

	public String getAssignErrorMsg() {
		return assignErrorMsg;
	}

	public void setAssignErrorMsg(String assignErrorMsg) {
		this.assignErrorMsg = assignErrorMsg;
	}

	public Date getShipmentsTime() {
		return shipmentsTime;
	}

	public void setShipmentsTime(Date shipmentsTime) {
		this.shipmentsTime = shipmentsTime;
	}

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public List<String> getTrackNoList() {
		return trackNoList;
	}

	public void setTrackNoList(List<String> trackNoList) {
		this.trackNoList = trackNoList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShippingWayId() {
		return shippingWayId;
	}

	public void setShippingWayId(Integer shippingWayId) {
		this.shippingWayId = shippingWayId;
	}

	public String getParcelNo() {
		return parcelNo;
	}

	public void setParcelNo(String parcelNo) {
		this.parcelNo = parcelNo == null ? null : parcelNo.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getOrderRealAmount() {
		return orderRealAmount;
	}

	public void setOrderRealAmount(BigDecimal orderRealAmount) {
		this.orderRealAmount = orderRealAmount;
	}

	public Integer getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode == null ? null : currencyCode.trim();
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName == null ? null : firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName == null ? null : lastName.trim();
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone == null ? null : telphone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode == null ? null : zipcode.trim();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode == null ? null : countryCode.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public Date getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Date lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getSyncInfo() {
		return syncInfo;
	}

	public void setSyncInfo(String syncInfo) {
		this.syncInfo = syncInfo == null ? null : syncInfo.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo == null ? null : trackNo.trim();
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Integer getIsSettlemented() {
		return isSettlemented;
	}

	public void setIsSettlemented(Integer isSettlemented) {
		this.isSettlemented = isSettlemented;
	}

	public BigDecimal getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(BigDecimal freightFee) {
		this.freightFee = freightFee;
	}

	public BigDecimal getFormalitiesFee() {
		return formalitiesFee;
	}

	public void setFormalitiesFee(BigDecimal formalitiesFee) {
		this.formalitiesFee = formalitiesFee;
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

	public Date getLastTrackTime() {
		return lastTrackTime;
	}

	public void setLastTrackTime(Date lastTrackTime) {
		this.lastTrackTime = lastTrackTime;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark == null ? null : orderRemark.trim();
	}

	public String getServiceRemark() {
		return serviceRemark;
	}

	public void setServiceRemark(String serviceRemark) {
		this.serviceRemark = serviceRemark == null ? null : serviceRemark.trim();
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<ShippingParcelDetail> getParcelDetails() {
		return parcelDetails;
	}

	public void setParcelDetails(List<ShippingParcelDetail> parcelDetails) {
		this.parcelDetails = parcelDetails;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public String getTable() {
		return "shipping_parcel";
	}
}