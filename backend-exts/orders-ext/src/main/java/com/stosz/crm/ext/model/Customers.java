package com.stosz.crm.ext.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stosz.crm.ext.enums.CodeTypeEnum;
import com.stosz.crm.ext.enums.CustomerCreditEnum;
import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 
 */
public class Customers extends AbstractParamEntity implements ITable, Serializable {

    /**
     * 订单编号
     */
    private Long orderId;

    @DBColumn
    private Integer id;

    /**
     * 电话
     */
    @DBColumn
    private String telphone;

    /**
     * 名字
     */
    @DBColumn
    private String firstName;

    /**
     * 姓
     */
    @DBColumn
    private String lastName;

    /**
     * 国家
     */
    @JsonIgnore
    @DBColumn
    private String country;

    /**
     * 邮箱
     */
    @DBColumn
    private String email;

    /**
     * 邮编
     */
    @DBColumn
    private String zipcode;

    /**
     * 黑名单级别
     */
    @DBColumn
    private Integer levelEnum;

    /**
     * 省份
     */
    @JsonIgnore
    @DBColumn
    private String province;

    /**
     * 城市
     */
    @JsonIgnore
    @DBColumn
    private String city;

    /**
     * 区域
     */
    @DBColumn
    private String area;

    /**
     * 地址
     */
    @DBColumn
    private String address;

    /**
     * 区域编码
     */
//    @DBColumn
    private String zoneCode;


    /**
     * 区域编号（以该项为准）
     */
    @DBColumn
    private Integer zoneId;

    /**
     * 验证码类型（0：无，1：正确：2：错误）
     */
    @DBColumn
    private Integer codeType;

    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;

    /**
     * 拒签次数
     */
    @DBColumn
    private Integer rejectQty;

    /**
     * 签收次数
     */
    @DBColumn
    private Integer acceptQty;

    /**
     * 备注
     */
    @DBColumn
    private String memo;

    /**
     * 状态
     */
    @DBColumn
    private int state;

    /**
     * 修改时间
     */
    @DBColumn
    private LocalDateTime updateAt;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    /**
     * 设置级别的方法0系统 1人工
     */
    @JsonIgnore
    @DBColumn
    private Integer setLevelEnum;

    private static final long serialVersionUID = 1L;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getLevelEnum() {
        return levelEnum;
    }

    public void setLevelEnum(Integer levelEnum) {
        this.levelEnum = levelEnum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Integer getRejectQty() {
        return rejectQty;
    }

    public void setRejectQty(Integer rejectQty) {
        this.rejectQty = rejectQty;
    }

    public Integer getAcceptQty() {
        return acceptQty;
    }

    public void setAcceptQty(Integer acceptQty) {
        this.acceptQty = acceptQty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getSetLevelEnum() {
        return setLevelEnum;
    }

    public void setSetLevelEnum(Integer setLevelEnum) {
        this.setLevelEnum = setLevelEnum;
    }


    public String getCodeTypeEnum(){
        return codeType == null ? "" : CodeTypeEnum.fromId(codeType).display();
    }

    public String getCustomerCreditEnum(){
        return levelEnum == null ? "": CustomerCreditEnum.fromId(levelEnum).display();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getStateDisplay(){
        UseableEnum use = UseableEnum.fromId(state);
        if(use !=null){
            return use.display();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Customers{" +
                "orderId=" + orderId +
                ", id=" + id +
                ", telphone='" + telphone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", levelEnum=" + levelEnum +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", zoneCode='" + zoneCode + '\'' +
                ", zoneId=" + zoneId +
                ", codeType=" + codeType +
                ", createAt=" + createAt +
                ", rejectQty=" + rejectQty +
                ", acceptQty=" + acceptQty +
                ", memo='" + memo + '\'' +
                ", state=" + state +
                ", updateAt=" + updateAt +
                ", creator='" + creator + '\'' +
                ", creatorId=" + creatorId +
                ", setLevelEnum=" + setLevelEnum +
                '}';
    }

    @Override
    public String getTable() {
        return "customer";
    }
}