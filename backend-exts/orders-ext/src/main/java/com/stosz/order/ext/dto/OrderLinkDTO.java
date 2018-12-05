package com.stosz.order.ext.dto;

import java.io.Serializable;

/**
  * 
  * 订单摘要新
  * @author liusl
  */
public class OrderLinkDTO implements Serializable{
    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = 2581365070261493814L;
    private Long linkId = new Long(0);//联系人ID
    private String firstName;//名字
    private String lastName;//姓
    private String telphone;//电话
    private String email;//邮箱
    private String province;//省
    private String getCode;//原验证码
    private String inputCode;//验证码
    private String codeType;//验证码验证结果
    private String city;//城市
    private String area;//区域
    private String address;//地址
    private String zipcode;//邮编
    private String customerMeassage;//买家留言

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Long getLinkId() {
        return linkId;
    }

    
    public void setLinkId(Long linkId) {
        this.linkId = linkId;
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
    
    public String getTelphone() {
        return telphone;
    }
    
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getGetCode() {
        return getCode;
    }
    
    public void setGetCode(String getCode) {
        this.getCode = getCode;
    }
    
    public String getInputCode() {
        return inputCode;
    }
    
    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
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
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public String getCustomerMeassage() {
        return customerMeassage;
    }
    
    public void setCustomerMeassage(String customerMeassage) {
        this.customerMeassage = customerMeassage;
    }
    
    
}
