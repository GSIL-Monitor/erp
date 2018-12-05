package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrdersRefundMethodEnum;
import com.stosz.order.ext.enums.OrdersRefundStatusEnum;
import com.stosz.order.ext.enums.OrdersRefundTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther tangtao
 * create time  2018/1/3
 */
public class OrdersRefundDTO implements Serializable {


    /**
     * 退款单编号
     */
    private String id;
    /**
     * 订单流水号
     */
    private String ordersId;

    /**
     * 网站来源
     */
    private String siteFrom;

    /**
     * 退款状态
     */
    private OrdersRefundStatusEnum refundStatusEnum;

    /**
     * 审核时间开始
     */
    private LocalDateTime auditTimeBegin;

    /**
     * 审核时间结束
     */
    private LocalDateTime auditTimeEnd;

    /**
     * 创建时间开始
     */
    private LocalDateTime createAtBegin;

    /**
     * 创建时间结束
     */
    private LocalDateTime createAtEnd;

    /**
     * 退款方式
     */
    private OrdersRefundMethodEnum refundMethodEnum;

    /**
     * 退款类型
     */
    private OrdersRefundTypeEnum refundTypeEnum;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 审核备注
     */
    private String auditMemo;

    /**
     * 审核结果（true 通过、false 驳回）
     */
    private Boolean reslult;

    /****************权限相关***************/

    /**
     * 用户权限
     *************************************/
    private Integer creatorId;

    /**
     * 区域权限
     *************************************/
    private List<Integer> zoneIds;
    /**
     * 部门权限
     *************************************/
    private List<Integer> departmentIds;


    /**
     * 起始位置，从0开始，又端 page参数计算而来
     *************************************/
    private Integer start = 0;
    /**
     * 每一页的数据量
     *************************************/
    private Integer limit = 20;

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public List<Integer> getZoneIds() {
        return zoneIds;
    }

    public void setZoneIds(List<Integer> zoneIds) {
        this.zoneIds = zoneIds;
    }

    public List<Integer> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStart() {
        return (start >= 0) ? start : 0;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getSiteFrom() {
        return siteFrom;
    }

    public void setSiteFrom(String siteFrom) {
        this.siteFrom = siteFrom;
    }

    public OrdersRefundStatusEnum getRefundStatusEnum() {
        return refundStatusEnum;
    }

    public void setRefundStatusEnum(OrdersRefundStatusEnum refundStatusEnum) {
        this.refundStatusEnum = refundStatusEnum;
    }

    public LocalDateTime getAuditTimeBegin() {
        return auditTimeBegin;
    }

    public void setAuditTimeBegin(LocalDateTime auditTimeBegin) {
        this.auditTimeBegin = auditTimeBegin;
    }

    public LocalDateTime getAuditTimeEnd() {
        return auditTimeEnd;
    }

    public void setAuditTimeEnd(LocalDateTime auditTimeEnd) {
        this.auditTimeEnd = auditTimeEnd;
    }

    public LocalDateTime getCreateAtBegin() {
        return createAtBegin;
    }

    public void setCreateAtBegin(LocalDateTime createAtBegin) {
        this.createAtBegin = createAtBegin;
    }

    public LocalDateTime getCreateAtEnd() {
        return createAtEnd;
    }

    public void setCreateAtEnd(LocalDateTime createAtEnd) {
        this.createAtEnd = createAtEnd;
    }

    public OrdersRefundMethodEnum getRefundMethodEnum() {
        return refundMethodEnum;
    }

    public void setRefundMethodEnum(OrdersRefundMethodEnum refundMethodEnum) {
        this.refundMethodEnum = refundMethodEnum;
    }

    public OrdersRefundTypeEnum getRefundTypeEnum() {
        return refundTypeEnum;
    }

    public void setRefundTypeEnum(OrdersRefundTypeEnum refundTypeEnum) {
        this.refundTypeEnum = refundTypeEnum;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getAuditMemo() {
        return auditMemo;
    }

    public void setAuditMemo(String auditMemo) {
        this.auditMemo = auditMemo;
    }

    public Boolean getReslult() {
        return reslult;
    }

    public void setReslult(Boolean reslult) {
        this.reslult = reslult;
    }
}
