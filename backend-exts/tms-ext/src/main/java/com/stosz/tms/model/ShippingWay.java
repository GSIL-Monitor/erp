package com.stosz.tms.model;

import java.time.LocalDateTime;
import java.util.List;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

public class ShippingWay extends AbstractParamEntity implements ITable {
	private static final long serialVersionUID = 1L;

	@DBColumn
	private Integer id;
	@DBColumn
	private String shippingWayCode;
	@DBColumn
	private String shippingWayName;
	@DBColumn
	private Integer shippingId;
	@DBColumn
	private String shippingCode;
	@DBColumn
	private String address;
	@DBColumn
	private String telphone;
	@DBColumn
	private String channel;
	@DBColumn
	private String account;
	@DBColumn
	private String modifier;
	@DBColumn
	private Integer modifierId;
	@DBColumn
	private LocalDateTime createAt;
	@DBColumn
	private LocalDateTime updateAt;
	@DBColumn
	private String creator;
	@DBColumn
	private Integer creatorId;
	@DBColumn
	private Integer state;

	@DBColumn
	private Integer needSendOrder;// 是否需要发送运单 0 不需要 1 需要

	@DBColumn
	private Integer needTrackNum;// 是否需要预先导入运单号 0 api返回运单号(不需要) 1 预先导入运单号(需要)

	@DBColumn
	private Integer trackNoWaringEnable;// 是否开启运单号的预警 0 不开启运单号预警 1 开启运单号预警

	@DBColumn
	private Integer trackNoWarningNum;// 运单号预警数值
	
	@DBColumn
	private Integer apiId;//物流接口ID
	
	@DBColumn
	private String apiCode;//物流接口Code


	private List<Integer> idList;



	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public Integer getApiId() {
		return apiId;
	}

	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	public Integer getNeedSendOrder() {
		return needSendOrder;
	}

	public void setNeedSendOrder(Integer needSendOrder) {
		this.needSendOrder = needSendOrder;
	}

	public Integer getNeedTrackNum() {
		return needTrackNum;
	}

	public void setNeedTrackNum(Integer needTrackNum) {
		this.needTrackNum = needTrackNum;
	}

	public Integer getTrackNoWaringEnable() {
		return trackNoWaringEnable;
	}

	public void setTrackNoWaringEnable(Integer trackNoWaringEnable) {
		this.trackNoWaringEnable = trackNoWaringEnable;
	}

	public Integer getTrackNoWarningNum() {
		return trackNoWarningNum;
	}

	public void setTrackNoWarningNum(Integer trackNoWarningNum) {
		this.trackNoWarningNum = trackNoWarningNum;
	}

	private String shippingName;

	private String apiName;


	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public Integer getId() {
		return id;
	}

	public String getShippingWayCode() {
		return shippingWayCode;
	}

	public void setShippingWayCode(String shippingWayCode) {
		this.shippingWayCode = shippingWayCode == null ? null : shippingWayCode.trim();
	}

	public String getShippingWayName() {
		return shippingWayName;
	}

	public void setShippingWayName(String shippingWayName) {
		this.shippingWayName = shippingWayName == null ? null : shippingWayName.trim();
	}

	public Integer getShippingId() {
		return shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public String getShippingCode() {
		return shippingCode;
	}

	public void setShippingCode(String shippingCode) {
		this.shippingCode = shippingCode == null ? null : shippingCode.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone == null ? null : telphone.trim();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier == null ? null : modifier.trim();
	}

	public Integer getModifierId() {
		return modifierId;
	}

	public void setModifierId(Integer modifierId) {
		this.modifierId = modifierId;
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
		this.creator = creator == null ? null : creator.trim();
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String getTable() {
		return "shipping_way";
	}

	@Override
	public void setId(Integer id) {
		this.id = id;

	}
}