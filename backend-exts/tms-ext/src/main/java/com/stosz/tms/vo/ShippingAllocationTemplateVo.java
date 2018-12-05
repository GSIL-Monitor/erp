package com.stosz.tms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stosz.plat.model.DBColumn;

import java.util.Date;

public class ShippingAllocationTemplateVo {

    
    private Integer id;
    
    private Integer shippingWayId;

    private String shippingWayName;

    private Integer generalCargoNum;
    
    private Integer specialCargoNum;
    
    private Integer cargoNum;
    
    private Integer wmsId;
    
    private String wmsName;

    private Integer sorted;
    
    private Integer eachSpecialNum;
    
    private Integer eachGeneralNum;
    
    private Integer eachCargoNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date updateAt;
    
    private String creator;
    
    private Integer creatorId;

    private Integer enable;

    private String modifier;

    private Integer modifierId;

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
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

    public String getShippingWayName() {
        return shippingWayName;
    }

    public void setShippingWayName(String shippingWayName) {
        this.shippingWayName = shippingWayName;
    }

    public Integer getGeneralCargoNum() {
        return generalCargoNum;
    }

    public void setGeneralCargoNum(Integer generalCargoNum) {
        this.generalCargoNum = generalCargoNum;
    }

    public Integer getSpecialCargoNum() {
        return specialCargoNum;
    }

    public void setSpecialCargoNum(Integer specialCargoNum) {
        this.specialCargoNum = specialCargoNum;
    }

    public Integer getCargoNum() {
        return cargoNum;
    }

    public void setCargoNum(Integer cargoNum) {
        this.cargoNum = cargoNum;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public Integer getSorted() {
        return sorted;
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public Integer getEachSpecialNum() {
        return eachSpecialNum;
    }

    public void setEachSpecialNum(Integer eachSpecialNum) {
        this.eachSpecialNum = eachSpecialNum;
    }

    public Integer getEachGeneralNum() {
        return eachGeneralNum;
    }

    public void setEachGeneralNum(Integer eachGeneralNum) {
        this.eachGeneralNum = eachGeneralNum;
    }

    public Integer getEachCargoNum() {
        return eachCargoNum;
    }

    public void setEachCargoNum(Integer eachCargoNum) {
        this.eachCargoNum = eachCargoNum;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
