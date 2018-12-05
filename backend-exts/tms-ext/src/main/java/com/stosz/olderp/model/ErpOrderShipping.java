package com.stosz.olderp.model;

import java.math.BigDecimal;
import java.util.Date;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

public class ErpOrderShipping extends AbstractParamEntity implements ITable {
	private Integer idOrderShipping;

	private Integer idShipping;

	private Integer idOrder;

	private Short fetchCount;

	private Byte isEmail;

	private String shippingName;

	private String trackNumber;

	private String statusLabel;

	private String summaryStatusLabel;

	private Date dateOnline;

	private Date dateReturn;

	private Date dateDelivery;

	private Date dateSigned;

	private Integer status;

	private Byte isSettlemented;

	private Date createdAt;

	private Date updatedAt;

	private BigDecimal freight;

	private BigDecimal formalitiesFee;

	private BigDecimal weight;

	private Boolean hasSent;

	private String remark;

	private String orderIdSa;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getOrderIdSa() {
		return orderIdSa;
	}

	public void setOrderIdSa(String orderIdSa) {
		this.orderIdSa = orderIdSa == null ? null : orderIdSa.trim();
	}

	public Integer getIdOrderShipping() {
		return idOrderShipping;
	}

	public void setIdOrderShipping(Integer idOrderShipping) {
		this.idOrderShipping = idOrderShipping;
	}

	public Integer getIdShipping() {
		return idShipping;
	}

	public void setIdShipping(Integer idShipping) {
		this.idShipping = idShipping;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public Short getFetchCount() {
		return fetchCount;
	}

	public void setFetchCount(Short fetchCount) {
		this.fetchCount = fetchCount;
	}

	public Byte getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(Byte isEmail) {
		this.isEmail = isEmail;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName == null ? null : shippingName.trim();
	}

	public String getTrackNumber() {
		return trackNumber;
	}

	public void setTrackNumber(String trackNumber) {
		this.trackNumber = trackNumber == null ? null : trackNumber.trim();
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel == null ? null : statusLabel.trim();
	}

	public String getSummaryStatusLabel() {
		return summaryStatusLabel;
	}

	public void setSummaryStatusLabel(String summaryStatusLabel) {
		this.summaryStatusLabel = summaryStatusLabel == null ? null : summaryStatusLabel.trim();
	}

	public Date getDateOnline() {
		return dateOnline;
	}

	public void setDateOnline(Date dateOnline) {
		this.dateOnline = dateOnline;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public Date getDateDelivery() {
		return dateDelivery;
	}

	public void setDateDelivery(Date dateDelivery) {
		this.dateDelivery = dateDelivery;
	}

	public Date getDateSigned() {
		return dateSigned;
	}

	public void setDateSigned(Date dateSigned) {
		this.dateSigned = dateSigned;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Byte getIsSettlemented() {
		return isSettlemented;
	}

	public void setIsSettlemented(Byte isSettlemented) {
		this.isSettlemented = isSettlemented;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getFormalitiesFee() {
		return formalitiesFee;
	}

	public void setFormalitiesFee(BigDecimal formalitiesFee) {
		this.formalitiesFee = formalitiesFee;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Boolean getHasSent() {
		return hasSent;
	}

	public void setHasSent(Boolean hasSent) {
		this.hasSent = hasSent;
	}

	@Override
	public String getTable() {
		return "erp_order_shipping";
	}

	@Override
	public Integer getId() {
		return this.idOrderShipping;
	}

	@Override
	public void setId(Integer id) {
		this.idOrderShipping = id;
	}
}