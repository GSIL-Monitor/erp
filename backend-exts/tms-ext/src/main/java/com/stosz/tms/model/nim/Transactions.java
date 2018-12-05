package com.stosz.tms.model.nim;

import java.io.Serializable;
import java.util.Date;

public class Transactions implements Serializable{
    private Long route_id;
    private Long  dnr_id;
    private Long distribution_point_id;
    private Date start_time;
    private Date end_time;
    private Date service_start_time;
    private Date service_end_time;
    private Date type;
    private Integer transit;
    private String status;//成功状态
    private Date estimated_date;
    private Signature signature;

    public Long getRoute_id() {
        return route_id;
    }

    public void setRoute_id(Long route_id) {
        this.route_id = route_id;
    }

    public Long getDnr_id() {
        return dnr_id;
    }

    public void setDnr_id(Long dnr_id) {
        this.dnr_id = dnr_id;
    }

    public Long getDistribution_point_id() {
        return distribution_point_id;
    }

    public void setDistribution_point_id(Long distribution_point_id) {
        this.distribution_point_id = distribution_point_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getService_start_time() {
        return service_start_time;
    }

    public void setService_start_time(Date service_start_time) {
        this.service_start_time = service_start_time;
    }

    public Date getService_end_time() {
        return service_end_time;
    }

    public void setService_end_time(Date service_end_time) {
        this.service_end_time = service_end_time;
    }

    public Date getType() {
        return type;
    }

    public void setType(Date type) {
        this.type = type;
    }

    public Integer getTransit() {
        return transit;
    }

    public void setTransit(Integer transit) {
        this.transit = transit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEstimated_date() {
        return estimated_date;
    }

    public void setEstimated_date(Date estimated_date) {
        this.estimated_date = estimated_date;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }
}
