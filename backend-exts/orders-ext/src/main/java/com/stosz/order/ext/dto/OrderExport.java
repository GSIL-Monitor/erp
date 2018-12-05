package com.stosz.order.ext.dto;

import java.time.LocalDateTime;

/**
 * @author  wangqian
 * 订单导出实体
 */
public class OrderExport {

//    /**
//     * 订单流水号，对内使用
//     */
//    private Integer id;

    /**
     * 建站传过来的订单号
     */
    private String merchantOrderNo;


    /**
     * 地区
     */
    private String area;


    /**
     * 广告专员
     */
    private String seoUserName;


    /**
     * 域名
     */
    private String domain;


    /**
     * 姓名
     */
    private String customerName;


    /**
     * 外部产品名
     */
    private String foreignProductTitle;


    /**
     * 产品内部名
     */
    private String innerProducctTitle;


    /**
     * 总价
     */
    private String price;

    /**
     * 属性（可能有多个属性）深色-35 x 2, 藍色-43 x 1
     */
    private String atrr;

    /**
     * sku （2090246,S010447400005）
     */
    private String sku;

    /**
     * 地址
     */
    private String address;

    /**
     * 购买产品数量
     */
    private Integer qty;

    /**
     * 留言备注（留言 + 备注 ？）
     */
    private String memo;

    /**
     * 下单时间
     */
    private LocalDateTime purchaseAt;

    /**
     * 到货时间(签收时间)
     */
    private LocalDateTime assignAt;

    /**
     * 上线时间
     */
    private LocalDateTime onlineAt;

    /**
     * 仓库
     */
    private String warehoure;

    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 发货日期
     */
    private LocalDateTime  deliveringAt;

    /**
     * 物流名称
     */
    private String logisticsName;

    /**
     * 运单号
     */
    private String trackingNo;

    /**
     * 物流状态
     */
    private String logisticsState;

    /**
     * 结款状态
     */
    private String payState;


//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

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

    public String getSeoUserName() {
        return seoUserName;
    }

    public void setSeoUserName(String seoUserName) {
        this.seoUserName = seoUserName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getForeignProductTitle() {
        return foreignProductTitle;
    }

    public void setForeignProductTitle(String foreignProductTitle) {
        this.foreignProductTitle = foreignProductTitle;
    }

    public String getInnerProducctTitle() {
        return innerProducctTitle;
    }

    public void setInnerProducctTitle(String innerProducctTitle) {
        this.innerProducctTitle = innerProducctTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAtrr() {
        return atrr;
    }

    public void setAtrr(String atrr) {
        this.atrr = atrr;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDateTime getPurchaseAt() {
        return purchaseAt;
    }

    public void setPurchaseAt(LocalDateTime purchaseAt) {
        this.purchaseAt = purchaseAt;
    }

    public LocalDateTime getAssignAt() {
        return assignAt;
    }

    public void setAssignAt(LocalDateTime assignAt) {
        this.assignAt = assignAt;
    }

    public LocalDateTime getOnlineAt() {
        return onlineAt;
    }

    public void setOnlineAt(LocalDateTime onlineAt) {
        this.onlineAt = onlineAt;
    }

    public String getWarehoure() {
        return warehoure;
    }

    public void setWarehoure(String warehoure) {
        this.warehoure = warehoure;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public LocalDateTime getDeliveringAt() {
        return deliveringAt;
    }

    public void setDeliveringAt(LocalDateTime deliveringAt) {
        this.deliveringAt = deliveringAt;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getLogisticsState() {
        return logisticsState;
    }

    public void setLogisticsState(String logisticsState) {
        this.logisticsState = logisticsState;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }


}
