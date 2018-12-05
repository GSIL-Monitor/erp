package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class Stock extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;
    @DBColumn
    private Integer wmsId;
    @DBColumn
    private Integer deptId;
    @DBColumn
    private String deptNo;
    @DBColumn
    private String deptName;
    @DBColumn
    private String spu;
    @DBColumn
    private String sku;
    @DBColumn
    private Integer instockQty;
    @DBColumn
    private Integer usableQty;
    @DBColumn
    private Integer occupyQty;
    @DBColumn
    private Integer intransitQty;
    @DBColumn
    private Long version;
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private LocalDateTime updateAt;

    /**
     * 仓库名
     */
    private transient String wmsName;

    public Stock(){}

    public Stock(Integer wmsId,Integer deptId,String sku) {
        this.wmsId=wmsId;
        this.deptId=deptId;
        this.sku=sku;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getInstockQty() {
        return instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public Integer getUsableQty() {
        return usableQty;
    }

    public void setUsableQty(Integer usableQty) {
        this.usableQty = usableQty;
    }

    public Integer getOccupyQty() {
        return occupyQty;
    }

    public void setOccupyQty(Integer occupyQty) {
        this.occupyQty = occupyQty;
    }

    public Integer getIntransitQty() {
        return intransitQty;
    }

    public void setIntransitQty(Integer intransitQty) {
        this.intransitQty = intransitQty;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    @Override
    public String getTable() {
        return "stock";
    }

}
