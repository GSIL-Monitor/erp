package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 老erp的库存信息实体，对应老erp的erp_warehouse_product表
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
public class OldErpStock extends AbstractParamEntity implements Serializable, ITable {

    @DBColumn
    private Integer id;
    @DBColumn
    private Integer zoneId;
    @DBColumn
    private Integer productId;
    @DBColumn
    private Integer departmentId;
    @DBColumn
    private Integer stock;
    @DBColumn
    private Integer qtyRoad;
    @DBColumn
    private Integer qtyPreout;

    private LocalDateTime lastOrderAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getLastOrderAt() {
        return lastOrderAt;
    }

    public void setLastOrderAt(LocalDateTime lastOrderAt) {
        this.lastOrderAt = lastOrderAt;
    }

    public Integer getQtyRoad() {
        return qtyRoad;
    }

    public void setQtyRoad(Integer qtyRoad) {
        this.qtyRoad = qtyRoad;
    }

    public Integer getQtyPreout() {
        return qtyPreout;
    }

    public void setQtyPreout(Integer qtyPreout) {
        this.qtyPreout = qtyPreout;
    }

    @Override
    public String getTable() {
        return "erp_warehouse_product";
    }
}
