package com.stosz.finance.ext.model;


import com.stosz.finance.ext.enums.BillTypeEnum;
import com.stosz.finance.ext.enums.GenerateTypeEnum;
import com.stosz.finance.ext.model.base.BaseModel;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:应付款表
 * @Created Time:
 * @Update Time:2017/12/28 10:08
 */
public class Payable extends BaseModel implements ITable, Serializable {

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
    private Integer changeUserId;//发生单据负责人id
    @DBColumn
    private String changeUserName;//发生单据负责人
    @DBColumn
    private String partner;//收款方
    @DBColumn
    private String payUser;//支付人
    @DBColumn
    private LocalDateTime payTime;//支付时间
    @DBColumn
    private Integer platId;//渠道id
    @DBColumn
    private String platOrdersNo;//渠道订单号
    @DBColumn
    private String platName;//渠道名称

    private transient Integer hasPaymentId;//是否生成状态（生成付款单号）0-否 1-是

    private transient GenerateTypeEnum generateTypeEnum;//生成类型

    private transient BillTypeEnum goalBillTypeEnum;//目标单据类型

    private transient BillTypeEnum changeBillTypeEnum;//发生单据类型

    public GenerateTypeEnum getGenerateTypeEnum() {
        return (null == paymentId) ? GenerateTypeEnum.isNull : GenerateTypeEnum.notNull;
    }

    public void setGenerateTypeEnum(GenerateTypeEnum generateTypeEnum) {
        this.generateTypeEnum = generateTypeEnum;
    }

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

    public Integer getHasPaymentId() {
        return hasPaymentId;
    }

    public void setHasPaymentId(Integer hasPaymentId) {
        this.hasPaymentId = hasPaymentId;
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

    public BigDecimal getPayAmount() {//空则使用amount
        return payAmount == null ? amount : payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getChangeUserId() {
        return changeUserId;
    }

    public void setChangeUserId(Integer changeUserId) {
        this.changeUserId = changeUserId;
    }

    public String getChangeUserName() {
        return changeUserName;
    }

    public void setChangeUserName(String changeUserName) {
        this.changeUserName = changeUserName;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getPayUser() {
        return payUser;
    }

    public void setPayUser(String payUser) {
        this.payUser = payUser;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
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

    @Override
    public String getTable() {
        return "payable";
    }
}
