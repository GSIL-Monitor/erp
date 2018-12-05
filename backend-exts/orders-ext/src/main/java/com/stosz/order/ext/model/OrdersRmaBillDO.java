package com.stosz.order.ext.model;

import com.stosz.order.ext.dto.OrderItemSpuDTO;

import java.util.List;

/**
 * @auther tangtao
 * create time  2018/1/23
 */
public class OrdersRmaBillDO extends OrdersRmaBill {

    private String firstName;
    private String telphone;
    private String address;

    /**
     * spu列表 { title：spu标题，sku:{attr: sku属性, qty: sku数量}}
     */
    private List<OrdersRmaBillItem> itemList;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrdersRmaBillItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrdersRmaBillItem> itemList) {
        this.itemList = itemList;
    }
}
