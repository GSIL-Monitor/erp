package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:Transfer
 * @Created Time:2017/11/28 16:34
 * @Update Time:2017/11/28 16:34
 */
public class TransferItem extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;
    @DBColumn
    private Integer tranId;//调拨单id
    @DBColumn
    private Integer inDeptId;//调入部门id
    @DBColumn
    private Integer outDeptId;//调出部门id
    @DBColumn
    private String outDeptNo;
    @DBColumn
    private String outDeptName;
    @DBColumn
    private String inDeptNo;
    @DBColumn
    private String inDeptName;
    @DBColumn
    private String trackingNo;//运单号,转寄仓调出记录运单号
    @DBColumn
    private String spu;//产品spu
    @DBColumn
    private String sku;//产品sku
    @DBColumn
    private BigDecimal cost;
    @DBColumn
    private Integer expectedQty;//预计调拨数
    @DBColumn
    private Integer factOutQty;//实际出库数
    @DBColumn
    private Integer factInQty;//实际入库数
    @DBColumn
    private LocalDateTime createAt;//创建时间
    @DBColumn
    private LocalDateTime updateAt;//修改时间

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTranId() {
        return tranId;
    }

    public void setTranId(Integer tranId) {
        this.tranId = tranId;
    }

    public Integer getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(Integer inDeptId) {
        this.inDeptId = inDeptId;
    }

    public Integer getOutDeptId() {
        return outDeptId;
    }

    public void setOutDeptId(Integer outDeptId) {
        this.outDeptId = outDeptId;
    }

    public String getOutDeptNo() {
        return outDeptNo;
    }

    public void setOutDeptNo(String outDeptNo) {
        this.outDeptNo = outDeptNo;
    }

    public String getOutDeptName() {
        return outDeptName;
    }

    public void setOutDeptName(String outDeptName) {
        this.outDeptName = outDeptName;
    }

    public String getInDeptNo() {
        return inDeptNo;
    }

    public void setInDeptNo(String inDeptNo) {
        this.inDeptNo = inDeptNo;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        this.inDeptName = inDeptName;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
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

    public Integer getExpectedQty() {
        return expectedQty;
    }

    public void setExpectedQty(Integer expectedQty) {
        this.expectedQty = expectedQty;
    }

    public Integer getFactOutQty() {
        return factOutQty;
    }

    public void setFactOutQty(Integer factOutQty) {
        this.factOutQty = factOutQty;
    }

    public Integer getFactInQty() {
        return factInQty;
    }

    public void setFactInQty(Integer factInQty) {
        this.factInQty = factInQty;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String getTable() {
        return "transfer_item";
    }
}
