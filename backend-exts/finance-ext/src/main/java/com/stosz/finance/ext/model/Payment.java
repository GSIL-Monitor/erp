package com.stosz.finance.ext.model;

import com.stosz.finance.ext.model.base.BaseModel;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:付款表
 * @Created Time:
 * @Update Time:2017/12/28 10:11
 */
public class Payment extends BaseModel implements ITable, Serializable {

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
    private String payUser;//付款人
    @DBColumn
    private LocalDateTime payTime;//付款时间
    @DBColumn
    private BigDecimal amount;//金额
    @DBColumn
    private Integer changeUserId;
    @DBColumn
    private String changeUserName;

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

    @Override
    public String getTable() {
        return "payment";
    }
}
