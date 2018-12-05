package com.stosz.tms.external.qhy;

import java.io.Serializable;

public class Shipper implements Serializable{
    private String shipper_name;//string  Option  发件人姓名
    private String shipper_telephone;//string  Option  发件人电话
    private String shipper_province;//省
    private String shipper_city;//string  Option  发件人城市
    private String shipper_street;//string  Option  发件人地址
    private String shipper_countrycode;//发件人国家二字码

    public String getShipper_province() {
        return shipper_province;
    }

    public void setShipper_province(String shipper_province) {
        this.shipper_province = shipper_province;
    }

    public String getShipper_countrycode() {
        return shipper_countrycode;
    }

    public void setShipper_countrycode(String shipper_countrycode) {
        this.shipper_countrycode = shipper_countrycode;
    }

    public String getShipper_name() {
        return shipper_name;
    }

    public void setShipper_name(String shipper_name) {
        this.shipper_name = shipper_name;
    }

    public String getShipper_telephone() {
        return shipper_telephone;
    }

    public void setShipper_telephone(String shipper_telephone) {
        this.shipper_telephone = shipper_telephone;
    }

    public String getShipper_city() {
        return shipper_city;
    }

    public void setShipper_city(String shipper_city) {
        this.shipper_city = shipper_city;
    }

    public String getShipper_street() {
        return shipper_street;
    }

    public void setShipper_street(String shipper_street) {
        this.shipper_street = shipper_street;
    }
}
