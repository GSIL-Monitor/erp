package com.stosz.purchase.ext.model.finance;


import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.purchase.ext.enums.BillTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:付款流水表
 * @Created Time:
 * @Update Time:2017/12/28 10:21
 */
public class PaymentItem extends AbstractParamEntity implements ITable, Serializable {

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
    private Integer changeBillItem;//发生单据详情id
    @DBColumn
    private String partner;//合作伙伴
    @DBColumn
    private Integer partnerId;//合作伙伴Id
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

    @Override
    public Integer getId() {
        return id;
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

    public Integer getChangeBillItem() {
        return changeBillItem;
    }

    public void setChangeBillItem(Integer changeBillItem) {
        this.changeBillItem = changeBillItem;
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

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String getTable() {
        return "payment_item";
    }
}
