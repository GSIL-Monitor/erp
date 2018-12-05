package com.stosz.tms.model.shunfeng;

import org.omg.PortableInterceptor.INACTIVE;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RouteRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteRequest {

    @XmlAttribute(name = "tracking_type")
    private Integer tracking_type;//Number(2) 否 默认1 1：顺丰单号,2：客户订单

    @XmlAttribute(name = "tracking_number")
    private String tracking_number;//String(4000) 是 有多单号以逗号隔开

    @XmlAttribute(name = "method_type")
    private Integer method_type;//Number(1) 否 1路由查询类别： 1：标准路由查询 2：定制路由查询

    public Integer getTracking_type() {
        return tracking_type;
    }

    public void setTracking_type(Integer tracking_type) {
        this.tracking_type = tracking_type;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public Integer getMethod_type() {
        return method_type;
    }

    public void setMethod_type(Integer method_type) {
        this.method_type = method_type;
    }
}
