package com.stosz.finance.ext.model;

import com.stosz.finance.ext.enums.AdjustTypeEnum;
import com.stosz.finance.ext.enums.BillTypeEnum;
import com.stosz.finance.ext.model.base.BaseModel;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author:yangqinghui
 * @Description:金额调整表
 * @Created Time:
 * @Update Time:2017/12/28 10:10
 */
public class AmountAdjust extends BaseModel implements ITable, Serializable {

    @DBColumn
    private Integer paymentId;//付款单id
    @DBColumn
    private Integer partner;//收款方
    @DBColumn
    private String goalBillNo;//目标单据编号
    @DBColumn
    private Integer goalBillType;//目标单据类型
    @DBColumn
    private Integer type;//调整类型 0-付款  1-收款
    @DBColumn
    private Integer quantity;//数量
    @DBColumn
    private BigDecimal amount;//金额

    private transient AdjustTypeEnum adjustTypeEnum;//调整类型

    private transient BillTypeEnum goalBillTypeEnum;//目标单据类型

    public BillTypeEnum getGoalBillTypeEnum() {
        return BillTypeEnum.fromId(goalBillType);
    }

    public void setGoalBillTypeEnum(BillTypeEnum goalBillTypeEnum) {
        this.goalBillTypeEnum = goalBillTypeEnum;
    }

    public AdjustTypeEnum getAdjustTypeEnum() {
        return AdjustTypeEnum.fromId(type);
    }

    public void setAdjustTypeEnum(AdjustTypeEnum adjustTypeEnum) {
        this.adjustTypeEnum = adjustTypeEnum;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPartner() {
        return partner;
    }

    public void setPartner(Integer partner) {
        this.partner = partner;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public String getTable() {
        return "amount_adjust";
    }
}
