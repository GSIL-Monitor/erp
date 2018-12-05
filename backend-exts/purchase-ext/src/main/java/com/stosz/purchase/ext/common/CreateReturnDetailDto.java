package com.stosz.purchase.ext.common;

public class CreateReturnDetailDto {

    private Integer purchaseItemId;// 采购明细ID

    private int returnedQty;// 采退数量

    public Integer getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Integer purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public int getReturnedQty() {
        return returnedQty;
    }

    public void setReturnedQty(int returnedQty) {
        this.returnedQty = returnedQty;
    }

}
