package com.stosz.tms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stosz.plat.model.DBColumn;

import java.math.BigDecimal;
import java.util.Date;

public class ShippingParcelDetailVo {

        private Integer id;

        private String parcelNo;

        private String orderNo;

        private BigDecimal orderAmount;

        private BigDecimal orderRealAmount;

        private Integer goodsQty;

        private Integer orderType;

        private Integer payState;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
        private Date orderDate;

        private String currencyCode;

        private Integer warehouseId;

        private Integer zoneId;

        private String firstName;

        private String lastName;

        private String telphone;

        private String email;

        private String province;

        private String city;

        private String address;

        private String zipcode;

        private String countryCode;

        private String country;

        private Long customerId;

        private String area;

        private Date syncTime;

        private Date lastSyncTime;

        private Integer syncStatus;

        private String syncInfo;

        private String code;

        private String trackNo;

        private BigDecimal weight;

        private Integer isSettlemented;

        private BigDecimal freightFee;

        private BigDecimal formalitiesFee;

        private String trackStatus;

        private String classifyStatus;

        private Date lastTrackTime;

        private Integer status;

        private String orderRemark;

        private String serviceRemark;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
        private Date createAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
        private Date updateAt;

        private Integer state;

        private String memo;


        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getParcelNo() {
                return parcelNo;
        }

        public void setParcelNo(String parcelNo) {
                this.parcelNo = parcelNo;
        }

        public String getOrderNo() {
                return orderNo;
        }

        public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
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
                this.currencyCode = currencyCode;
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
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getTelphone() {
                return telphone;
        }

        public void setTelphone(String telphone) {
                this.telphone = telphone;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getProvince() {
                return province;
        }

        public void setProvince(String province) {
                this.province = province;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getZipcode() {
                return zipcode;
        }

        public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
        }

        public String getCountryCode() {
                return countryCode;
        }

        public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
        }

        public String getCountry() {
                return country;
        }

        public void setCountry(String country) {
                this.country = country;
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
                this.area = area;
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
                this.syncInfo = syncInfo;
        }

        public String getCode() {
                return code;
        }

        public void setCode(String code) {
                this.code = code;
        }

        public String getTrackNo() {
                return trackNo;
        }

        public void setTrackNo(String trackNo) {
                this.trackNo = trackNo;
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
                this.trackStatus = trackStatus;
        }

        public String getClassifyStatus() {
                return classifyStatus;
        }

        public void setClassifyStatus(String classifyStatus) {
                this.classifyStatus = classifyStatus;
        }

        public Date getLastTrackTime() {
                return lastTrackTime;
        }

        public void setLastTrackTime(Date lastTrackTime) {
                this.lastTrackTime = lastTrackTime;
        }

        public Integer getStatus() {
                return status;
        }

        public void setStatus(Integer status) {
                this.status = status;
        }

        public String getOrderRemark() {
                return orderRemark;
        }

        public void setOrderRemark(String orderRemark) {
                this.orderRemark = orderRemark;
        }

        public String getServiceRemark() {
                return serviceRemark;
        }

        public void setServiceRemark(String serviceRemark) {
                this.serviceRemark = serviceRemark;
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
}
