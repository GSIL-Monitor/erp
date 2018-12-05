package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退换货申请
 * @author wangqian
 */
public class OrderChangeApplyParam {

    /**
     * 申请单编号
     */
    private Integer rmaId;

    /**
     * 退换货类型
     */
    private String applyType;
//    private ChangeTypeEnum applyType;

    /**
     * 订单编号
     */
    private Integer orderId;

    /**
     * 退换货原因类型
     */
//    private ChangeReasonEnum changeReason;
    private String changeReason;


    /**
     * 其它原因备注
     */
    private String changeReasonOtherMemo;

    /**
     * 退换货来源类型
     */
//    private RmaSourceEnum rmaSource;
    private String rmaSource;


    /**
     * 是否回收
     */
//    private RecycleGoodsEnum recycleGoods;
    private String recycleGoods;


    /**
     * 问题描述
     */
    private String questionMemo;


    /**
     * 备注
     */
    private String applyMemo;

    /**
     * 退换货运单号
     */
    private String trackingNo;

    /**
     * 退款金额
     */
    private BigDecimal amount;


    /**
     * 状态，草稿还是待审核
     */
//    private OrdersRmaStateEnum rmaState;
    private String rmaState;


    /**
     * 图片
     */
    private List<String> imgs;


    /**
     * 需要退换货的sku及数量
     */
    List<OrderItemQty> orderItemQtyList;


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

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public void setRmaSource(String rmaSource) {
        this.rmaSource = rmaSource;
    }

    public void setRecycleGoods(String recycleGoods) {
        this.recycleGoods = recycleGoods;
    }

    public void setRmaState(String rmaState) {
        this.rmaState = rmaState;
    }

//    public OrdersRmaStateEnum getRmaState() {
//        return rmaState;
//    }
//
//    public void setRmaState(OrdersRmaStateEnum rmaState) {
//        this.rmaState = rmaState;
//    }
//
//    public RecycleGoodsEnum getRecycleGoods() {
//        return recycleGoods;
//    }
//
//    public void setRecycleGoods(RecycleGoodsEnum recycleGoods) {
//        this.recycleGoods = recycleGoods;
//    }

    public String getQuestionMemo() {
        return questionMemo;
    }

    public void setQuestionMemo(String questionMemo) {
        this.questionMemo = questionMemo;
    }

    public String getApplyMemo() {
        return applyMemo;
    }

    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

//    public ChangeTypeEnum getApplyType() {
//        return applyType;
//    }
//
//    public void setApplyType(ChangeTypeEnum applyType) {
//        this.applyType = applyType;
//    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

//    public ChangeReasonEnum getChangeReason() {
//        return changeReason;
//    }
//
//    public void setChangeReason(ChangeReasonEnum changeReason) {
//        this.changeReason = changeReason;
//    }
//
//    public RmaSourceEnum getRmaSource() {
//        return rmaSource;
//    }
//
//    public void setRmaSource(RmaSourceEnum rmaSource) {
//        this.rmaSource = rmaSource;
//    }


    public String getApplyType() {
        return applyType;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public String getRmaSource() {
        return rmaSource;
    }

    public String getRecycleGoods() {
        return recycleGoods;
    }

    public String getRmaState() {
        return rmaState;
    }

    public List<OrderItemQty> getOrderItemQtyList() {
        return orderItemQtyList;
    }

    public void setOrderItemQtyList(List<OrderItemQty> orderItemQtyList) {
        this.orderItemQtyList = orderItemQtyList;
    }

    public static class OrderItemQty{

        /**
         * 订单项
         */
        private Integer orderItemId;

        /**
         * 订单项退货的数量
         */
        private Integer qty;

        public Integer getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(Integer orderItemId) {
            this.orderItemId = orderItemId;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
}
