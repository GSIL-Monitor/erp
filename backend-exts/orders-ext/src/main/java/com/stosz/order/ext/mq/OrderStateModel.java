package com.stosz.order.ext.mq;

import java.io.Serializable;

/**
 * @auther carter
 * create time    2017-12-12
 * 订单状态上报数据实体；
 *order_front_orderStatusChange
 */
public class OrderStateModel implements Serializable{

    public static final String messageType = "orderStatusChange";

    /**订单号*******************/
    private String order_no;
    /**订单状态 0 待审*******************/
    private Integer status;
    /**运单号*******************/
    private String  track_number ;
    /**物流公司名称*******************/
    private String  logistics_name;
    /**运单产生时间*******************/
    private long    track_time;
    /**备注*******************/
    private String  remark;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTrack_number() {
        return track_number;
    }

    public void setTrack_number(String track_number) {
        this.track_number = track_number;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public long getTrack_time() {
        return track_time;
    }

    public void setTrack_time(long track_time) {
        this.track_time = track_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
