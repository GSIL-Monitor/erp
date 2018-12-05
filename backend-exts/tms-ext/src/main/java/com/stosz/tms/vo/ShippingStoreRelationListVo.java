package com.stosz.tms.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShippingStoreRelationListVo {

    private Integer id;

    private Integer shippingWayId;

    private String wmsName;

    private Integer wmsId;

    private Integer enable;

    private Integer isBackChoice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateAt;

    private String creator;

    private Integer creatorId;

    private String modifier;

    private Integer modifier_id;

    private String shippingWayCode;

    private String shippingWayName;

    private Integer allowedProductType;

    private Integer expressSheetType;

    private String shippingGeneralName;

    private String shippingSpeciaName;

    private String shippingGeneralCode;

    private String shippingSpeciaCode;

    public Integer getIsBackChoice() {
        return isBackChoice;
    }

    public void setIsBackChoice(Integer isBackChoice) {
        this.isBackChoice = isBackChoice;
    }

    public Integer getExpressSheetType() {
        return expressSheetType;
    }

    public void setExpressSheetType(Integer expressSheetType) {
        this.expressSheetType = expressSheetType;
    }

    public String getShippingGeneralName() {
        return shippingGeneralName;
    }

    public void setShippingGeneralName(String shippingGeneralName) {
        this.shippingGeneralName = shippingGeneralName;
    }

    public String getShippingSpeciaName() {
        return shippingSpeciaName;
    }

    public void setShippingSpeciaName(String shippingSpeciaName) {
        this.shippingSpeciaName = shippingSpeciaName;
    }

    public String getShippingGeneralCode() {
        return shippingGeneralCode;
    }

    public void setShippingGeneralCode(String shippingGeneralCode) {
        this.shippingGeneralCode = shippingGeneralCode;
    }

    public String getShippingSpeciaCode() {
        return shippingSpeciaCode;
    }

    public void setShippingSpeciaCode(String shippingSpeciaCode) {
        this.shippingSpeciaCode = shippingSpeciaCode;
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

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getModifier_id() {
        return modifier_id;
    }

    public void setModifier_id(Integer modifier_id) {
        this.modifier_id = modifier_id;
    }

    public String getShippingWayCode() {
        return shippingWayCode;
    }

    public void setShippingWayCode(String shippingWayCode) {
        this.shippingWayCode = shippingWayCode;
    }

    public String getShippingWayName() {
        return shippingWayName;
    }

    public void setShippingWayName(String shippingWayName) {
        this.shippingWayName = shippingWayName;
    }

    public Integer getAllowedProductType() {
        return allowedProductType;
    }

    public void setAllowedProductType(Integer allowedProductType) {
        this.allowedProductType = allowedProductType;
    }
   
}
