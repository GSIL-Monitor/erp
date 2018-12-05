package com.stosz.order.ext.model;

import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 客服区域权限实体类
 */
public class UserZone  extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Integer	id ;
    /**客服的id*/
    @DBColumn
    private	Integer	userId;
    /**区域id*/
    @DBColumn
    private	Integer	zoneId;
    /**可用状态*/
    @DBColumn
    private UseableEnum useStatus = UseableEnum.other;
    /**创建时间*/
    @DBColumn
    private	java.time.LocalDateTime	createAt;
    /**更新时间*/
    @DBColumn
    private	java.time.LocalDateTime	updateAt;
    /**创建者id*/
    @DBColumn
    private	Integer	creatorId;
    /**创建人*/
    @DBColumn
    private	String	creator;
    /**客服人员名称*/
    @DBColumn
    private	String	userName;
    /**区域名称*/
    @DBColumn
    private	String	zoneName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public UseableEnum getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(UseableEnum useStatus) {
        this.useStatus = useStatus;
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

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    @Override
    public String toString() {
        return "UserZone{" +
                "id=" + id +
                ", userId=" + userId +
                ", zoneId=" + zoneId +
                ", useStatus=" + useStatus +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", creatorId=" + creatorId +
                ", creator='" + creator + '\'' +
                ", userName='" + userName + '\'' +
                ", zoneName='" + zoneName + '\'' +
                '}';
    }

    @Override
    public String getTable() {
        return "user_zone";
    }

}
