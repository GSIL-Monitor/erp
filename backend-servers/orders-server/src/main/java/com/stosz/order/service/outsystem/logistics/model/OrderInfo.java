package com.stosz.order.service.outsystem.logistics.model;

import java.math.BigDecimal;

/**
 * 
 * 调用物流接口用到的实体
 * @author liusl
 */
public class OrderInfo {
    private String id_increment;//   订单号 必填
    private Integer id_department = new Integer(0); // 订单部门ID 必填
    private Integer   id_warehouse = new Integer(0); //   订单仓库ID 必填
    private Integer id_users = new Integer(0);  //  订单业务员ID 必填
    private BigDecimal price_total = new java.math.BigDecimal(0); //  订单总额 必填
    private Integer payment_method = new Integer(0);  //   支付方式 必填
    private String currency_code; //  货币单位 必填
    private String date_purchase ; // 建站下单时间 必填
    private String remark;   //  订单备注 非必填
    
    public String getId_increment() {
        return id_increment;
    }
    
    public void setId_increment(String id_increment) {
        this.id_increment = id_increment;
    }
    
    public Integer getId_department() {
        return id_department;
    }
    
    public void setId_department(Integer id_department) {
        this.id_department = id_department;
    }
    
    public Integer getId_warehouse() {
        return id_warehouse;
    }
    
    public void setId_warehouse(Integer id_warehouse) {
        this.id_warehouse = id_warehouse;
    }
    
    public Integer getId_users() {
        return id_users;
    }
    
    public void setId_users(Integer id_users) {
        this.id_users = id_users;
    }
    
    
    public BigDecimal getPrice_total() {
        return price_total;
    }

    
    public void setPrice_total(BigDecimal price_total) {
        this.price_total = price_total;
    }

    public Integer getPayment_method() {
        return payment_method;
    }
    
    public void setPayment_method(Integer payment_method) {
        this.payment_method = payment_method;
    }
    
    public String getCurrency_code() {
        return currency_code;
    }
    
    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
    
    public String getDate_purchase() {
        return date_purchase;
    }
    
    public void setDate_purchase(String date_purchase) {
        this.date_purchase = date_purchase;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
