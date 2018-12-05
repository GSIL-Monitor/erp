package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.PayMethodEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author  wangqian
 * 订单列表页DTO
 */
public class OrderDTO {

    private  String orderNo;

    /**
     * 订单id
     */
    private Long id;

    /**
     * 下单ip
     */
    private String ip;

    /**
     * 下单区域编码（投放区域编码）
     */
    private String zoneCode;

    /**
     * 下单区域名称（投放区域名称）
     */
    private String area;

    /**
     * 当天购买数量
     */
    private int dayPurchaseCount;

    /**
     * 订单警告类型，（正常：0，重复订单：1，黑名单：2）
     */
    private int orderWarningType;


    /**
     * 订单警告具体原因（正常，重复，ip黑名单，地址黑名单，email黑名单等）
     */
    private String orderWarningReason;

    /**
     * 域名
     */
    private String domain;

    // TODO: 2017/11/6
    // 香港 ：产品区域
    // 陈小静 ： 优化师
    /**
     * 订单状态
     *
     */
    private int orderStatusEnum;

    /**
     * 支付方式 1、在线支付，2、货到货款
     */
    private int paymentMethod;

    /**
     * 名字
     */
    private String firstName;

    /**
     * 姓
     */
    private String lastName;

    /**
     * 邮箱
     */
    private String email;


    /**
     * 电话
     */
    private String telphone;

    /**
     * 接收的验证码
     */
    private String getCode;

    /**
     * 输入的验证码
     */
    private String inputCode;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 购买数量
     */
    private int goodsQty;


    // TODO: 2017/11/6  产品名，多个spu应该是个集合
    /**
     * 产品名
     */
    private String title;


    // TODO: 2017/11/6  待处理
    /**
     * 产品属性
     */
    private String attrs;

    /**
     * 地址
     */
    private String address;


    /**
     * 邮政编码
     */
    private String zipcode;


    /**
     * 留言
     */
    private String customerMeassage;


    /**
     * 备注
     */
    private String memo;


    // TODO: 2017/11/6  展示时间是指？
    /**
     * 展示时间
     */
    private LocalDateTime showTime;


    /**
     * 黑名单字段
     */
    private String blackFields;


    /**
     * 重复下单
     */
    private String repeatInfo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getDayPurchaseCount() {
        return dayPurchaseCount;
    }

    public void setDayPurchaseCount(int dayPurchaseCount) {
        this.dayPurchaseCount = dayPurchaseCount;
    }

    public int getOrderWarningType() {
        return orderWarningType;
    }

    public void setOrderWarningType(int orderWarningType) {
        this.orderWarningType = orderWarningType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(int orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getGetCode() {
        return getCode;
    }

    public void setGetCode(String getCode) {
        this.getCode = getCode;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(int goodsQty) {
        this.goodsQty = goodsQty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCustomerMeassage() {
        return customerMeassage;
    }

    public void setCustomerMeassage(String customerMeassage) {
        this.customerMeassage = customerMeassage;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public String getPaymentMethodStateName(){
        return PayMethodEnum.fromId(this.paymentMethod).display();
    }

    public String getOrderStatusEnumStateName(){
        return OrderStateEnum.fromId(this.orderStatusEnum).display();
    }

    public String getOrderWarningReason() {
        return orderWarningReason;
    }

    public void setOrderWarningReason(String orderWarningReason) {
        this.orderWarningReason = orderWarningReason;
    }

    public String getBlackFields() {
        return blackFields;
    }

    public void setBlackFields(String blackFields) {
        this.blackFields = blackFields;
    }

    public String getRepeatInfo() {
        return repeatInfo;
    }

    public void setRepeatInfo(String repeatInfo) {
        this.repeatInfo = repeatInfo;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", zoneCode='" + zoneCode + '\'' +
                ", area='" + area + '\'' +
                ", dayPurchaseCount=" + dayPurchaseCount +
                ", orderWarningType=" + orderWarningType +
                ", domain='" + domain + '\'' +
                ", orderStatusEnum=" + orderStatusEnum +
                ", paymentMethod=" + paymentMethod +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", telphone='" + telphone + '\'' +
                ", getCode='" + getCode + '\'' +
                ", inputCode='" + inputCode + '\'' +
                ", orderAmount=" + orderAmount +
                ", goodsQty=" + goodsQty +
                ", title='" + title + '\'' +
                ", attrs='" + attrs + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", customerMeassage='" + customerMeassage + '\'' +
                ", memo='" + memo + '\'' +
                ", showTime=" + showTime +
                '}';
    }
}
