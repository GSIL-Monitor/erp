package com.stosz.tms.model.yufeng;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "podlist")
@XmlAccessorType(XmlAccessType.FIELD)
public class Podlist {

    @XmlElement(name = "list",nillable=true)
    private List<TrackDetail> list;

    public List<TrackDetail> getList() {
        return list;
    }

    public void setList(List<TrackDetail> list) {
        this.list = list;
    }
}
