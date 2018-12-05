package com.stosz.order.ext.dto;

import com.stosz.order.ext.model.OrdersRmaBillItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * 退换货详情页面
 * @author liusl
 */
public class OrdersChangeDetailDTO implements Serializable {
   /**
     * 意义，目的和功能
     */
   private static final long serialVersionUID = 6347664269106799050L;

   private Integer orderId;//退货的订单编号

   private  Integer changeId;//退货ID
   
   private Integer changeTypeEnum;//退换货类型
   
   private String changeReason;//退换货原因
   
   private String changeFrom;//来源
   
   private List<OrdersRmaBillItem> orderItems;//退换货之前的商品

   private List<OrdersRmaBillItem> afterOrderItems;//退换货之后的商品
   
   private boolean recycleGoods;//是否回收商品
   
   private BigDecimal returnAmount;//加收费用/退款金额
   
   private BigDecimal totalAmount;//订单总价

   private String memo;//备注
    
    public Integer getChangeId() {
        return changeId;
    }
    
    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }
    
    public Integer getChangeTypeEnum() {
        return changeTypeEnum;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }


    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setChangeTypeEnum(Integer changeTypeEnum) {
        this.changeTypeEnum = changeTypeEnum;
    }
    
    public String getChangeReason() {
        return changeReason;
    }
    
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
    
    public String getChangeFrom() {
        return changeFrom;
    }
    
    public void setChangeFrom(String changeFrom) {
        this.changeFrom = changeFrom;
    }

    
    public boolean isRecycleGoods() {
        return recycleGoods;
    }

    
    public void setRecycleGoods(boolean recycleGoods) {
        this.recycleGoods = recycleGoods;
    }
    
    public BigDecimal getReturnAmount() {
        return returnAmount;
    }
    
    public void setReturnAmount(BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }
    
    public String getMemo() {
        return memo;
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


    public List<OrdersRmaBillItem> getOrderItems() {
        return orderItems;
    }


    public void setOrderItems(List<OrdersRmaBillItem> orderItems) {
        this.orderItems = orderItems;
    }


    public List<OrdersRmaBillItem> getAfterOrderItems() {
        return afterOrderItems;
    }


    public void setAfterOrderItems(List<OrdersRmaBillItem> afterOrderItems) {
        this.afterOrderItems = afterOrderItems;
    }



}