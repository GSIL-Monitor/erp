package com.stosz.crm.ext.model;

import com.stosz.plat.model.AbstractParamEntity;

import java.io.Serializable;

/**
 * @author 
 */
public class CustomerOrderLog extends AbstractParamEntity implements Serializable {

    private Integer id;

    /**
     * 订单编号
     */
    private Integer orderId;

    /**
     * 手机号
     */
    private String tel;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CustomerOrderLog{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", tel='" + tel + '\'' +
                ", state=" + state +
                '}';
    }
}