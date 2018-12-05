package com.stosz.order.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.OrdersRefundMethodEnum;
import com.stosz.order.ext.enums.OrdersRefundStatusEnum;
import com.stosz.order.ext.enums.OrdersRefundTypeEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @auther tangtao
 * create time  2018/1/3
 */
public class OrdersRefundBill extends AbstractParamEntity implements ITable, Serializable, IFsmInstance {

    /**
     * 退款单编号
     */
    @DBColumn
    private Integer id;
    /**
     * 订单流水号
     */
    @DBColumn
    private Integer ordersId;
    /**
     * 订单金额
     */
    @DBColumn
    private BigDecimal ordersAmount = new BigDecimal("0");
    /**
     * 退货拒收单号
     */
    @DBColumn
    private Integer ordersRamBillId;
    /**
     * 订单付款流水号
     */
    @DBColumn
    private String ordersPayNo;
    /**
     * 退款状态
     */
    @DBColumn
    private OrdersRefundStatusEnum refundStatusEnum;
    /**
     * 退款类型
     */
    @DBColumn
    private OrdersRefundTypeEnum refundTypeEnum;
    /**
     * 退款方式
     */
    private OrdersRefundMethodEnum refundMethodEnum;
    /**
     * 币种
     */
    @DBColumn
    private String currencyCode;
    /**
     * 退款金额
     */
    @DBColumn
    private BigDecimal refundAmount;
    /**
     * 审核备注
     */
    @DBColumn
    private String auditMemo;
    /**
     * 网站来源
     */
    @DBColumn
    private String siteFrom;
    /**
     * 审核时间
     */
    @DBColumn
    private LocalDateTime auditTime;
    /**
     * 订单下单时间
     */
    @DBColumn
    private LocalDateTime ordersPurchaseAt;
    /**
     * 审核人id
     */
    @DBColumn
    private Integer auditUserId;
    /**
     * 审核人
     */
    @DBColumn
    private String auditUserName;
    /**
     * 退款流水号
     */
    @DBColumn
    private String refundSerialNumber;
    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @DBColumn
    private LocalDateTime updateAt;
    /**
     * 创建人
     */
    @DBColumn
    private String creator;
    /**
     * 状态机时间
     */
    @DBColumn
    private java.time.LocalDateTime stateTime;
    /**
     * 部门ID
     */
    @DBColumn
    private Integer seoDepartmentId;
    /**
     * 区域ID
     */
    @DBColumn
    private Integer zoneId;

    /**
     * 退款物流企业id
     */
    @DBColumn
    private Integer logisticsId;

    /**
     * 退款物流企业名称
     */
    @DBColumn
    private String logisticName;

    public Integer getSeoDepartmentId() {
        return seoDepartmentId;
    }

    public void setSeoDepartmentId(Integer seoDepartmentId) {
        this.seoDepartmentId = seoDepartmentId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public OrdersRefundMethodEnum getRefundMethodEnum() {
        return refundMethodEnum;
    }

    public void setRefundMethodEnum(OrdersRefundMethodEnum refundMethodEnum) {
        this.refundMethodEnum = refundMethodEnum;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return getId();
    }

    @Override
    public String getState() {
        if (null == refundStatusEnum) return "";
        return refundStatusEnum.name();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public void setState(String state) {
        refundStatusEnum = OrdersRefundStatusEnum.fromName(state);
    }

    @Override
    public LocalDateTime getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTable() {
        return "orders_refund_bill";
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getOrdersRamBillId() {
        return ordersRamBillId;
    }

    public void setOrdersRamBillId(Integer ordersRamBillId) {
        this.ordersRamBillId = ordersRamBillId;
    }

    public String getOrdersPayNo() {
        return ordersPayNo;
    }

    public void setOrdersPayNo(String ordersPayNo) {
        this.ordersPayNo = ordersPayNo;
    }

    public OrdersRefundStatusEnum getRefundStatusEnum() {
        return refundStatusEnum;
    }

    public void setRefundStatusEnum(OrdersRefundStatusEnum refundStatusEnum) {
        this.refundStatusEnum = refundStatusEnum;
    }

    public BigDecimal getOrdersAmount() {
        return ordersAmount;
    }

    public void setOrdersAmount(BigDecimal ordersAmount) {
        this.ordersAmount = ordersAmount;
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

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticName() {
        return logisticName;
    }

    public void setLogisticName(String logisticName) {
        this.logisticName = logisticName;
    }
}
