package com.stosz.order.ext.dto;

/**
 * 
 * 批量退货操作用到的实体
 * @author liusl
 */
public class OrdersChangeExport {

    private Integer orderId =  new Integer(0);   //订单号
    
    private String orderTrack;//订单的运单
    
    private Integer changeId;//退换货的主键ID
    
    private String applyStatus;//退换货状态
    
    private String changeReason;//退换货原因

    private String changeFrom;//退换货来源
    
    private Integer changeType = new Integer(0);//退换货类型
    
    
    public Integer getOrderId() {
        return orderId;
    }


    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
    


    
    public Integer getChangeType() {
        return changeType;
    }



    public String getOrderTrack() {
        return orderTrack;
    }

    
    public void setOrderTrack(String orderTrack) {
        this.orderTrack = orderTrack;
    }


    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    
    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
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

    
}
