package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:Invoicing
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class SettlementMonth extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;//主键id
    @DBColumn
    private Integer planId;//排程表id
    @DBColumn
    private Integer wmsId;//仓库id
    @DBColumn
    private Integer deptId;//部门id
    @DBColumn
    private String deptNo;//部门no
    @DBColumn
    private String deptName;//部门名称
    @DBColumn
    private String spu;//产品sku
    @DBColumn
    private String sku;//产品sku
    @DBColumn
    private Integer beginQty;//期初数量
    @DBColumn
    private Integer endQty;//期末数量
    @DBColumn
    private BigDecimal beginAmount;//期初金额
    @DBColumn
    private BigDecimal endAmount;//期末金额
    @DBColumn
    private BigDecimal cost;//成本价
    @DBColumn
    private Integer sellQty;//销售数量
    @DBColumn
    private BigDecimal sellAmount;//销售金额
    @DBColumn
    private Integer purchaseQty;//采购数量
    @DBColumn
    private BigDecimal purchaseAmount;//采购金额
    @DBColumn
    private Integer transferOutQty;//调出数量
    @DBColumn
    private BigDecimal transferOutAmount;//调拨出库数量
    @DBColumn
    private Integer transferInQty;//调入数量
    @DBColumn
    private BigDecimal transferInAmount;//调拨入库金额
    @DBColumn
    private Integer otherOutQty;//其他出库数量
    @DBColumn
    private BigDecimal otherOutAmount;//其他出库金额
    @DBColumn
    private Integer otherInQty;//其他入库数量
    @DBColumn
    private BigDecimal otherInAmount;//其他入库金额
    @DBColumn
    private LocalDateTime createAt;//创建时间
    @DBColumn
    private LocalDateTime updateAt;//更新时间

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public Integer getBeginQty() {
        return beginQty;
    }

    public void setBeginQty(Integer beginQty) {
        this.beginQty = beginQty;
    }

    public Integer getEndQty() {
        return endQty;
    }

    public void setEndQty(Integer endQty) {
        this.endQty = endQty;
    }

    public BigDecimal getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(BigDecimal beginAmount) {
        this.beginAmount = beginAmount;
    }

    public BigDecimal getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(BigDecimal endAmount) {
        this.endAmount = endAmount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getSellQty() {
        return sellQty;
    }

    public void setSellQty(Integer sellQty) {
        this.sellQty = sellQty;
    }

    public BigDecimal getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(BigDecimal sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Integer getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Integer getTransferOutQty() {
        return transferOutQty;
    }

    public void setTransferOutQty(Integer transferOutQty) {
        this.transferOutQty = transferOutQty;
    }

    public BigDecimal getTransferOutAmount() {
        return transferOutAmount;
    }

    public void setTransferOutAmount(BigDecimal transferOutAmount) {
        this.transferOutAmount = transferOutAmount;
    }

    public Integer getTransferInQty() {
        return transferInQty;
    }

    public void setTransferInQty(Integer transferInQty) {
        this.transferInQty = transferInQty;
    }

    public BigDecimal getTransferInAmount() {
        return transferInAmount;
    }

    public void setTransferInAmount(BigDecimal transferInAmount) {
        this.transferInAmount = transferInAmount;
    }

    public Integer getOtherOutQty() {
        return otherOutQty;
    }

    public void setOtherOutQty(Integer otherOutQty) {
        this.otherOutQty = otherOutQty;
    }

    public BigDecimal getOtherOutAmount() {
        return otherOutAmount;
    }

    public void setOtherOutAmount(BigDecimal otherOutAmount) {
        this.otherOutAmount = otherOutAmount;
    }

    public Integer getOtherInQty() {
        return otherInQty;
    }

    public void setOtherInQty(Integer otherInQty) {
        this.otherInQty = otherInQty;
    }

    public BigDecimal getOtherInAmount() {
        return otherInAmount;
    }

    public void setOtherInAmount(BigDecimal otherInAmount) {
        this.otherInAmount = otherInAmount;
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
        return "settlement_month";
    }
}