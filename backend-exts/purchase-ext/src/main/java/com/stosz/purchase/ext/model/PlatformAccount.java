package com.stosz.purchase.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PlatformAccount extends AbstractParamEntity implements ITable,Serializable  {


    @DBColumn
    private Integer id;
    @DBColumn
    private int platId;//采购渠道id
    @DBColumn
    private String account;//买手账号
    @DBColumn
    private String creator;//创建人
    @DBColumn
    private Integer creatorId;
    @DBColumn
    private  Integer state=0;//状态
    private LocalDateTime createAt;//创建时间
    private LocalDateTime updateAt;
    private  String name;//采购渠道

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDateTime getStateTime() {
        return null;
    }

    public void setStateTime(LocalDateTime stateTime) {
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

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
    this.id=id;
    }

    @Override
    public String getTable() {
        return "platform_account";
    }
}
