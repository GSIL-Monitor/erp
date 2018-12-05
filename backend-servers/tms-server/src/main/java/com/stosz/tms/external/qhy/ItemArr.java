package com.stosz.tms.external.qhy;

import java.io.Serializable;

/**
 * 海关申报信息
 */
public class ItemArr implements Serializable{
    private String invoice_enname;//string  Require  海关申报品名英文
    private String invoice_cnname;//中文海关申报品名
    private Float invoice_weight;//float  Require  申报重量，单位 KG, 精确到三位小数。
    private Integer invoice_quantity;//int  Require  数量
    private Float invoice_unitcharge;//float  Require  单价

    public String getInvoice_cnname() {
        return invoice_cnname;
    }

    public void setInvoice_cnname(String invoice_cnname) {
        this.invoice_cnname = invoice_cnname;
    }

    public String getInvoice_enname() {
        return invoice_enname;
    }

    public void setInvoice_enname(String invoice_enname) {
        this.invoice_enname = invoice_enname;
    }

    public Float getInvoice_weight() {
        return invoice_weight;
    }

    public void setInvoice_weight(Float invoice_weight) {
        this.invoice_weight = invoice_weight;
    }

    public Integer getInvoice_quantity() {
        return invoice_quantity;
    }

    public void setInvoice_quantity(Integer invoice_quantity) {
        this.invoice_quantity = invoice_quantity;
    }

    public Float getInvoice_unitcharge() {
        return invoice_unitcharge;
    }

    public void setInvoice_unitcharge(Float invoice_unitcharge) {
        this.invoice_unitcharge = invoice_unitcharge;
    }
}
