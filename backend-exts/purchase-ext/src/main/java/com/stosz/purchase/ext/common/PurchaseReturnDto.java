package com.stosz.purchase.ext.common;

import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.PurchaseReturnedSpu;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseReturnDto {

    private Integer purchaseId;// 采购单ID

    private ReturnedTypeEnum returnedType;// 退货类型

    private List<PurchaseReturnedSpu> purchaseReturnedSpus;// 采购明细
    
    
    private BigDecimal refundAmount;//退款金额
    
    private String refundAddress;//退款地址
    
    private String shippingName;//物流公司名称
    
    private String memo;//备注
    
    
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundAddress() {
        return refundAddress;
    }

    public void setRefundAddress(String refundAddress) {
        this.refundAddress = refundAddress;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public ReturnedTypeEnum getReturnedType() {
        return returnedType;
    }

    public void setReturnedType(ReturnedTypeEnum returnedType) {
        this.returnedType = returnedType;
    }

    public List<PurchaseReturnedSpu> getPurchaseReturnedSpus() {
        return purchaseReturnedSpus;
    }

    public void setPurchaseReturnedSpus(List<PurchaseReturnedSpu> purchaseReturnedSpus) {
        this.purchaseReturnedSpus = purchaseReturnedSpus;
    }

}
