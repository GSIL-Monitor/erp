package com.stosz.tms.model;

import java.util.Date;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

public class ShippingTrackApi extends AbstractParamEntity implements ITable {
	private Integer id;

	private String apiName;

	private String apiCode;

	private String md5Key;

	private String waybilltrackUrl;

	private String account;

	private String token;

	private Date createAt;

	private Date updateAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName == null ? null : apiName.trim();
	}

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode == null ? null : apiCode.trim();
	}

	public String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key == null ? null : md5Key.trim();
	}

	public String getWaybilltrackUrl() {
		return waybilltrackUrl;
	}

	public void setWaybilltrackUrl(String waybilltrackUrl) {
		this.waybilltrackUrl = waybilltrackUrl == null ? null : waybilltrackUrl.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token == null ? null : token.trim();
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

	@Override
	public String getTable() {
		return "shipping_track_api";
	}
}