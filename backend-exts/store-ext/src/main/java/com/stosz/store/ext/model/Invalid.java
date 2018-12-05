package com.stosz.store.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.store.ext.enums.CalculateTypeEnum;
import com.stosz.store.ext.enums.InvalidStateEnum;
import com.stosz.store.ext.enums.InvalidTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 报废主表
 * @Created Time:2017/12/22 18:16
 * @Update Time:
 */
public class Invalid extends AbstractParamEntity implements ITable, IFsmInstance {

    @DBColumn
    private Integer id;
    /**
     * 仓库id
     */
    @DBColumn
    private Integer wmsId;
    /**
     * 仓库名称
     */
    @DBColumn
    private String stockName;

    /**
     * 报废数
     */
    @DBColumn
    private Integer qty;

    /**
     * 报废类型  0-破损 1-批处理
     */
    @DBColumn
    private Integer invalidType;

    /**
     * 费用计算类型  0-按包裹数平均算入归属部门 1-按重量平均算入归属部门
     */
    @DBColumn
    private Integer calculateType;

    @DBColumn
    private String memo;

    /**
     * 报废总价
     */
    @DBColumn
    private BigDecimal invalidTotal;

    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;
    /**
     * 更新时间
     */
    @DBColumn
    private LocalDateTime updateAt;

    /**
     * 状态
     */
    @DBColumn
    private String state;

    @DBColumn
    private LocalDateTime stateTime;

    private transient InvalidTypeEnum invalidTypeEnum;

    private transient CalculateTypeEnum calculateTypeEnum;

    private transient InvalidStateEnum invalidStateEnum;

    private transient String tracks; //导入运单

    public String getTracks() {
        return tracks;
    }

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

    public InvalidStateEnum getInvalidStateEnum() {
        return InvalidStateEnum.fromName(state);
    }

    public void setInvalidStateEnum(InvalidStateEnum invalidStateEnum) {
        this.invalidStateEnum = invalidStateEnum;
    }

    public InvalidTypeEnum getInvalidTypeEnum() {
        return invalidType == null ? null : InvalidTypeEnum.fromId(invalidType);
    }

    public void setInvalidTypeEnum(InvalidTypeEnum invalidTypeEnum) {
        this.invalidTypeEnum = invalidTypeEnum;
    }

    public Integer getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(Integer calculateType) {
        this.calculateType = calculateType;
    }

    public CalculateTypeEnum getCalculateTypeEnum() {
        return calculateType == null ? null : CalculateTypeEnum.fromId(calculateType);
    }

    public void setCalculateTypeEnum(CalculateTypeEnum calculateTypeEnum) {
        this.calculateTypeEnum = calculateTypeEnum;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return null;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public LocalDateTime getStateTime() {
        return this.stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getInvalidType() {
        return invalidType;
    }

    public void setInvalidType(Integer invalidType) {
        this.invalidType = invalidType;
    }

    public BigDecimal getInvalidTotal() {
        return invalidTotal;
    }

    public void setInvalidTotal(BigDecimal invalidTotal) {
        this.invalidTotal = invalidTotal;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String getTable() {
        return "invalid";
    }

}
