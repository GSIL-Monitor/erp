package com.stosz.product.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.enums.TypeEnum;

import java.time.LocalDateTime;

/**
 * 合作伙伴实体类
 *
 * @author minxiaolong
 * @create 2017-12-29 10:48
 **/
public class Partner extends AbstractParamEntity implements IFsmInstance, ITable {

    private static final long serialVersionUID = 5384525348997626392L;

    @DBColumn
    private Integer id;

    @DBColumn
    private String no;

    @DBColumn
    private TypeEnum typeEnum;

    @DBColumn
    private String name;

    @DBColumn
    private String contact;

    @DBColumn
    private String mobile;

    @DBColumn
    private String address;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    @DBColumn
    private LocalDateTime createAt;

    @DBColumn
    private LocalDateTime updateAt;

    @DBColumn
    private String financialLoginid;

    @DBColumn
    private String financialAccount;

    @DBColumn
    private Integer financialAccountType;

    @DBColumn
    private String issuer;

    @DBColumn
    private Boolean usable;

    @DBColumn
    private String remark;

    @DBColumn
    private String oldId;

    @DBColumn
    private Integer settlementMethod;

    @DBColumn
    private String settlementDay;

    @Override
    public String getTable() {
        return "partner";
    }

    @Override
    public Integer getParentId() {
        return null;
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public void setState(String state) {

    }

    @Override
    public LocalDateTime getStateTime() {
        return null;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
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

    public String getFinancialLoginid() {
        return financialLoginid;
    }

    public void setFinancialLoginid(String financialLoginid) {
        this.financialLoginid = financialLoginid;
    }

    public String getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(String financialAccount) {
        this.financialAccount = financialAccount;
    }

    public Integer getFinancialAccountType() {
        return financialAccountType;
    }

    public void setFinancialAccountType(Integer financialAccountType) {
        this.financialAccountType = financialAccountType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public Integer getSettlementMethod() {
        return settlementMethod;
    }

    public void setSettlementMethod(Integer settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    public String getSettlementDay() {
        return settlementDay;
    }

    public void setSettlementDay(String settlementDay) {
        this.settlementDay = settlementDay;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }
}
