package com.stosz.finance.ext.model;

import com.stosz.finance.ext.enums.BillTypeEnum;
import com.stosz.finance.ext.model.base.BaseModel;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author:yangqinghui
 * @Description:付款流水表
 * @Created Time:
 * @Update Time:2017/12/28 10:21
 */
public class PaymentItem extends BaseModel implements ITable, Serializable {

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
    private Integer changeBillItem;//发生单据详情id
    @DBColumn
    private String partner;//合作伙伴
    @DBColumn
    private Integer deptId;//部门id
    @DBColumn
    private String deptName;//部门名称
    @DBColumn
    private String sku;//sku
    @DBColumn
    private Integer quantity;//数量
    @DBColumn
    private BigDecimal amount;//金额

    private transient BillTypeEnum goalBillTypeEnum;//目标单据类型

    private transient BillTypeEnum changeBillTypeEnum;//发生单据类型

    public BillTypeEnum getGoalBillTypeEnum() {
        return BillTypeEnum.fromId(goalBillType);
    }

    public void setGoalBillTypeEnum(BillTypeEnum goalBillTypeEnum) {
        this.goalBillTypeEnum = goalBillTypeEnum;
    }

    public BillTypeEnum getChangeBillTypeEnum() {
        return BillTypeEnum.fromId(changeBillType);
    }

    public void setChangeBillTypeEnum(BillTypeEnum changeBillTypeEnum) {
        this.changeBillTypeEnum = changeBillTypeEnum;
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

    public String getChangeBillNo() {
        return changeBillNo;
    }

    public void setChangeBillNo(String changeBillNo) {
        this.changeBillNo = changeBillNo;
    }

    public Integer getChangeBillItem() {
        return changeBillItem;
    }

    public void setChangeBillItem(Integer changeBillItem) {
        this.changeBillItem = changeBillItem;
    }

    public Integer getGoalBillType() {
        return goalBillType;
    }

    public void setGoalBillType(Integer goalBillType) {
        this.goalBillType = goalBillType;
    }

    public Integer getChangeBillType() {
        return changeBillType;
    }

    public void setChangeBillType(Integer changeBillType) {
        this.changeBillType = changeBillType;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
        return "payment_item";
    }
}
