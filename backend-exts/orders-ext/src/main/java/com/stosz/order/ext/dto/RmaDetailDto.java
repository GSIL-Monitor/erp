package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.*;
import com.stosz.plat.model.DBColumn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RmaDetailDto {

    private RmaInfo rmaInfo;

    private List<RmaItemInfo> rmaItemInfoList;

    public RmaInfo getRmaInfo() {
        return rmaInfo;
    }

    public void setRmaInfo(RmaInfo rmaInfo) {
        this.rmaInfo = rmaInfo;
    }

    public List<RmaItemInfo> getRmaItemInfoList() {
        return rmaItemInfoList;
    }

    public void setRmaItemInfoList(List<RmaItemInfo> rmaItemInfoList) {
        this.rmaItemInfoList = rmaItemInfoList;
    }


    public static class RmaInfo {

        /**
         * 申请单编号
         */
        private Integer rmaId;

        /**
         * 订单编号
         */
        private Integer orderId;

        /**
         * 订单购买时间
         */
        private LocalDateTime purchaseAt;

        /**
         * 订单金额
         */
        private BigDecimal orderAmount;


        /**
         * 原因备注
         */
        private String changeReasonOtherMemo;


        /**
         * 订单确认金额，也就是可退的最高金额
         */
        private BigDecimal confirmAmount;


        private String leftSymbol = "";

        private String rightSymbol = "";

        /**
         * 支付方式
         */
        private PayMethodEnum payMethod;


        /**
         * 订单的原单号
         */
        private String orderTrackingNo;

        /**
         * 退回运单号
         */
        private String trackingNo;

        /**
         * 站点来源
         */
        private String webUrl;


        /**
         * 退换货状态
         */
        private OrdersRmaStateEnum state;

        /**
         * 退换货类型
         */
        private ChangeTypeEnum changeType;

        /**
         * 退换货来源
         */
        private RmaSourceEnum rmaSource;

        /**
         * 是否回收商品
         */
        private RecycleGoodsEnum recycleGoods;

        /**
         * 退换货原因
         */
        private ChangeReasonEnum changeReason;

        /**
         * 问题描述
         */
        private String questionMemo;

        /**
         * 退款方式
         */
        private ChangeWayEnum changeWay;

        /**
         * 物流名称
         */
        private String logisticsName;


        /**
         * 申请金额
         */
        private BigDecimal applyAmount;

        /**
         * 申请账号（可无）
         */
        private String applyAccount;

        /**
         * 上传的图片
         */
        private List<String> imgList;

        /**
         * 申请备注
         */
        private String applyMemo;

        /**
         * 状态
         */
        private OrdersRmaStateEnum rmaStatus;

        private LocalDateTime applyTime;


        private Integer id;

        private String logisticName;

        private Long refundId;

        private String checkMemo;

        public String getCheckMemo() {
            return checkMemo;
        }

        public void setCheckMemo(String checkMemo) {
            this.checkMemo = checkMemo;
        }

        public Integer getRmaId() {
            return rmaId;
        }

        public void setRmaId(Integer rmaId) {
            this.rmaId = rmaId;
        }

        public String getChangeReasonOtherMemo() {
            return changeReasonOtherMemo;
        }

        public void setChangeReasonOtherMemo(String changeReasonOtherMemo) {
            this.changeReasonOtherMemo = changeReasonOtherMemo;
        }

        public LocalDateTime getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(LocalDateTime applyTime) {
            this.applyTime = applyTime;
        }

        public String getOrderTrackingNo() {
            return orderTrackingNo;
        }

        public void setOrderTrackingNo(String orderTrackingNo) {
            this.orderTrackingNo = orderTrackingNo;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLogisticName() {
            return logisticName;
        }

        public void setLogisticName(String logisticName) {
            this.logisticName = logisticName;
        }

        public Long getRefundId() {
            return refundId;
        }

        public void setRefundId(Long refundId) {
            this.refundId = refundId;
        }

        public OrdersRmaStateEnum getRmaStatus() {
            return rmaStatus;
        }

        public void setRmaStatus(OrdersRmaStateEnum rmaStatus) {
            this.rmaStatus = rmaStatus;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public LocalDateTime getPurchaseAt() {
            return purchaseAt;
        }

        public void setPurchaseAt(LocalDateTime purchaseAt) {
            this.purchaseAt = purchaseAt;
        }

        public BigDecimal getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(BigDecimal orderAmount) {
            this.orderAmount = orderAmount;
        }

        public BigDecimal getConfirmAmount() {
            return confirmAmount;
        }

        public void setConfirmAmount(BigDecimal confirmAmount) {
            this.confirmAmount = confirmAmount;
        }


        public String getLeftSymbol() {
            return leftSymbol;
        }

        public void setLeftSymbol(String leftSymbol) {
            this.leftSymbol = leftSymbol;
        }

        public String getRightSymbol() {
            return rightSymbol;
        }

        public void setRightSymbol(String rightSymbol) {
            this.rightSymbol = rightSymbol;
        }

        public PayMethodEnum getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(PayMethodEnum payMethod) {
            this.payMethod = payMethod;
        }

        public String getTrackingNo() {
            return trackingNo;
        }

        public void setTrackingNo(String trackingNo) {
            this.trackingNo = trackingNo;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public OrdersRmaStateEnum getState() {
            return state;
        }

        public void setState(OrdersRmaStateEnum state) {
            this.state = state;
        }

        public ChangeTypeEnum getChangeType() {
            return changeType;
        }

        public void setChangeType(ChangeTypeEnum changeType) {
            this.changeType = changeType;
        }

        public RmaSourceEnum getRmaSource() {
            return rmaSource;
        }

        public void setRmaSource(RmaSourceEnum rmaSource) {
            this.rmaSource = rmaSource;
        }

        public RecycleGoodsEnum getRecycleGoods() {
            return recycleGoods;
        }

        public void setRecycleGoods(RecycleGoodsEnum recycleGoods) {
            this.recycleGoods = recycleGoods;
        }

        public ChangeReasonEnum getChangeReason() {
            return changeReason;
        }

        public void setChangeReason(ChangeReasonEnum changeReason) {
            this.changeReason = changeReason;
        }

        public String getQuestionMemo() {
            return questionMemo;
        }

        public void setQuestionMemo(String questionMemo) {
            this.questionMemo = questionMemo;
        }

        public ChangeWayEnum getChangeWay() {
            return changeWay;
        }

        public void setChangeWay(ChangeWayEnum changeWay) {
            this.changeWay = changeWay;
        }

        public String getLogisticsName() {
            return logisticsName;
        }

        public void setLogisticsName(String logisticsName) {
            this.logisticsName = logisticsName;
        }


        public BigDecimal getApplyAmount() {
            return applyAmount;
        }

        public void setApplyAmount(BigDecimal applyAmount) {
            this.applyAmount = applyAmount;
        }

        public String getApplyAccount() {
            return applyAccount;
        }

        public void setApplyAccount(String applyAccount) {
            this.applyAccount = applyAccount;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public String getApplyMemo() {
            return applyMemo;
        }

        public void setApplyMemo(String applyMemo) {
            this.applyMemo = applyMemo;
        }
    }

    public static class RmaItemInfo {

        /**
         * 订单项编号
         */
        private Integer itemId;

        /**
         * sku
         */
        private String sku;

        /**
         * 产品标题
         */
        private String productTitle;

        /**
         * 购买的数量
         */
        private Integer ordersItemQty;

        /**
         * 可退的数量
         */
        private Integer ordersItemReturnQty;

        /**
         * 申请数量
         */
        private Integer ordersItemApplyQty;

        /**
         * 退换货单价（中）
         */
        private String singleAmountShow;

        /**
         * 货币名称
         */
        private String currencyName;

        /**
         * 入库货位
         */
        private String storageLocation;

        /**
         * 仓库入库(货)数量
         */
        private Integer inQty;

        /**
         * 属性值数组
         */
        private String attrTitleArray;


        public String getStorageLocation() {
            return storageLocation;
        }

        public void setStorageLocation(String storageLocation) {
            this.storageLocation = storageLocation;
        }

        public Integer getInQty() {
            return inQty;
        }

        public void setInQty(Integer inQty) {
            this.inQty = inQty;
        }

        public String getAttrTitleArray() {
            return attrTitleArray;
        }

        public void setAttrTitleArray(String attrTitleArray) {
            this.attrTitleArray = attrTitleArray;
        }

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public Integer getOrdersItemQty() {
            return ordersItemQty;
        }

        public void setOrdersItemQty(Integer ordersItemQty) {
            this.ordersItemQty = ordersItemQty;
        }

        public Integer getOrdersItemReturnQty() {
            return ordersItemReturnQty;
        }

        public void setOrdersItemReturnQty(Integer ordersItemReturnQty) {
            this.ordersItemReturnQty = ordersItemReturnQty;
        }

        public Integer getOrdersItemApplyQty() {
            return ordersItemApplyQty;
        }

        public void setOrdersItemApplyQty(Integer ordersItemApplyQty) {
            this.ordersItemApplyQty = ordersItemApplyQty;
        }

        public String getSingleAmountShow() {
            return singleAmountShow;
        }

        public void setSingleAmountShow(String singleAmountShow) {
            this.singleAmountShow = singleAmountShow;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public void setCurrencyName(String currencyName) {
            this.currencyName = currencyName;
        }
    }
}
