package com.stosz.tms.model.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class KerryContent implements Serializable {
    private String con_no;
    private String s_zipcode;
    private String s_mobile1;
    private String s_address;
    private String s_name;
    private String r_name;
    private String r_address;
    private String r_zipcode;
    private String r_mobile1;
    private String service_code;
    private Integer tot_pkg;
//    private String s_telephone;
//    private Long shipment_type;
//    private String s_village;
//    private String s_soi;
//    private String s_road;
//    private String s_subdistrict;
//    private String s_district;
//    private String s_province;
//    private String s_mobile2;
//    private String s_email;
//    private String s_contact;
//    private String r_village;
//    private String r_soi;
//    private String r_road;
//    private String r_telephone;


    public String getCon_no() {
        return con_no;
    }

    public void setCon_no(String con_no) {
        this.con_no = con_no;
    }

    public String getS_zipcode() {
        return s_zipcode;
    }

    public void setS_zipcode(String s_zipcode) {
        this.s_zipcode = s_zipcode;
    }

    public String getS_mobile1() {
        return s_mobile1;
    }

    public void setS_mobile1(String s_mobile1) {
        this.s_mobile1 = s_mobile1;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getR_address() {
        return r_address;
    }

    public void setR_address(String r_address) {
        this.r_address = r_address;
    }

    public String getR_zipcode() {
        return r_zipcode;
    }

    public void setR_zipcode(String r_zipcode) {
        this.r_zipcode = r_zipcode;
    }

    public String getR_mobile1() {
        return r_mobile1;
    }

    public void setR_mobile1(String r_mobile1) {
        this.r_mobile1 = r_mobile1;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public Integer getTot_pkg() {
        return tot_pkg;
    }

    public void setTot_pkg(Integer tot_pkg) {
        this.tot_pkg = tot_pkg;
    }

//    private String r_subdistrict;
//    private String r_district;
//    private String r_province;
//    private String r_mobile2;
//    private String r_email;
//    private String r_contact;
//    private String special_note;
//    private String cod_type;
//    private String declare_value;
//    private String ref_no;
//    private String merchant_id;
//    private String original_consignment_no;
//    private String action_code;
//    private Long cod_amount;


}
