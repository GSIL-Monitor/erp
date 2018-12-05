package com.stosz.order.ext.model;

import com.google.common.collect.Lists;
import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.PayMethodEnum;
import com.stosz.order.ext.enums.PayStateEnum;
import com.stosz.order.ext.enums.TelCodeState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by liusl on 2018/1/6.
 */
public class CombOrderItem{


    /**
     * 订单编号（ERP）
     */
    private Integer id = 0;


    /**
     * 订单编号（建站编号，前端显示此项）
     */
    private String merchantOrderNo = "";

    /**
     * 下单IP所有在的地区
     */
    private String area = "";

    /**
     * 下单区域名称
     */
    private String zoneName ="";

    /**
     * 区域ID
     */
    private Integer zoneId;
    /**
     *  国家
     */
    private String country;
    /**
     * ip地址
     */
    private String ip ="";

    /**
     * 订单状态
     */
    private OrderStateEnum orderStatusEnum ;


    /**
     * 支付状态
     */
    private PayStateEnum payState = PayStateEnum.unPaid;

    /**
     * 支付方法
     */
    private PayMethodEnum paymentMethod = PayMethodEnum.cod;

    /**
     * firstName
     */
    private String firstName = "";

    /**
     * lastName
     */
    private String lastName="";

    /**
     * 广告员名称
     */
    private String seoUserName="";


    /**
     * 广告元部门名称
     */
    private String departmentUserInfo = "";

    /**
     * 手机号
     */
    private String telphone="";


    /**
     * 邮箱
     */
    private String email="";

    /**
     * 订单金额
     */
    private BigDecimal orderAmount = new BigDecimal("0");
    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 订单金额，包括了单位符号(用于展示)
     */
    private String amountShow="";

    /**
     * 数量
     */
    private int goodsQty=0;

    /**
     * 地址
     */
    private String address="";

    /**
     * 省
     */
    private String provice="";
    /**
     * 城市
     */
    private String city="";
    /**
     * 邮编
     */
    private String zipcode="";

    /**
     * 客户留言
     */
    private String customerMessage="";

    /**
     * 备注
     */
    private String memo="";

    /**
     * 运单号
     */
    private String trackingNo="";

    /**
     * 仓库编号
     */
    private Integer warehouseId =0;
    /**
     * 客户ID
     */
    private  Integer customerId;

    /**
     * 物流公司编号
     */
    private String logisticsId="";

    /**
     * 快递公司名称
     */
    private String logisticsName = "";

    /**
     * 仓库名称
     */
    private String warehouseName = "";
    /**
     * 下单时间
     */
    private Date purchaserAt;
    /**
     * 订单项信息
     */
    private List<OrdersItem> ordersItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public OrderStateEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStateEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public PayStateEnum getPayState() {
        return payState;
    }

    public void setPayState(PayStateEnum payState) {
        this.payState = payState;
    }

    public PayMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PayMethodEnum paymentMethod) {
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

    public String getSeoUserName() {
        return seoUserName;
    }

    public void setSeoUserName(String seoUserName) {
        this.seoUserName = seoUserName;
    }

    public String getDepartmentUserInfo() {
        return departmentUserInfo;
    }

    public void setDepartmentUserInfo(String departmentUserInfo) {
        this.departmentUserInfo = departmentUserInfo;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAmountShow() {
        return amountShow;
    }

    public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
    }

    public int getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(int goodsQty) {
        this.goodsQty = goodsQty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Date getPurchaserAt() {
        return purchaserAt;
    }

    public void setPurchaserAt(Date purchaserAt) {
        this.purchaserAt = purchaserAt;
    }

    public List<OrdersItem> getOrdersItems() {
        return ordersItems;
    }

    public void setOrdersItems(List<OrdersItem> ordersItems) {
        this.ordersItems = ordersItems;
    }
}
