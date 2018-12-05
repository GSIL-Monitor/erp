package com.stosz.order.ext.dto;

/**
 * 订单推送到建站
 */
public class OrderFrontDTO {

    /**
     * 订单号
     */
    private String order_no;

    /**
     * 订单状态
     */
    private int status;

    /**
     * 运单号
     */
    private String track_number;


    /**
     * 运单产生时间
     */
    private long track_time;


    /**
     * 物流公司名称
     */
    private String logistics_name;


    /**
     * 备注
     */
    private String remark;


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTrack_number() {
        return track_number;
    }

    public void setTrack_number(String track_number) {
        this.track_number = track_number;
    }

    public long getTrack_time() {
        return track_time;
    }

    public void setTrack_time(long track_time) {
        this.track_time = track_time;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return "OrderFrontDTO{" +
                "order_no='" + order_no + '\'' +
                ", status=" + status +
                ", track_number='" + track_number + '\'' +
                ", track_time=" + track_time +
                ", logistics_name='" + logistics_name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
