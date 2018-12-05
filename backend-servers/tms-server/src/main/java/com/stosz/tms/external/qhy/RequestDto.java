package com.stosz.tms.external.qhy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class RequestDto implements Serializable{
    private String reference_no;//string  Require 客户参考号
    private String shipping_method;//string  Require 配送方式
    private String country_code;//string  Require 收件人国家二字码
    private Float order_weight;//float  Require 订单重量，单位 KG，最多 3 位小数
    private Integer order_pieces;//int  Require 外包装件数
    private String mail_cargo_type;//包裹申报种类
    private Float insurance_value;//float  Option  投保金额，默认 RMB
    private Integer length;//int Require  包裹长
    private Integer width;//int Require  包裹宽
    private Integer height;//int Require  包裹高
    private Integer is_return;//Int  Require 是否退回,包裹无人签收时是否退回，1-退 回，0-不退回
    private Consignee consignee;//收件人信息
    private Shipper shipper;//发件人信息
    private List<ItemArr> itemArr;//海关申报信息

    public String getMail_cargo_type() {
        return mail_cargo_type;
    }

    public void setMail_cargo_type(String mail_cargo_type) {
        this.mail_cargo_type = mail_cargo_type;
    }

    public String getReference_no() {
        return reference_no;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Float getOrder_weight() {
        return order_weight;
    }

    public void setOrder_weight(Float order_weight) {
        this.order_weight = order_weight;
    }

    public Integer getOrder_pieces() {
        return order_pieces;
    }

    public void setOrder_pieces(Integer order_pieces) {
        this.order_pieces = order_pieces;
    }

    public Float getInsurance_value() {
        return insurance_value;
    }

    public void setInsurance_value(Float insurance_value) {
        this.insurance_value = insurance_value;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getIs_return() {
        return is_return;
    }

    public void setIs_return(Integer is_return) {
        this.is_return = is_return;
    }

    @JsonProperty(value="Consignee")
    public Consignee getConsignee() {
        return consignee;
    }

    public void setConsignee(Consignee consignee) {
        this.consignee = consignee;
    }

    @JsonProperty(value="Shipper")
    public Shipper getShipper() {
        return shipper;
    }

    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @JsonProperty(value="ItemArr")
    public List<ItemArr> getItemArr() {
        return itemArr;
    }

    public void setItemArr(List<ItemArr> itemArr) {
        this.itemArr = itemArr;
    }
}
