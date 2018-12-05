package com.stosz.purchase.ext.model.finance;

import com.stosz.product.ext.model.Partner;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.enums.PayableStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FinancePay implements Serializable {

    private Integer id;
    private Integer buyerId;//发生单据负责人id
    private String buyerName;//发生单据负责人
    private String buyerAccount;//购买账号
    private String platOrdersNo;//渠道订单号
    private Integer hasPaymentId;//是否生成状态（生成付款单号）0-否 1-是
    private PayableStateEnum payableStateEnum;//是否生成状态（生成付款单号）0-否 1-是
    private BillTypeEnum goalBillTypeEnum;//目标单据类型
    private BillTypeEnum changeBillTypeEnum;//发生单据类型
    private Integer paymentId;//付款单id
    private String changeBillNo;//发生单据编号
    private Integer changeBillType;//发生单据类型
    private String goalBillNo;//目标单据编号
    private Integer goalBillType;//目标单据类型
    private LocalDateTime createAt;//创建时间
    private String productTitle;// 产品标题
    private String mainImageUrl;// 产品图片
    private Partner partner;//收款方
    private Integer partnerId;//收款方id
    private Integer platId;//渠道id
    private String platName;//渠道名称
    private BigDecimal amount;//发生金额
    private BigDecimal payAmount;//本次支付金额
    private String memo;//备注
    private List<FinanceItemSku> financeItemSkus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPlatOrdersNo() {
        return platOrdersNo;
    }

    public void setPlatOrdersNo(String platOrdersNo) {
        this.platOrdersNo = platOrdersNo;
    }

    public Integer getHasPaymentId() {
        return hasPaymentId;
    }

    public void setHasPaymentId(Integer hasPaymentId) {
        this.hasPaymentId = hasPaymentId;
    }

    public PayableStateEnum getPayableStateEnum() {
        return payableStateEnum;
    }

    public void setPayableStateEnum(PayableStateEnum payableStateEnum) {
        this.payableStateEnum = payableStateEnum;
    }

    public BillTypeEnum getGoalBillTypeEnum() {
        return goalBillTypeEnum;
    }

    public void setGoalBillTypeEnum(BillTypeEnum goalBillTypeEnum) {
        this.goalBillTypeEnum = goalBillTypeEnum;
    }

    public BillTypeEnum getChangeBillTypeEnum() {
        return changeBillTypeEnum;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<FinanceItemSku> getFinanceItemSkus() {
        return financeItemSkus;
    }

    public void setFinanceItemSkus(List<FinanceItemSku> financeItemSkus) {
        this.financeItemSkus = financeItemSkus;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String toString() {
        return "FinancePay{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", buyerName='" + buyerName + '\'' +
                ", buyerAccount='" + buyerAccount + '\'' +
                ", platOrdersNo='" + platOrdersNo + '\'' +
                ", hasPaymentId=" + hasPaymentId +
                ", payableStateEnum=" + payableStateEnum +
                ", goalBillTypeEnum=" + goalBillTypeEnum +
                ", changeBillTypeEnum=" + changeBillTypeEnum +
                ", paymentId=" + paymentId +
                ", changeBillNo='" + changeBillNo + '\'' +
                ", changeBillType=" + changeBillType +
                ", goalBillNo='" + goalBillNo + '\'' +
                ", goalBillType=" + goalBillType +
                ", createAt=" + createAt +
                ", productTitle='" + productTitle + '\'' +
                ", mainImageUrl='" + mainImageUrl + '\'' +
                ", partner=" + partner +
                ", partnerId=" + partnerId +
                ", platId=" + platId +
                ", platName='" + platName + '\'' +
                ", amount=" + amount +
                ", payAmount=" + payAmount +
                ", memo='" + memo + '\'' +
                ", financeItemSkus=" + financeItemSkus +
                '}';
    }
}
