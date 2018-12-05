package com.stosz.order.ext.model;

/**
 *
 *  当前订单项可退数量
 *
 *  @author wangqian
 *  @data 2018年1月10日
 */
public class OrderRmaItemReturnQty {

    private Integer id;

    private Integer qty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderRmaItemReturnQty{" +
                "id=" + id +
                ", qty=" + qty +
                '}';
    }
}
