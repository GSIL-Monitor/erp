package com.stosz.order.ext.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @auther carter
 * create time    2017-12-25
 * 订单对应的sku的订单需求数量
 */
public class DeptOrderQtyTimeDto implements Serializable {

    /**部门id*************/
    private Integer dept = 0;
    /**订单id*************/
    private Integer orderId=0;
    /**需求数量*************/
    private Integer qty  = 0;
    /**分配数量*************/
    private Integer assignQty = 0;
    /**创建时间*************/
    private LocalDateTime createAt=null;
    /**是否分配*************/
    private boolean assign = false;

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
        this.assignQty = qty;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isAssign() {
        return assign;
    }

    public void setAssign(boolean assign) {
        this.assign = assign;
    }

    public Integer getAssignQty() {
        if(!isAssign()) assignQty = 0;
        return assignQty;
    }

    public void setAssignQty(Integer assignQty) {
        this.assignQty = assignQty;
    }
}
