package com.stosz.purchase.ext.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CreateReturnDto implements Serializable{

    private Integer returnType;// 采退类型

    private Integer purchaseId;// 采购单ID

    private BigDecimal refundAmount;// 退款金额

    private BigDecimal payAmount;// 本次支付金额

    private String refundAddress;// 退货地址

    private Integer shippingId;// 快递公司ID

    private String memo;// 备注

    private List<CreateReturnDetailDto> returnDetails;// 采退明细

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getRefundAddress() {
        return refundAddress;
    }

    public void setRefundAddress(String refundAddress) {
        this.refundAddress = refundAddress;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<CreateReturnDetailDto> getReturnDetails() {
        return returnDetails;
    }

    public void setReturnDetails(List<CreateReturnDetailDto> returnDetails) {
        this.returnDetails = returnDetails;
    }
}
