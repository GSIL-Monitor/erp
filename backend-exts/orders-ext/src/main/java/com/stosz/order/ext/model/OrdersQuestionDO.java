package com.stosz.order.ext.model;

import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.order.ext.enums.OrderQuestionStatusEnum;
import com.stosz.order.ext.enums.OrderQuestionTypeEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 问题件列表DO
 */
public class OrdersQuestionDO {

    /**
     * 自增主键
     */
    private Long id;
    /**
     * 部门id
     */
    private Integer departmentId;
    /**
     * 部门名称
     */
    private String departmentInfo;
    /**
     * 区域id
     */
    private Integer zoneId;
    /**
     * 区域名称
     */
    private String zoneName;
    /**
     * 物流公司id
     */
    private Integer logisticId;
    /**
     * 物流公司名称
     */
    private String logisticName;

    /**
     * 运单号
     */
    private String transNo;
    /**
     * 订单id
     */
    private Long ordersId;

    /**
     * 订单内部名称
     */
    private String orderInnerTitle;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 问题类型
     */
    private OrderQuestionTypeEnum questionType;

    /**
     * 问题详情
     */
    private String questionDetail;
    /**
     * 用户id
     */
    private Long customerId;
    /**
     * 用户姓名
     */
    private String customerName;
    /**
     * 用户电话
     */
    private String customerPhone;
    /**
     * 用户的邮件地址
     */
    private String customerEmail;
    /**
     * 总价
     */
    private java.math.BigDecimal totalAmount;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 处理结果
     */
    private String dealResult;
    /**
     * 处理状态
     */
    private OrderQuestionStatusEnum dealStatusEnum;

    /**
     * 处理人姓名
     */
    private String dealUsername;


    /**
     * 首次处理时间
     */
    private LocalDateTime dealFirstTime;

    /**
     * 最后处理时间
     */
    private LocalDateTime dealLastTime;

    /**
     * 处理备注
     */
    private String dealMemo;


    /**
     * 物流配送时间
     */
    private LocalDateTime logisticDeliveryTime;

    /**
     * 导入时间
     */
    private java.time.LocalDateTime createAt;


    /**
     * spu列表 { title：spu标题，sku:{attr: sku属性, qty: sku数量}}
     */
    private List<OrderItemSpuDTO> spuList;


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

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getDealResult() {
        return dealStatusEnum.display();
    }

    public void setLogisticDeliveryTime(LocalDateTime logisticDeliveryTime) {
        this.logisticDeliveryTime = logisticDeliveryTime;
    }

    public LocalDateTime getLogisticDeliveryTime() {
        return logisticDeliveryTime;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public List<OrderItemSpuDTO> getSpuList() {
        return spuList;
    }

    public void setSpuList(List<OrderItemSpuDTO> spuList) {
        this.spuList = spuList;
    }
}