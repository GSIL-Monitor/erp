package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseReturned extends AbstractParamEntity implements ITable, IFsmInstance, Serializable {

    @DBColumn
    private Integer id;
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private LocalDateTime updateAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;
    @DBColumn
    private String purchaseNo;
    @DBColumn
    private String platOrdersNo;
    @DBColumn
    private String returnedNo;
    @DBColumn
    private Integer buDeptId;
    @DBColumn
    private String buDeptNo;
    @DBColumn
    private String buDeptName;
    @DBColumn
    private Integer wmsId;
    @DBColumn
    private String wmsSysCode;
    @DBColumn
    private String wmsName;
    @DBColumn
    private String state;
    @DBColumn
    private LocalDateTime stateTime;
    @DBColumn
    private Integer type;
    @DBColumn
    private String trackingNo;
    @DBColumn
    private BigDecimal amount;
    @DBColumn
    private BigDecimal payAmount;
    @DBColumn
    private String address;
    @DBColumn
    private Integer shippingId;
    @DBColumn
    private String shippingName;
    @DBColumn
    private Integer refundUserId;
    @DBColumn
    private Integer auditorId;
    @DBColumn
    private String refundUser;
    @DBColumn
    private String auditor;
    @DBColumn
    private LocalDateTime refundTime;
    @DBColumn
    private Integer senderUserId;
    @DBColumn
    private String senderUser;
    @DBColumn
    private LocalDateTime senderTime;
    @DBColumn
    private String memo;

    private String sku;

    private String supplierCode;

    private String supplierName;

    private String buyer;


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getPlatOrdersNo() {
        return platOrdersNo;
    }

    public void setPlatOrdersNo(String platOrdersNo) {
        this.platOrdersNo = platOrdersNo;
    }

    public String getReturnedNo() {
        return returnedNo;
    }

    public void setReturnedNo(String returnedNo) {
        this.returnedNo = returnedNo;
    }

    public Integer getBuDeptId() {
        return buDeptId;
    }

    public void setBuDeptId(Integer buDeptId) {
        this.buDeptId = buDeptId;
    }

    public String getBuDeptNo() {
        return buDeptNo;
    }

    public void setBuDeptNo(String buDeptNo) {
        this.buDeptNo = buDeptNo;
    }

    public String getBuDeptName() {
        return buDeptName;
    }

    public void setBuDeptName(String buDeptName) {
        this.buDeptName = buDeptName;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getWmsSysCode() {
        return wmsSysCode;
    }

    public void setWmsSysCode(String wmsSysCode) {
        this.wmsSysCode = wmsSysCode;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public LocalDateTime getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Integer getRefundUserId() {
        return refundUserId;
    }

    public void setRefundUserId(Integer refundUserId) {
        this.refundUserId = refundUserId;
    }

    public String getRefundUser() {
        return refundUser;
    }

    public void setRefundUser(String refundUser) {
        this.refundUser = refundUser;
    }

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(String senderUser) {
        this.senderUser = senderUser;
    }

    public LocalDateTime getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(LocalDateTime senderTime) {
        this.senderTime = senderTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    @Override
    public String getTable() {
        return "purchase_returned";
    }


}