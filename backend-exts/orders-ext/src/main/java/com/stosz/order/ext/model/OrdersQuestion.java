package com.stosz.order.ext.model;

import com.stosz.order.ext.enums.OrderQuestionStatusEnum;
import com.stosz.order.ext.enums.OrderQuestionTypeEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 问题件实体类
 */
public class OrdersQuestion extends AbstractParamEntity implements ITable, Serializable {

    /**
     * 自增主键
     */
    @DBColumn
    private Long id = new Long(0);
    /**
     * 部门id
     */
    @DBColumn
    private Integer departmentId = new Integer(0);
    /**
     * 部门名称
     */
    @DBColumn
    private String departmentInfo = "";
    /**
     * 区域id
     */
    @DBColumn
    private Integer zoneId = new Integer(0);
    /**
     * 区域名称
     */
    @DBColumn
    private String zoneName = "";
    /**
     * 物流公司id
     */
    @DBColumn
    private Integer logisticId = new Integer(0);
    /**
     * 物流公司名称
     */
    @DBColumn
    private String logisticName = "";

    /**
     * 运单号
     */
    @DBColumn
    private String transNo = "";
    /**
     * 订单id
     */
    @DBColumn
    private Long ordersId = new Long(0);

    /**
     * 订单内部名称
     */
    @DBColumn
    private String orderInnerTitle = "";

    /**
     * 订单号
     */
    @DBColumn
    private String orderNo = "";
    /**
     * 问题类型
     */
    @DBColumn
    private OrderQuestionTypeEnum questionType = OrderQuestionTypeEnum.Other;

    /**
     * 问题详情
     */
    @DBColumn
    private String questionDetail = "";
    /**
     * 用户id
     */
    @DBColumn
    private Long customerId = new Long(0);
    /**
     * 用户姓名
     */
    @DBColumn
    private String customerName = "";
    /**
     * 用户电话
     */
    @DBColumn
    private String customerPhone = "";
    /**
     * 用户的邮件地址
     */
    @DBColumn
    private String customerEmail = "";
    /**
     * 总价
     */
    @DBColumn
    private java.math.BigDecimal totalAmount = new java.math.BigDecimal(0);
    /**
     * 发送时间
     */
    @DBColumn
    private java.time.LocalDateTime sendTime = java.time.LocalDateTime.now();
    /**
     * 处理结果
     */
    @DBColumn
    private String dealResult = "";
    /**
     * 处理状态
     */
    @DBColumn
    private OrderQuestionStatusEnum dealStatusEnum = OrderQuestionStatusEnum.NotHandle;

    /**
     * 处理人ID
     */
    @DBColumn
    private Integer dealUserId = new Integer(0);

    /**
     * 处理人姓名
     */
    @DBColumn
    private String dealUsername = "";


    /**
     * 首次处理时间
     */
    @DBColumn
    private java.time.LocalDateTime dealFirstTime;

    /**
     * 最后处理时间
     */
    @DBColumn
    private java.time.LocalDateTime dealLastTime;

    /**
     * 处理备注
     */
    @DBColumn
    private String dealMemo = "";


    /**
     * 物流配送时间
     */
    @DBColumn
    private java.time.LocalDateTime logisticDeliveryTime = java.time.LocalDateTime.now();
    /**
     * 创建时间
     */
    @DBColumn
    private java.time.LocalDateTime createAt = java.time.LocalDateTime.now();
    /**
     * 更新时间
     */
    @DBColumn
    private java.time.LocalDateTime updateAt = java.time.LocalDateTime.now();
    /**
     * 创建者id
     */
    @DBColumn
    private Integer creatorId = new Integer(0);
    /**
     * 创建人
     */
    @DBColumn
    private String creator = "";


    public OrdersQuestion() {
    }

    public void setId(Integer id) {
        this.id = Long.valueOf(id);
    }

    public Integer getId() {
        return id.intValue();
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getZoneId() {
        return zoneId;
    }


    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTransNo() {
        return transNo;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setQuestionType(OrderQuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public OrderQuestionTypeEnum getQuestionType() {
        return questionType;
    }

    public void setQuestionDetail(String questionDetail) {
        this.questionDetail = questionDetail;
    }

    public String getQuestionDetail() {
        return questionDetail;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setTotalAmount(java.math.BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public java.math.BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setSendTime(java.time.LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public java.time.LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getDealResult() {
        return dealStatusEnum.display();
    }

    public void setLogisticDeliveryTime(java.time.LocalDateTime logisticDeliveryTime) {
        this.logisticDeliveryTime = logisticDeliveryTime;
    }

    public java.time.LocalDateTime getLogisticDeliveryTime() {
        return logisticDeliveryTime;
    }

    public void setCreateAt(java.time.LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public java.time.LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setUpdateAt(java.time.LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public java.time.LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public String getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(String departmentInfo) {
        this.departmentInfo = departmentInfo;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(Integer logisticId) {
        this.logisticId = logisticId;
    }

    public String getLogisticName() {
        return logisticName;
    }

    public void setLogisticName(String logisticName) {
        this.logisticName = logisticName;
    }

    public String getOrderInnerTitle() {
        return orderInnerTitle;
    }

    public void setOrderInnerTitle(String orderInnerTitle) {
        this.orderInnerTitle = orderInnerTitle;
    }

    public OrderQuestionStatusEnum getDealStatusEnum() {
        return dealStatusEnum;
    }

    public void setDealStatusEnum(OrderQuestionStatusEnum dealStatusEnum) {
        this.dealStatusEnum = dealStatusEnum;
    }

    public Integer getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(Integer dealUserId) {
        this.dealUserId = dealUserId;
    }

    public String getDealUsername() {
        return dealUsername;
    }

    public void setDealUsername(String dealUsername) {
        this.dealUsername = dealUsername;
    }

    public LocalDateTime getDealFirstTime() {
        return dealFirstTime;
    }

    public void setDealFirstTime(LocalDateTime dealFirstTime) {
        this.dealFirstTime = dealFirstTime;
    }

    public LocalDateTime getDealLastTime() {
        return dealLastTime;
    }

    public void setDealLastTime(LocalDateTime dealLastTime) {
        this.dealLastTime = dealLastTime;
    }

    public String getDealMemo() {
        return dealMemo;
    }

    public void setDealMemo(String dealMemo) {
        this.dealMemo = dealMemo;
    }

    @Override
    public String getTable() {
        return "orders_question";
    }

}