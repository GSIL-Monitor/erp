package com.stosz.store.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.store.ext.enums.InvoicingTypeEnum;
import com.stosz.store.ext.enums.StockStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:Invoicing
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class Invoicing extends AbstractParamEntity implements ITable, Serializable {

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
    private String voucherNo;
    @DBColumn
    private Integer quantity;
    @DBColumn
    private BigDecimal cost;
    @DBColumn
    private Integer state;
    @DBColumn
    private BigDecimal amount;
    @DBColumn
    private Integer type;
    @DBColumn
    private Integer stockDate;
    @DBColumn
    private Integer planId;
    @DBColumn
    private LocalDateTime changeAt;
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private LocalDateTime updateAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;

    /**
     * 仓库名
     */
    private transient String wmsName;

    /**
     * 月份
     */
    private transient String planMonth;

    /**
     * 单据类型
     */
    private transient InvoicingTypeEnum invoicingTypeEnum;

    /**
     * 0:进仓 1:出仓
     */
    private transient StockStateEnum stockStateEnum;

    public Invoicing() {
    }

    public Invoicing(Integer wmsId, Integer deptId, String sku) {
        this.wmsId = wmsId;
        this.deptId = deptId;
        this.sku = sku;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public InvoicingTypeEnum getInvoicingTypeEnum() {
        return type == null ? null : InvoicingTypeEnum.fromId(type);
    }

    public void setInvoicingTypeEnum(InvoicingTypeEnum invoicingTypeEnum) {
        this.invoicingTypeEnum = invoicingTypeEnum;
    }

    public StockStateEnum getStockStateEnum() {
        return  state == null ? null : StockStateEnum.fromId(state);
    }

    public void setStockStateEnum(StockStateEnum stockStateEnum) {
        this.stockStateEnum = stockStateEnum;
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

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStockDate() {
        return stockDate;
    }

    public void setStockDate(Integer stockDate) {
        this.stockDate = stockDate;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public LocalDateTime getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(LocalDateTime changeAt) {
        this.changeAt = changeAt;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getTable() {
        return "invoicing";
    }
}