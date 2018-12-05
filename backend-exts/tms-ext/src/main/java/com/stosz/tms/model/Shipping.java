package com.stosz.tms.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Shipping  extends AbstractParamEntity implements Serializable, ITable {

    @DBColumn
    private Integer id;

    @DBColumn
    private String shippingName;

    @DBColumn
    private String shippingCode;

    @DBColumn
    private String md5Key;

    @DBColumn
    private String shipmentNumberUrl;

    @DBColumn
    private String interfaceUrl;

    @DBColumn
    private String waybilltrackUrl;

    @DBColumn
    private String tmsServiceCode;

    @DBColumn
    private String account;

    @DBColumn
    private String token;

    @DBColumn
    private String modifier;

    @DBColumn
    private Integer modifierId;

    @DBColumn
    private Date createAt;

    @DBColumn
    private Date updateAt;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    @DBColumn
    private String tag;

    private List<String> inShppingCodes;
    

    public List<String> getInShppingCodes() {
		return inShppingCodes;
	}

	public void setInShppingCodes(List<String> inShppingCodes) {
		this.inShppingCodes = inShppingCodes;
	}

	public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName == null ? null : shippingName.trim();
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode == null ? null : shippingCode.trim();
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key == null ? null : md5Key.trim();
    }

    public String getShipmentNumberUrl() {
        return shipmentNumberUrl;
    }

    public void setShipmentNumberUrl(String shipmentNumberUrl) {
        this.shipmentNumberUrl = shipmentNumberUrl == null ? null : shipmentNumberUrl.trim();
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl == null ? null : interfaceUrl.trim();
    }

    public String getWaybilltrackUrl() {
        return waybilltrackUrl;
    }

    public void setWaybilltrackUrl(String waybilltrackUrl) {
        this.waybilltrackUrl = waybilltrackUrl == null ? null : waybilltrackUrl.trim();
    }

    public String getTmsServiceCode() {
        return tmsServiceCode;
    }

    public void setTmsServiceCode(String tmsServiceCode) {
        this.tmsServiceCode = tmsServiceCode == null ? null : tmsServiceCode.trim();
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id ;
    }


    @Override
    public String getTable() {
        return "shipping";
    }
}