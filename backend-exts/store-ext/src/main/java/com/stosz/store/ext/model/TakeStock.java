package com.stosz.store.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.store.ext.enums.TakeStockStateEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 盘点主表
 * @Created Time:2017/12/22 18:16
 * @Update Time:
 */
public class TakeStock extends AbstractParamEntity implements ITable, IFsmInstance {

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
     * 盘盈数
     */
    @DBColumn
    private Integer inventoryProfitQty;

    /**
     * 盘亏数
     */
    @DBColumn
    private Integer inventoryLossesQty;

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
     * 状态
     */
    @DBColumn
    private String state;

    @DBColumn
    private LocalDateTime stateTime;
    /**
     * 审核人
     */
    @DBColumn
    private String approver;
    /**
     * 更新时间
     */
    @DBColumn
    private LocalDateTime updateAt;

    /**
     * 备注
     */
    @DBColumn
    private String memo;
    /**
     * 类型 0-盘盈 1-盘亏
     */
    @DBColumn
    private Integer takeStockType;

    private TakeStockStateEnum takeStockStateEnum;

    public TakeStockStateEnum getTakeStockStateEnum() {
        return state == null ? null : TakeStockStateEnum.fromName(state);
    }

    public void setTakeStockStateEnum(TakeStockStateEnum takeStockStateEnum) {
        this.takeStockStateEnum = takeStockStateEnum;
    }

    @Override
    public String getTable() {
        return "take_stock";
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
    public void setId(Integer id) {
        this.id = id;
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

    public Integer getInventoryProfitQty() {
        return inventoryProfitQty;
    }

    public void setInventoryProfitQty(Integer inventoryProfitQty) {
        this.inventoryProfitQty = inventoryProfitQty;
    }

    public Integer getInventoryLossesQty() {
        return inventoryLossesQty;
    }

    public void setInventoryLossesQty(Integer inventoryLossesQty) {
        this.inventoryLossesQty = inventoryLossesQty;
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
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getTakeStockType() {
        return takeStockType;
    }

    public void setTakeStockType(Integer takeStockType) {
        this.takeStockType = takeStockType;
    }
}
