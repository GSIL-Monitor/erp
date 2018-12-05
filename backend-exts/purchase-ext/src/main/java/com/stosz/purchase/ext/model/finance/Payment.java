package com.stosz.purchase.ext.model.finance;


import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.purchase.ext.enums.PayableStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:付款表
 * @Created Time:
 * @Update Time:2017/12/28 10:11
 */
public class Payment extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;//主键id
    @DBColumn
    private Integer payType;//付款类型
    @DBColumn
    private String partnerIssuer;//账户发行方
    @DBColumn
    private String partnerBank;//账户开户行
    @DBColumn
    private String partnerAccount;//账户编码
    @DBColumn
    private String partner;//收款人
    @DBColumn
    private String partnerId;//收款人
    @DBColumn
    private Integer payUserId;//付款人
    @DBColumn
    private String payUser;//付款人
    @DBColumn
    private LocalDateTime payTime;//付款时间
    @DBColumn
    private BigDecimal amount;//金额
    @DBColumn
    private Integer buyerId;
    @DBColumn
    private String buyerName;
    @DBColumn
    private String buyerAccount;
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

    private transient PayableStateEnum payableStateEnum;//生成类型


    public void setPayableStateEnum(PayableStateEnum payableStateEnum) {
        this.payableStateEnum = payableStateEnum;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPartnerIssuer() {
        return partnerIssuer;
    }

    public void setPartnerIssuer(String partnerIssuer) {
        this.partnerIssuer = partnerIssuer;
    }

    public String getPartnerBank() {
        return partnerBank;
    }

    public void setPartnerBank(String partnerBank) {
        this.partnerBank = partnerBank;
    }

    public String getPartnerAccount() {
        return partnerAccount;
    }

    public void setPartnerAccount(String partnerAccount) {
        this.partnerAccount = partnerAccount;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Integer getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(Integer payUserId) {
        this.payUserId = payUserId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public String getTable() {
        return "payment";
    }
}
