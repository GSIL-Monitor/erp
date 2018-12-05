package com.stosz.tms.dto;

import java.io.Serializable;

/**
  * 
  * 订单摘要新
  * @author liusl
  */
public class OrderLinkDto implements Serializable{
    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = 2581365070261493814L;
    
    
    private String firstName;//名字
    private String lastName;//姓
    private String telphone;//电话
    private String email;//邮箱
    private String province;//省
    private String city;//城市
    private String area;//区域
    private String address;//地址
    private String zipcode;//邮编
    private String country;//国家
    private Long customerId;//客户ID
    private String countryCode;//国家编码
    private String companyName;//公司名称

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
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
    
    
    
}
