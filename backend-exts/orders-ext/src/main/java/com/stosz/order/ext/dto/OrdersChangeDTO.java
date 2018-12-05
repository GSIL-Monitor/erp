package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.ChangeReasonEnum;
import com.stosz.order.ext.enums.ChangeTypeEnum;
import com.stosz.order.ext.enums.OrdersRmaStateEnum;
import com.stosz.order.ext.enums.RmaSourceEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 
 * 退换货列表
 * @author liusl
 */
public class OrdersChangeDTO implements Serializable {
    /**
     * 意义，目的和功能
     */
    private static final long serialVersionUID = 655731992050966451L;
    /**自增主键*/

    private Long    id  =   new Long(0);
    /**订单编号*/
    private String  merchantOrderNo =   "";
    /**运单编号*/
    private String  trackingNo  =   "";
    /**商品信息*/
    private String orderInnerTitle;
    /**带符号的总价*/
    private String amountShow;
    /**物流企业*/
    private String logisticsName;
    /**业务部门*/
    private String departmentUserInfo;
    /**域名*/
    private String domain;
    /**是0退货还是1换货 */
    private String changeTypeEnum;
    /**是否回收商品*/
    private String recycleGoods;
    /**退款金额*/
    private java.math.BigDecimal    returnAmount    =   new java.math.BigDecimal(0);
    /**申请来源*/
    private String  applyFrom   =   "";
    /**退换货的状态*/
    private String applyStatusEnum;
    /** 订单Id*/
    private Integer orderId;
    /**退换货方式*/
    private String  changeWay   =   "";
    /**退换货原因*/
    private String  changeReason;
    /**申请人*/
    private String applyUserId;
    /**申请时间*/
    private java.time.LocalDateTime applyTime   =   java.time.LocalDateTime.now();
    /**申请备注*/
    private String  applyMemo   =   "";
    /**审核人id*/
    private Integer checkUserId =   new Integer(0);
    /**审核时间*/
    private java.time.LocalDateTime checkTime   =   java.time.LocalDateTime.now();
    /**审核备注*/
    private String  checkMemo   =   "";

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }
    
    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }
    
    public String getTrackingNo() {
        return trackingNo;
    }
    
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }
    
    public String getOrderInnerTitle() {
        return orderInnerTitle;
    }
    
    public void setOrderInnerTitle(String orderInnerTitle) {
        this.orderInnerTitle = orderInnerTitle;
    }
    
    public String getAmountShow() {
        return amountShow;
    }
    
    public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
    }
    
    
    public String getDepartmentUserInfo() {
        return departmentUserInfo;
    }
    
    public void setDepartmentUserInfo(String departmentUserInfo) {
        this.departmentUserInfo = departmentUserInfo;
    }
    
    public String getDomain() {
        return domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    
    public String getLogisticsName() {
        return logisticsName;
    }

    
    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    
    public String getChangeTypeEnum() {
        if(StringUtils.isNumeric(changeTypeEnum)){
            return ChangeTypeEnum.fromId(Integer.parseInt(changeTypeEnum)).display();
        }
        return  "";
    }

    
    public void setChangeTypeEnum(String changeTypeEnum) {
        this.changeTypeEnum = changeTypeEnum;
    }

    
    public String getRecycleGoods() {
        if(StringUtils.isNumeric(recycleGoods)){
            return ChangeTypeEnum.fromId(Integer.parseInt(recycleGoods)).display();
        }
        return recycleGoods;
    }

    
    public void setRecycleGoods(String recycleGoods) {
        this.recycleGoods = recycleGoods;
    }

    public java.math.BigDecimal getReturnAmount() {
        return returnAmount;
    }
    
    public void setReturnAmount(java.math.BigDecimal returnAmount) {
        this.returnAmount = returnAmount;
    }
    
    public String getApplyFrom() {
        if(StringUtils.isNumeric(applyFrom) ){
            return RmaSourceEnum.fromId(Integer.parseInt(applyFrom)).display();
            
        }
        return  "";    
    }
    
    public void setApplyFrom(String applyFrom) {
        this.applyFrom = applyFrom;
    }
    
    public String getApplyStatusEnum() {
        if(StringUtils.isNumeric(applyStatusEnum)){
            return OrdersRmaStateEnum.fromId(Integer.parseInt(applyStatusEnum)).display();
        }
        return  "";
        
    }
    
    public void setApplyStatusEnum(String applyStatusEnum) {
        this.applyStatusEnum = applyStatusEnum;
    }
    
    public String getChangeWay() {
        return changeWay;
    }
    
    public void setChangeWay(String changeWay) {
        this.changeWay = changeWay;
    }
    
    public String getChangeReason() {
        if(StringUtils.isNumeric(changeReason)){
            return ChangeReasonEnum.fromId(Integer.parseInt(changeReason)).display();
        }
            return  "";
    }
    
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
    
    public String getApplyUserId() {
        return applyUserId;
    }
    
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }
    
    public java.time.LocalDateTime getApplyTime() {
        return applyTime;
    }
    
    public void setApplyTime(java.time.LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }
    
    public String getApplyMemo() {
        return applyMemo;
    }
    
    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }
    
    public Integer getCheckUserId() {
        return checkUserId;
    }
    
    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }
    
    public java.time.LocalDateTime getCheckTime() {
        return checkTime;
    }
    
    public void setCheckTime(java.time.LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }
    
    public String getCheckMemo() {
        return checkMemo;
    }
    
    public void setCheckMemo(String checkMemo) {
        this.checkMemo = checkMemo;
    }
    
}