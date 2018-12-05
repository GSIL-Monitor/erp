package com.stosz.tms.model.zwy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "WSGET")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wsget {

    @XmlElement(name = "AccessRequest",nillable=true)
    private AccessRequest accessRequest;

    @XmlElement(name = "Orders",nillable=true)
    private Orders orders;

    public AccessRequest getAccessRequest() {
        return accessRequest;
    }

    public void setAccessRequest(AccessRequest accessRequest) {
        this.accessRequest = accessRequest;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
