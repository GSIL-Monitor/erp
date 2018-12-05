package com.stosz.plat.wms.bean;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/21]
 */
public class PurchaseUpdateInfoRequest {

   private String orderCode; //采购单号
    private String expressNo;//物流单号

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }
}
