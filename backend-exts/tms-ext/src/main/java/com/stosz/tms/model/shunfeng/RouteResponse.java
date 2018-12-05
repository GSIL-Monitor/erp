package com.stosz.tms.model.shunfeng;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "RouteResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteResponse {

    @XmlElement(name = "Route",nillable=true)
    private Route route;

    @XmlAttribute(name = "mailno")
    private String mailno;//运单号

    @XmlAttribute(name = "orderid")
    private String orderid;//客户订单号

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
