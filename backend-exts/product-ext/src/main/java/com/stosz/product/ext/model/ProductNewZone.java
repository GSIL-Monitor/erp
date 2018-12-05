package com.stosz.product.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.time.LocalDateTime;

/**
 * 新品区域对照关系表
 *
 * @author Administrator
 */
public class ProductNewZone extends AbstractParamEntity implements ITable {

    @DBColumn
    private Integer id;

    @DBColumn
    private Integer productNewId;

    private String zoneCode;

    private String zoneName;

    private Integer countryId;
    @DBColumn
    private Integer zoneId;
    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @DBColumn
    private LocalDateTime updateAt;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getProductNewId() {
        return productNewId;
    }

    public void setProductNewId(Integer productNewId) {
        this.productNewId = productNewId;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public String getTable() {
        return "product_new_zone";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}