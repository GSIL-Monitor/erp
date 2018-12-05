package com.stosz.tms.model.yufeng;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "list")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackDetail {

    @XmlAttribute(name = "scandate")
    private String scandate;

    @XmlAttribute(name = "scandime")
    private String scandime;

    @XmlAttribute(name = "scanstation")
    private String scanstation;

    @XmlAttribute(name = "scanstatusname")
    private String scanstatusname;

    public String getScandate() {
        return scandate;
    }

    public void setScandate(String scandate) {
        this.scandate = scandate;
    }

    public String getScandime() {
        return scandime;
    }

    public void setScandime(String scandime) {
        this.scandime = scandime;
    }

    public String getScanstation() {
        return scanstation;
    }

    public void setScanstation(String scanstation) {
        this.scanstation = scanstation;
    }

    public String getScanstatusname() {
        return scanstatusname;
    }

    public void setScanstatusname(String scanstatusname) {
        this.scanstatusname = scanstatusname;
    }
}
