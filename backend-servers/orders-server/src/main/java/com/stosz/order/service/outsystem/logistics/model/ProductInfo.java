package com.stosz.order.service.outsystem.logistics.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * 调用物流接口用到的实体
 * @author liusl
 */
public class ProductInfo {
    private  Integer id_product = new Integer(0); //产品ID 必填
    private  Integer id_product_sku = new Integer(0);; // SKU ID 必填
    private  String sku ; //SKU 必填
    private  String product_title; //    产品名 必填
    private  String sale_title; // 产品销售名 必填
    private  Integer qty  = new Integer(0); ; //产品数量 必填
    private  BigDecimal price; //   产品单价 必填
    private List<String> attrs; //产品属性ID 必填
    private List<String> attrs_title; //产品属性值 必填
    
    public Integer getId_product() {
        return id_product;
    }
    
    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }
    
    public Integer getId_product_sku() {
        return id_product_sku;
    }
    
    public void setId_product_sku(Integer id_product_sku) {
        this.id_product_sku = id_product_sku;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getProduct_title() {
        return product_title;
    }
    
    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }
    
    public String getSale_title() {
        return sale_title;
    }
    
    public void setSale_title(String sale_title) {
        this.sale_title = sale_title;
    }
    
    public Integer getQty() {
        return qty;
    }
    
    public void setQty(Integer qty) {
        this.qty = qty;
    }
    
    
    public BigDecimal getPrice() {
        return price;
    }

    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getAttrs() {
        return attrs;
    }
    
    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }
    
    public List<String> getAttrs_title() {
        return attrs_title;
    }
    
    public void setAttrs_title(List<String> attrs_title) {
        this.attrs_title = attrs_title;
    }
    
}
