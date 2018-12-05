package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.PayMethodEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 属性
 */
public class OrderSkuAttribute {
    //sku
    private String sku;
    //属性组合
    private String attributeValueTitle;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getAttributeValueTitle() {
        return attributeValueTitle;
    }

    public void setAttributeValueTitle(String attributeValueTitle) {
        this.attributeValueTitle = attributeValueTitle;
    }
}
