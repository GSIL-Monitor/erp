package com.stosz.order.service.outsystem.logistics.model;

/**
 * 
 * 订单推送物流会用到的客户实体
 * @author liusl
 */
public class CustomerInfo {
   private String first_name;   //客户名1 必填
   private String last_name;   //  客户名2 非必填
   private String tel;   //   客户电话 必填 
   private String email ;   //  客户电子邮箱 非必填
   private Integer id_zone = new Integer(0);    //   客户地区 必填
   private String  country ;   //  客户国家 必填
   private String province;   //   客户省份 必填
   private String city ;   //  客户城市 必填
   private String area ;   //  客户区域 非必填
   private String address ;   //  客户地址 必填
   private String zipcode ;   //  客户邮编 必填

public String getFirst_name() {
    return first_name;
}

public void setFirst_name(String first_name) {
    this.first_name = first_name;
}

public String getLast_name() {
    return last_name;
}

public void setLast_name(String last_name) {
    this.last_name = last_name;
}

public String getTel() {
    return tel;
}

public void setTel(String tel) {
    this.tel = tel;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}



public Integer getId_zone() {
    return id_zone;
}


public void setId_zone(Integer id_zone) {
    this.id_zone = id_zone;
}

public String getCountry() {
    return country;
}

public void setCountry(String country) {
    this.country = country;
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
