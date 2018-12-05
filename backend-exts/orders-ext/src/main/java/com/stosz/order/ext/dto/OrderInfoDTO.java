package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 
 * 订单摘要的订单信息
 * @author liusl
 */
public class OrderInfoDTO implements Serializable{
    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = -2486443041693213036L;
    private Integer orderId;//订单Id
    private LocalDateTime purchaserAt;//下单时间
    private String zoneName;//区域
    private String paymentMethod;//支付方式
    private String webUrl;//二级域名
    private String domain;//域名
    private String orderFrom;//来源
    private String ip;//ip
    private String ipName;//ipName
    private String orderStatusEnum;//订单状态
    private String payState;//支付状态
    private String merchantOrderNo;//订单编号
    private String departmentUserInfo;//部门信息
    private String seoUserName;//优化师
    private Integer goodsQty;//下单数量
    private String memo;//备注
    private String merchantEnum;//平台编号（0：未知，1：单品站，2：综合站）。
    private String  comboName;//套餐名称
    private String  trackingStatusShow;//物流状态中文
    private String  trackingNo;//运单号
    private String  logisticsName;//物流名称
    private String  symbolLeft;//如果在左边则显示
    private String  symbolRight;//如果在右边则显示在右边
    private String currencyName;//币种名称


    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getTrackingStatusShow() {
        return trackingStatusShow;
    }

    public void setTrackingStatusShow(String trackingStatusShow) {
        this.trackingStatusShow = trackingStatusShow;
    }

    public String getSymbolLeft() {
        return symbolLeft;
    }

    public void setSymbolLeft(String symbolLeft) {
        this.symbolLeft = symbolLeft;
    }

    public String getSymbolRight() {
        return symbolRight;
    }

    public void setSymbolRight(String symbolRight) {
        this.symbolRight = symbolRight;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public String getMerchantEnum() {
        return merchantEnum;
    }

    public void setMerchantEnum(String merchantEnum) {
        this.merchantEnum = merchantEnum;
    }

    /**
     * 订单金额，包括了单位符号(用于展示)
     */
    private String amountShow;
    /**
     * 订单需要时候的金额
     */
    private BigDecimal confirmAmount = new BigDecimal("0.00");

    public BigDecimal getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(BigDecimal confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getAmountShow() {
        return amountShow;
    }

    public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getMemo() {
        return memo;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public Integer getGoodsQty() {
        return goodsQty;
    }

    
    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
    }



    public LocalDateTime getPurchaserAt() {
        return purchaserAt;
    }


    
    public void setPurchaserAt(LocalDateTime purchaserAt) {
        this.purchaserAt = purchaserAt;
    }


    public String getPayState() {
        return payState;
    }

    
    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getZoneName() {
        return zoneName;
    }
    
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    public String getOrderFrom() {
        return orderFrom;
    }
    
    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getOrderStatusEnum() {
        return orderStatusEnum;
    }
    
    public void setOrderStatusEnum(String orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }
    
    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }
    
    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }
    
    public String getDepartmentUserInfo() {
        return departmentUserInfo;
    }
    
    public void setDepartmentUserInfo(String departmentUserInfo) {
        this.departmentUserInfo = departmentUserInfo;
    }
    
    public String getSeoUserName() {
        return seoUserName;
    }
    
    public void setSeoUserName(String seoUserName) {
        this.seoUserName = seoUserName;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
}
