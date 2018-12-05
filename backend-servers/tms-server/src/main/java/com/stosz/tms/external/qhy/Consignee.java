package com.stosz.tms.external.qhy;

import java.io.Serializable;

public class Consignee implements Serializable{
    private String consignee_province;//收件人省
    private String consignee_city;//string  Require  收件人城市
    private String consignee_street;//string  Require  收件人地址 1
    private String consignee_name;//string  Require  收件人姓名
    private String consignee_company;//收件人公司
    private String consignee_telephone;//收件人电话
    private String consignee_postcode;//收件人邮编

    public String getConsignee_postcode() {
        return consignee_postcode;
    }

    public void setConsignee_postcode(String consignee_postcode) {
        this.consignee_postcode = consignee_postcode;
    }

    public String getConsignee_province() {
        return consignee_province;
    }

    public void setConsignee_province(String consignee_province) {
        this.consignee_province = consignee_province;
    }

    public String getConsignee_company() {
        return consignee_company;
    }

    public void setConsignee_company(String consignee_company) {
        this.consignee_company = consignee_company;
    }

    public String getConsignee_telephone() {
        return consignee_telephone;
    }

    public void setConsignee_telephone(String consignee_telephone) {
        this.consignee_telephone = consignee_telephone;
    }

    public String getConsignee_city() {
        return consignee_city;
    }

    public void setConsignee_city(String consignee_city) {
        this.consignee_city = consignee_city;
    }

    public String getConsignee_street() {
        return consignee_street;
    }

    public void setConsignee_street(String consignee_street) {
        this.consignee_street = consignee_street;
    }

    public String getConsignee_name() {
        return consignee_name;
    }

    public void setConsignee_name(String consignee_name) {
        this.consignee_name = consignee_name;
    }
}
