package com.stosz.plat.wms.bean;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/20]
 */
public class PurchaseCreateOrUpdate {
    private PurchaseHeadInfoBean head;

    public PurchaseHeadInfoBean getHead() {
        return head;
    }

    public void setHead(PurchaseHeadInfoBean head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "PurchaseCreateOrUpdate{" +
                "head=" + head +
                '}';
    }
}
