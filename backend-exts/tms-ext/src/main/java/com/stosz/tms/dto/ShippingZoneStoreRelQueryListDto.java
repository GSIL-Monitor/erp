package com.stosz.tms.dto;

import com.stosz.plat.model.AbstractParamEntity;

import java.util.List;

public class ShippingZoneStoreRelQueryListDto extends AbstractParamEntity {

    private String zoneIdStr;

    private Integer shippingWayId;

    private Integer allowedProductType;

    private Integer enable;

    private String wmsIdStr;

    private List<Integer> zoneIdList;

    private List<Integer> wmsIdList;

    public String getZoneIdStr() {
        return zoneIdStr;
    }

    public void setZoneIdStr(String zoneIdStr) {
        this.zoneIdStr = zoneIdStr;
    }

    public Integer getShippingWayId() {
        return shippingWayId;
    }

    public void setShippingWayId(Integer shippingWayId) {
        this.shippingWayId = shippingWayId;
    }

    public Integer getAllowedProductType() {
        return allowedProductType;
    }

    public void setAllowedProductType(Integer allowedProductType) {
        this.allowedProductType = allowedProductType;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getWmsIdStr() {
        return wmsIdStr;
    }

    public void setWmsIdStr(String wmsIdStr) {
        this.wmsIdStr = wmsIdStr;
    }

    public List<Integer> getZoneIdList() {
        return zoneIdList;
    }

    public void setZoneIdList(List<Integer> zoneIdList) {
        this.zoneIdList = zoneIdList;
    }

    public List<Integer> getWmsIdList() {
        return wmsIdList;
    }

    public void setWmsIdList(List<Integer> wmsIdList) {
        this.wmsIdList = wmsIdList;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
    }
}
