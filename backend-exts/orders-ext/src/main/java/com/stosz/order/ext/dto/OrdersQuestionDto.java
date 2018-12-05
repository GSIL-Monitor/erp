package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrderQuestionStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @auther carter
 * create time    2017-12-06
 * 问题件查询页面参数实体
 */
public class OrdersQuestionDto implements Serializable {

    /**
     * 订单号
     ***************/
    private String orderId = null;
    /**
     * 运单号
     ***************/
    private String transNo = null;
    /**
     * 录入时间开始
     ***************/
    private LocalDateTime inputTimeBegin = null;
    /**
     * 录入时间结束
     ***************/
    private LocalDateTime inputTimeEnd = null;
    /**
     * 处理时间开始
     ***************/
    private LocalDateTime handleTimeBegin = null;
    /**
     * 处理时间结束
     ***************/
    private LocalDateTime handleTimeEnd = null;
    /**
     * 区域id
     ***************/
    private Integer zoneId = null;
    /**
     * 物流公司id
     ***************/
    private Integer logisticId = null;
    /**
     * 部门id
     ***************/
    private Integer departmentId = null;
    /**
     * 处理用户的姓名
     ***************/
    private String dealUserId = null;

    /**
     * 问题件处理状态
     ***************/
    private OrderQuestionStatusEnum orderQuestionStatusEnum = null;

    /**
     * 当前页面，从1开始
     *************************************/
    private Integer pageIndex = 1;
    /**
     * 每一页的数据量
     *************************************/
    private Integer pageSize = 20;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public LocalDateTime getInputTimeBegin() {
        return inputTimeBegin;
    }

    public void setInputTimeBegin(LocalDateTime inputTimeBegin) {
        this.inputTimeBegin = inputTimeBegin;
    }

    public LocalDateTime getInputTimeEnd() {
        return inputTimeEnd;
    }

    public void setInputTimeEnd(LocalDateTime inputTimeEnd) {
        this.inputTimeEnd = inputTimeEnd;
    }

    public LocalDateTime getHandleTimeBegin() {
        return handleTimeBegin;
    }

    public void setHandleTimeBegin(LocalDateTime handleTimeBegin) {
        this.handleTimeBegin = handleTimeBegin;
    }

    public LocalDateTime getHandleTimeEnd() {
        return handleTimeEnd;
    }

    public void setHandleTimeEnd(LocalDateTime handleTimeEnd) {
        this.handleTimeEnd = handleTimeEnd;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(Integer logisticId) {
        this.logisticId = logisticId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    public Integer getPageIndex() {
        if (pageIndex < 1) pageIndex = 1;
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public OrderQuestionStatusEnum getOrderQuestionStatusEnum() {
        return orderQuestionStatusEnum;
    }

    public void setOrderQuestionStatusEnum(OrderQuestionStatusEnum orderQuestionStatusEnum) {
        this.orderQuestionStatusEnum = orderQuestionStatusEnum;
    }
}
