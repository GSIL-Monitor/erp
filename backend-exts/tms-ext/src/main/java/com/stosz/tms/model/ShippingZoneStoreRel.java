package com.stosz.tms.model;

import java.util.Date;
import java.util.List;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

/**
 * 物流区域仓库表
 * @author feiheping
 * @version [1.0,2017年12月16日]
 */
public class ShippingZoneStoreRel extends AbstractParamEntity implements ITable {

    @DBColumn
    private Integer id;

    @DBColumn
    private Integer shippingWayId;

    @DBColumn
    private String zoneName;

    @DBColumn
    private Integer zoneId;

    @DBColumn
    private Integer allowedProductType;

    @DBColumn
    private Integer wmsId;

    @DBColumn
    private String wmsName;

    @DBColumn
    private Integer enable;

    @DBColumn
    private Date createAt;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    private String shippingWayCode;

    private List<Integer> zoneIdList;

    private List<Integer> wmsIdList;

    private List<Integer> shippingWayIdList;


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

    public String getShippingWayCode() {
        return shippingWayCode;
    }

    public void setShippingWayCode(String shippingWayCode) {
        this.shippingWayCode = shippingWayCode;
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

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName == null ? null : zoneName.trim();
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getAllowedProductType() {
        return allowedProductType;
    }

    public void setAllowedProductType(Integer allowedProductType) {
        this.allowedProductType = allowedProductType;
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

	@Override
	public String getTable() {
		return "shipping_zone_store_rel";
	}
}