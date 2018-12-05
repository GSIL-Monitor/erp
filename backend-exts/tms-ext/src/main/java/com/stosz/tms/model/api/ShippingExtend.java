package com.stosz.tms.model.api;

import com.stosz.plat.model.AbstractParamEntity;

import java.io.Serializable;
import java.util.Date;

public class ShippingExtend extends AbstractParamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String code;

	private String sender;//寄件公司

	private String senderContactName;//寄件联系人

	private String senderEmail;//寄件人邮箱

	private String senderTelphone;//寄件联系人电话

	private String senderProvince;//寄件省份

	private String senderCity;//寄件城市

	private String cityId;//城市id

	private String senderTown;//寄件区/县

	private String senderAddress;//寄件地址

	private String senderZipcode;//寄件邮编

	private String senderCountry;//寄件国家

	private String senderCountryCode;//寄件国家二字码

	private String interfaceUrl;//物流下单地址
	
	private String waybilltrackUrl;//物流运单轨迹查询地址

	private String modifier;//修改人

	private Integer modifierId;//修改人ID
	
	private String account;//接口账号
	
	private String md5Key;//接口MD5Key
	
	private String token;//接口TOKEN

	private Date updateAt;

	private Date createAt;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getSenderCountryCode() {
		return senderCountryCode;
	}

	public void setSenderCountryCode(String senderCountryCode) {
		this.senderCountryCode = senderCountryCode;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWaybilltrackUrl() {
		return waybilltrackUrl;
	}

	public void setWaybilltrackUrl(String waybilltrackUrl) {
		this.waybilltrackUrl = waybilltrackUrl;
	}

	public String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}

	public Integer getId() {
		return id.intValue();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender == null ? null : sender.trim();
	}

	public String getSenderContactName() {
		return senderContactName;
	}

	public void setSenderContactName(String senderContactName) {
		this.senderContactName = senderContactName == null ? null : senderContactName.trim();
	}

	public String getSenderTelphone() {
		return senderTelphone;
	}

	public void setSenderTelphone(String senderTelphone) {
		this.senderTelphone = senderTelphone == null ? null : senderTelphone.trim();
	}

	public String getSenderProvince() {
		return senderProvince;
	}

	public void setSenderProvince(String senderProvince) {
		this.senderProvince = senderProvince == null ? null : senderProvince.trim();
	}

	public String getSenderCity() {
		return senderCity;
	}

	public void setSenderCity(String senderCity) {
		this.senderCity = senderCity == null ? null : senderCity.trim();
	}

	public String getSenderTown() {
		return senderTown;
	}

	public void setSenderTown(String senderTown) {
		this.senderTown = senderTown == null ? null : senderTown.trim();
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress == null ? null : senderAddress.trim();
	}

	public String getSenderZipcode() {
		return senderZipcode;
	}

	public void setSenderZipcode(String senderZipcode) {
		this.senderZipcode = senderZipcode == null ? null : senderZipcode.trim();
	}

	public String getSenderCountry() {
		return senderCountry;
	}

	public void setSenderCountry(String senderCountry) {
		this.senderCountry = senderCountry == null ? null : senderCountry.trim();
	}

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl == null ? null : interfaceUrl.trim();
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

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub

	}
}