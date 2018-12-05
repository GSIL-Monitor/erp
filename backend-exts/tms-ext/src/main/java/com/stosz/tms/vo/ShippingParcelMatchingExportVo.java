package com.stosz.tms.vo;

public class ShippingParcelMatchingExportVo {

    private String trackNo;

    private String orderNo;

    private String productTitle;

    private String orderDetailQty;

    private String sku;

    private String wmsName;

    private Integer goodsQty; //商品数量

    private  String state;

    private String shippingName;

    private String trackStatus; //物流状态

    private String  classifyStatus; //物流归类状态

    private String isSettlemented; //是否结款

    private String createAt;  //下单时间

    private String receiveTime; //签收时间

    private String shipmentsTime; //发货时间

    public String getShipmentsTime() {
        return shipmentsTime;
    }

    public void setShipmentsTime(String shipmentsTime) {
        this.shipmentsTime = shipmentsTime;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOrderDetailQty() {
        return orderDetailQty;
    }

    public void setOrderDetailQty(String orderDetailQty) {
        this.orderDetailQty = orderDetailQty;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public Integer getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(String trackStatus) {
        this.trackStatus = trackStatus;
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus;
    }

    public String getIsSettlemented() {
        return isSettlemented;
    }

    public void setIsSettlemented(String isSettlemented) {
        this.isSettlemented = isSettlemented;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
}


