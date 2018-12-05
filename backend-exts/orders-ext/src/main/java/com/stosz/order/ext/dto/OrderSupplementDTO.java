package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrdersSupplementReasonEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单补发参数实体
 *
 * @auther tangtao
 * create time  2017/12/18
 */
public class OrderSupplementDTO {

    /**
     * 订单id
     */
    private String orderId;
    /**
     * 补发原因
     */
    private OrdersSupplementReasonEnum supplementReasonEnumName;
    /**
     * 补发订单项列表
     */
    private List<String> itemIdArray;
    /**
     * 补发费用
     */
    private String addAmount;
    /**
     * 备注
     */
    private String memo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrdersSupplementReasonEnum getSupplementReasonEnumName() {
        return supplementReasonEnumName;
    }

    public void setSupplementReasonEnumName(OrdersSupplementReasonEnum supplementReasonEnumName) {
        this.supplementReasonEnumName = supplementReasonEnumName;
    }

    public List<String> getItemIdArray() {
        return itemIdArray;
    }

    public void setItemIdArray(List<String> itemIdArray) {
        this.itemIdArray = itemIdArray;
    }

    public String getAddAmount() {
        return addAmount;
    }

    public void setAddAmount(String addAmount) {
        this.addAmount = addAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
