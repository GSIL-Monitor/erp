package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/5]
 */
public class OldErpProductZone extends AbstractParamEntity implements ITable, Serializable {

    private Integer id;
    private Integer productId;
    private Integer departmentId;
    private Integer zoneId;
    private Boolean status;
    private LocalDateTime createdAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getTable() {
        return null;
    }
}
