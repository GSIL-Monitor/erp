package com.stosz.order.ext.dto;

import java.util.List;

/**
 *  销售订单出库
 */
public class OrderOutDTO {
    /**
     * 订单号
     */
    private  String orderNo;
    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 物流名称
     */
    private String shippingName;

    /**
     * 快递单号
     */
    private String shippingNo;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    private List<OrderOutItemDTO> data;

    public List<OrderOutItemDTO> getData() {
        return data;
    }

    public void setData(List<OrderOutItemDTO> data) {
        this.data = data;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
