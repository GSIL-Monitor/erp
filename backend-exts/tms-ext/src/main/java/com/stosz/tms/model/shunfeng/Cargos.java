package com.stosz.tms.model.shunfeng;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "Cargos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cargos implements Serializable {

    @XmlElement(name = "Cargo",nillable=true)
    private List<Cargo> cargos;

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
}
