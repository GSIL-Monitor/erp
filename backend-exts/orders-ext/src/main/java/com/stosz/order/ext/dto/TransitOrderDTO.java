package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库表
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class TransitOrderDTO implements Serializable {
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * sku
     S0114663 Coolrich正品意大利高檔手工頭層牛皮托特包…
     D011473900001 棕色,37 x 1
     S011466300014 墨綠色,39 x 1
     */
    private List<OrderItemSpuDTO> spuList;

    /**
     * 地区
     */
    private  String zoneName;

    /**
     * 物流公司名称
     */
    private  String logisticsName;
    /**
     * 物流状态
     */
    private  String trackingStatusShow;

    //拣货导出时所需字段
    /**
     * 客户姓名
     */
    private  String customerName;
    /**
     * 电话
     */
    private  String mobile;
    /**
     * 总价
     */
    private BigDecimal orderAmount;
    /**
     * 产品数量(商品数量)
     */
    private  Integer goodQty;
    /**
     * 用户留言
     */
    private  String customerMessage;
    /**
     * 地址
     */
    private  String address;
    /**
     * 邮编
     */
    private  String zipCode;
    /**
     * 省/洲
     */
    private  String province;
    /**
     * 城市
     */
    private  String city;
    /**
     * 区域
     */
    private  String area;

    /**
     * 发货时间
     * @return
     */
    private LocalDateTime deliveryTime;

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTrackingStatusShow() {
        return trackingStatusShow;
    }

    public void setTrackingStatusShow(String trackingStatusShow) {
        this.trackingStatusShow = trackingStatusShow;
    }

    public List<OrderItemSpuDTO> getSpuList() {
        return spuList;
    }

    public void setSpuList(List<OrderItemSpuDTO> spuList) {
        this.spuList = spuList;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getGoodQty() {
        return goodQty;
    }

    public void setGoodQty(Integer goodQty) {
        this.goodQty = goodQty;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
