package com.stosz.order.ext.dto;

/**
 *  销售订单出库
 */
public class OrderOutItemDTO {
    /**
     * 订单号
     */
    private  String sku;
    /**
     * 部门ID
     */
    private Integer quanitay;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuanitay() {
        return quanitay;
    }

    public void setQuanitay(Integer quanitay) {
        this.quanitay = quanitay;
    }
}
