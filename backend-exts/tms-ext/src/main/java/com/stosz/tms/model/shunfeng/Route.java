package com.stosz.tms.model.shunfeng;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement(name = "Route")
@XmlAccessorType(XmlAccessType.FIELD)
public class Route {

    @XmlAttribute(name = "accept_time")
    private Date accept_time;//节点时间

    @XmlAttribute(name = "accept_address")
    private String accept_address;//节点地点

    @XmlAttribute(name = "remark")
    private String  remark;

    @XmlAttribute(name = "opcode")
    private String opcode;

    public Date getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(Date accept_time) {
        this.accept_time = accept_time;
    }

    public String getAccept_address() {
        return accept_address;
    }

    public void setAccept_address(String accept_address) {
        this.accept_address = accept_address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }
}
