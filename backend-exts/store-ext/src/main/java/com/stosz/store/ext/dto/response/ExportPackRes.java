package com.stosz.store.ext.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 拣货导出 excel
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class ExportPackRes implements Serializable {

    /**
     * 地区
     */
    private String zoneName;

    private Long OrderIdNew;

    private String trackingNoNew;

    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 电话
     */
    private String mobile;

    /**
     * 内部名
     */
    private String innerName;
    /**
     * 外文名称
     */
    private String foreignTitle;
    /**
     * sku
     */
    private String sku;
    /**
     * 总价
     */
    private Integer orderAmount;
    /**
     * 产品数量(商品数量)
     */
    private Integer goodsQty;
    /**
     * 地址
     */
    private String address;
    /**
     * 用户留言
     */
    private String customerMeassage;
    /**
     * （新订单）下单日期
     */
    private LocalDateTime orderAt;
    /**
     * 状态名称
     */
    private String transitStatusName;

    private String trackingNoOld;
    /**
     * 库位
     */
    private String storageLocal;
    /**
     * 邮编
     */
    private String zipcode;
    /**
     * 仓库名称
     */
    private String stockName;
    /**
     * 省/洲
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区域
     */
    private String area;

    /**
     * 导出状态
     */
    private String exportStatus;
    /**
     * 导出结果描述
     */
    private String exportMsg;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Long getOrderIdNew() {
        return OrderIdNew;
    }

    public void setOrderIdNew(Long orderIdNew) {
        OrderIdNew = orderIdNew;
    }

    public String getTrackingNoNew() {
        return trackingNoNew;
    }

    public void setTrackingNoNew(String trackingNoNew) {
        this.trackingNoNew = trackingNoNew;
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

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }

    public String getForeignTitle() {
        return foreignTitle;
    }

    public void setForeignTitle(String foreignTitle) {
        this.foreignTitle = foreignTitle;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerMeassage() {
        return customerMeassage;
    }

    public void setCustomerMeassage(String customerMeassage) {
        this.customerMeassage = customerMeassage;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public String getTransitStatusName() {
        return transitStatusName;
    }

    public void setTransitStatusName(String transitStatusName) {
        this.transitStatusName = transitStatusName;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public String getStorageLocal() {
        return storageLocal;
    }

    public void setStorageLocal(String storageLocal) {
        this.storageLocal = storageLocal;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
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

    public String getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }

    public String getExportMsg() {
        return exportMsg;
    }

    public void setExportMsg(String exportMsg) {
        this.exportMsg = exportMsg;
    }
}
