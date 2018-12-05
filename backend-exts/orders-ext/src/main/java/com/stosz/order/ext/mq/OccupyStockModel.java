package com.stosz.order.ext.mq;

import java.io.Serializable;

/**
 * @auther carter
 * create time    2017-11-09
 * <p>
 * 配货的MQ消息实体；
 *
 * 触发条件，
 * 1）当订单审核通过的时候，会对该订单进行配货；
 * 2）当库存数量增加的时候，会对可配的库存数量进行配货；
 *
 */
public class OccupyStockModel implements Serializable {

    public static final String MESSAGE_TYPE="occupyStock";

    /**可以配置的数量,即可配库存数量*/
    private Integer canAssignQty=0;
    /**仓库id*/
    private Integer warehouseId = 0;
    /**部门*/
    private Integer dept = 0;
    /**sku*/
    private String sku = "";
    /**订单流水号*/
    private Integer orderId = 0;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getDept() {
        return dept;
    }

    public void setDept(Integer dept) {
        this.dept = dept;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCanAssignQty() {
        return canAssignQty;
    }

    public void setCanAssignQty(Integer canAssignQty) {
        this.canAssignQty = canAssignQty;
    }
}
