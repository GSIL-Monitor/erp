package com.stosz.purchase.ext.model.finance;


import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.enums.PayableStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class Payable extends AbstractParamEntity implements ITable, Serializable,IFsmInstance {

    @DBColumn
    private Integer id;//主键id
    @DBColumn
    private Integer paymentId;//付款单id
    @DBColumn
    private String goalBillNo;//目标单据编号
    @DBColumn
    private Integer goalBillType;//目标单据类型
    @DBColumn
    private String changeBillNo;//发生单据编号
    @DBColumn
    private Integer changeBillType;//发生单据类型
    @DBColumn
    private Integer quantity;//发生数量
    @DBColumn
    private BigDecimal amount;//发生金额
    @DBColumn
    private BigDecimal payAmount;//本次支付金额
    @DBColumn
    private Integer buyerId;//发生单据负责人id
    @DBColumn
    private String buyerName;//发生单据负责人
    @DBColumn
    private String buyerAccount;//发生单据负责人
    @DBColumn
    private Integer partnerId;//合作伙伴id
    @DBColumn
    private Integer platId;//渠道id
    @DBColumn
    private String platOrdersNo;//渠道订单号
    @DBColumn
    private String platName;//渠道名称
    @DBColumn
    private String state;//状态
    @DBColumn
    private LocalDateTime stateTime;//状态更改时间
    @DBColumn
    private String memo;//备注
    @DBColumn
    private LocalDateTime createAt;//创建时间
    @DBColumn
    private LocalDateTime updateAt;//修改时间

    private PayableStateEnum payableStateEnum;//应付款单的状态枚举

    private BillTypeEnum goalBillTypeEnum;//目标单据类型

    private BillTypeEnum changeBillTypeEnum;//发生单据类型

    private List<PayableItem> payableItemList;//应付款单明细

    public PayableStateEnum getPayableStateEnum() {
        return this.state == null ? null: PayableStateEnum.fromName(this.state);
    }

    public BillTypeEnum getGoalBillTypeEnum() {
        return this.goalBillType == null ? null : BillTypeEnum.fromId(goalBillType);
    }

    public BillTypeEnum getChangeBillTypeEnum() {
        return this.changeBillType == null ? null : BillTypeEnum.fromId(changeBillType);
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

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getGoalBillNo() {
        return goalBillNo;
    }

    public void setGoalBillNo(String goalBillNo) {
        this.goalBillNo = goalBillNo;
    }

    public Integer getGoalBillType() {
        return goalBillType;
    }

    public void setGoalBillType(Integer goalBillType) {
        this.goalBillType = goalBillType;
    }

    public String getChangeBillNo() {
        return changeBillNo;
    }

    public void setChangeBillNo(String changeBillNo) {
        this.changeBillNo = changeBillNo;
    }

    public Integer getChangeBillType() {
        return changeBillType;
    }

    public void setChangeBillType(Integer changeBillType) {
        this.changeBillType = changeBillType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public String getPlatOrdersNo() {
        return platOrdersNo;
    }

    public void setPlatOrdersNo(String platOrdersNo) {
        this.platOrdersNo = platOrdersNo;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public List<PayableItem> getPayableItemList() {
        return payableItemList;
    }

    public void setPayableItemList(List<PayableItem> payableItemList) {
        this.payableItemList = payableItemList;
    }

    @Override
    public String getTable() {
        return "payable";
    }
}
