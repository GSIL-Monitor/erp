package com.stosz.order.ext.model;

import com.stosz.order.ext.enums.OrdersRefundMethodEnum;
import com.stosz.order.ext.enums.OrdersRefundStatusEnum;
import com.stosz.order.ext.enums.OrdersRefundTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @auther tangtao
 * create time  2018/1/3
 */
public class OrdersRefundBillDO {

    /**
     * 退款单编号
     */
    private Integer id;
    /**
     * 订单流水号
     */
    private Integer ordersId;
    /**
     * 订单金额
     */
    private BigDecimal ordersAmount;

    /**
     * 下单时间
     */
    private LocalDateTime ordersPurchaserAt;
    /**
     * 退货拒收单号
     */
    private Integer ordersRamBillId;

    /**
     * 退款方式
     */
    private OrdersRefundMethodEnum refundMethodEnum;
    /**
     * 退款状态
     */
    private OrdersRefundStatusEnum refundStatusEnum;
    /**
     * 退款类型
     */
    private OrdersRefundTypeEnum refundTypeEnum;
    /**
     * 币种
     */
    private String currencyCode;
    /**
     * 退款金额
     */
    private BigDecimal refundAmount;
    /**
     * 审核备注
     */
    private String auditMemo;
    /**
     * 网站来源
     */
    private String siteFrom;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 订单下单时间
     */
    private LocalDateTime ordersPurchaseAt;
    /**
     * 审核人id
     */
    private Integer auditUserId;
    /**
     * 审核人
     */
    private String auditUserName;
    /**
     * 退款流水号
     */
    private String refundSerialNumber;


    /**
     * 订单付款流水号
     */
    private String ordersPayNo = "";

    /**
     * 退款物流企业名称
     */
    private String logisticName;


    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public BigDecimal getOrdersAmount() {
        return ordersAmount;
    }

    public void setOrdersAmount(BigDecimal ordersAmount) {
        this.ordersAmount = ordersAmount;
    }

    public LocalDateTime getOrdersPurchaserAt() {
        return ordersPurchaserAt;
    }

    public void setOrdersPurchaserAt(LocalDateTime ordersPurchaserAt) {
        this.ordersPurchaserAt = ordersPurchaserAt;
    }

    public Integer getOrdersRamBillId() {
        return ordersRamBillId;
    }

    public void setOrdersRamBillId(Integer ordersRamBillId) {
        this.ordersRamBillId = ordersRamBillId;
    }


    public OrdersRefundMethodEnum getRefundMethodEnum() {
        return refundMethodEnum;
    }

    public void setRefundMethodEnum(OrdersRefundMethodEnum refundMethodEnum) {
        this.refundMethodEnum = refundMethodEnum;
    }

    public OrdersRefundStatusEnum getRefundStatusEnum() {
        return refundStatusEnum;
    }

    public void setRefundStatusEnum(OrdersRefundStatusEnum refundStatusEnum) {
        this.refundStatusEnum = refundStatusEnum;
    }

    public OrdersRefundTypeEnum getRefundTypeEnum() {
        return refundTypeEnum;
    }

    public void setRefundTypeEnum(OrdersRefundTypeEnum refundTypeEnum) {
        this.refundTypeEnum = refundTypeEnum;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public String getSiteFrom() {
        return siteFrom;
    }

    public void setSiteFrom(String siteFrom) {
        this.siteFrom = siteFrom;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public LocalDateTime getOrdersPurchaseAt() {
        return ordersPurchaseAt;
    }

    public void setOrdersPurchaseAt(LocalDateTime ordersPurchaseAt) {
        this.ordersPurchaseAt = ordersPurchaseAt;
    }

    public Integer getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Integer auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public String getRefundSerialNumber() {
        return refundSerialNumber;
    }

    public void setRefundSerialNumber(String refundSerialNumber) {
        this.refundSerialNumber = refundSerialNumber;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getOrdersPayNo() {
        return ordersPayNo;
    }

    public void setOrdersPayNo(String ordersPayNo) {
        this.ordersPayNo = ordersPayNo;
    }

    public String getLogisticName() {
        return logisticName;
    }

    public void setLogisticName(String logisticName) {
        this.logisticName = logisticName;
    }
}
