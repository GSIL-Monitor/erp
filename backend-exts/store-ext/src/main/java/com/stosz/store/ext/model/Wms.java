package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.store.ext.enums.UsesWmsEnum;
import com.stosz.store.ext.enums.WmsStateEnum;
import com.stosz.store.ext.enums.WmsTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/22 18:31
 * @Update Time:
 */
public class Wms extends AbstractParamEntity implements ITable, Serializable {

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
    private String name;
    @DBColumn
    private Integer zoneId;
    @DBColumn
    private String zoneName;
    @DBColumn
    private Integer type;
    @DBColumn
    private Integer state;
    @DBColumn
    private Integer useWms;
    @DBColumn
    private String linkman;  //寄件联系人
    @DBColumn
    private String mobile;
    @DBColumn
    private String address;
    @DBColumn
    private String wmsSysCode;
    @DBColumn
    private Integer priority;

    @DBColumn
    private Integer deleted = new Integer(0); //是否删除 0-否 1-是  规避关键字
    @DBColumn
    private String sender;//寄件公司

    @DBColumn
    private String senderEmail;//寄件人邮箱

    @DBColumn
    private String senderProvince;//寄件省份
    @DBColumn
    private String senderCity;//寄件城市
    @DBColumn
    private String senderTown;//寄件区/县

    @DBColumn
    private String senderZipcode;//寄件邮编
    @DBColumn
    private String senderCountry;//寄件国家

    /**
     * 仓库类型名称
     */
    private transient WmsTypeEnum wmsTypeEnum;

    /**
     * 是否使用WMS
     */
    private transient UsesWmsEnum usesWmsEnum;

    /**
     * 仓库状态
     */
    private transient WmsStateEnum wmsStateEnum;


    public WmsTypeEnum getWmsTypeEnum() {
        return type == null ? null : WmsTypeEnum.fromId(type);
    }

    public void setWmsTypeEnum(WmsTypeEnum wmsTypeEnum) {
        this.wmsTypeEnum = wmsTypeEnum;
    }

    public UsesWmsEnum getUsesWmsEnum() {
        return useWms == null ? null : UsesWmsEnum.fromId(useWms);
    }

    public void setUsesWmsEnum(UsesWmsEnum usesWmsEnum) {
        this.usesWmsEnum = usesWmsEnum;
    }

    public WmsStateEnum getWmsStateEnum() {
        return state == null ? null : WmsStateEnum.fromId(state);
    }

    public void setWmsStateEnum(WmsStateEnum wmsStateEnum) {
        this.wmsStateEnum = wmsStateEnum;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderTown() {
        return senderTown;
    }

    public void setSenderTown(String senderTown) {
        this.senderTown = senderTown;
    }

    public String getSenderZipcode() {
        return senderZipcode;
    }

    public void setSenderZipcode(String senderZipcode) {
        this.senderZipcode = senderZipcode;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUseWms() {
        return useWms;
    }

    public void setUseWms(Integer useWms) {
        this.useWms = useWms;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWmsSysCode() {
        return wmsSysCode;
    }

    public void setWmsSysCode(String wmsSysCode) {
        this.wmsSysCode = wmsSysCode;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String getTable() {
        return "wms";
    }
}
