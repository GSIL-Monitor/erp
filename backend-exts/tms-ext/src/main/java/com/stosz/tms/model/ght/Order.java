package com.stosz.tms.model.ght;

import java.math.BigDecimal;

public class Order {
    private String orderNo;//运单号

    private String cargo;//货物名称

    private Integer cargoCnt;//获取件数

    private BigDecimal weight;//重量

    private String destCont;//收货人

    private String destTel;//收货人电话

    private String destCity;//目的城市

    private String destAddr;//收货地址

    private BigDecimal cod;//代收款额

    private String sendName;//发货单位

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getCargoCnt() {
        return cargoCnt;
    }

    public void setCargoCnt(Integer cargoCnt) {
        this.cargoCnt = cargoCnt;
    }

    public String getDestCont() {
        return destCont;
    }

    public void setDestCont(String destCont) {
        this.destCont = destCont;
    }

    public String getDestTel() {
        return destTel;
    }

    public void setDestTel(String destTel) {
        this.destTel = destTel;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    public BigDecimal getCod() {
        return cod;
    }

    public void setCod(BigDecimal cod) {
        this.cod = cod;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }
}
