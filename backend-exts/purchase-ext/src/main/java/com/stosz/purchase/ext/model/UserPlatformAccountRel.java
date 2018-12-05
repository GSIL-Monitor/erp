package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserPlatformAccountRel extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer state = 0;
    @DBColumn
    private int platId;
    @DBColumn
    private int id;

    private String name;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @DBColumn
    private Integer platAccountId;
    @DBColumn
    private String buyer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @DBColumn
    private Integer buyerId;

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getPlatAccountId() {
        return platAccountId;
    }

    public void setPlatAccountId(Integer platAccountId) {
        this.platAccountId = platAccountId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public int getPlatId() {
        return platId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
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

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Integer getParentId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTable() {
        return "user_platform_account_rel";
    }
}
