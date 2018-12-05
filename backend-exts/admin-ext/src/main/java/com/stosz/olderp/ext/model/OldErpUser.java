package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.util.Date;

public class OldErpUser extends AbstractParamEntity implements Serializable, ITable {
    private Integer id;

    private String userLogin;

    private String userPass;

    private String userNicename;

    private String userEmail;

    private String userUrl;

    private String userTel;

    private String avatar;

    private Short sex;

    private Date birthday;

    private String signature;

    private String lastLoginIp;

    private Date lastLoginTime;

    private Date createTime;

    private String userActivationKey;

    private Integer userStatus;

    private Integer score;

    private Short userType;

    private Integer coin;

    private String mobile;

    private Integer superiorUserId;

    private String belongWareId;

    private String belongZoneId;

    private Integer oaId;

    private String oaLogin;

    private String oaLastName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin == null ? null : userLogin.trim();
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass == null ? null : userPass.trim();
    }

    public String getUserNicename() {
        return userNicename;
    }

    public void setUserNicename(String userNicename) {
        this.userNicename = userNicename == null ? null : userNicename.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl == null ? null : userUrl.trim();
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserActivationKey() {
        return userActivationKey;
    }

    public void setUserActivationKey(String userActivationKey) {
        this.userActivationKey = userActivationKey == null ? null : userActivationKey.trim();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getSuperiorUserId() {
        return superiorUserId;
    }

    public void setSuperiorUserId(Integer superiorUserId) {
        this.superiorUserId = superiorUserId;
    }

    public String getBelongWareId() {
        return belongWareId;
    }

    public void setBelongWareId(String belongWareId) {
        this.belongWareId = belongWareId == null ? null : belongWareId.trim();
    }

    public String getBelongZoneId() {
        return belongZoneId;
    }

    public void setBelongZoneId(String belongZoneId) {
        this.belongZoneId = belongZoneId == null ? null : belongZoneId.trim();
    }


    public Integer getOaId() {
        return oaId;
    }

    public void setOaId(Integer oaId) {
        this.oaId = oaId;
    }

    public String getOaLogin() {
        return oaLogin;
    }

    public void setOaLogin(String oaLogin) {
        this.oaLogin = oaLogin;
    }


    public String getOaLastName() {
        return oaLastName;
    }

    public void setOaLastName(String oaLastName) {
        this.oaLastName = oaLastName;
    }


    @Override
    public String toString() {
        return "OldErpUser{" +
                "id=" + id +
                ", userLogin='" + userLogin + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userNicename='" + userNicename + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userUrl='" + userUrl + '\'' +
                ", userTel='" + userTel + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", signature='" + signature + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", createTime=" + createTime +
                ", userActivationKey='" + userActivationKey + '\'' +
                ", userStatus=" + userStatus +
                ", score=" + score +
                ", userType=" + userType +
                ", coin=" + coin +
                ", mobile='" + mobile + '\'' +
                ", superiorUserId=" + superiorUserId +
                ", belongWareId='" + belongWareId + '\'' +
                ", belongZoneId='" + belongZoneId + '\'' +
                ", oaId=" + oaId +
                ", oaLogin='" + oaLogin + '\'' +
                ", oaLastName='" + oaLastName + '\'' +
                '}';
    }

    @Override
    public String getTable() {
        return "erp_user";
    }
}