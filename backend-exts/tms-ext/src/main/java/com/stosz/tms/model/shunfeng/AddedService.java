package com.stosz.tms.model.shunfeng;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddedService")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddedService {

    @XmlAttribute(name = "name")
    private String name;//String(20) 是 增值服务名，如 COD 等。

    @XmlAttribute(name = "value")
    private String value;//String(30)

    @XmlAttribute(name = "value1")
    private String value1;//String(30) 条件 增值服务扩展属性

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }
}
