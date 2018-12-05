package com.stosz.tms.model.yufeng;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "info")
@XmlAccessorType(XmlAccessType.FIELD)
public class Info {

    @XmlAttribute(name = "ishaspod")
    private String ishaspod;
    @XmlAttribute(name = "ishas")
    private String ishas;
    @XmlAttribute(name = "error")
    private String error;
    @XmlAttribute(name = "jobno")
    private String jobno;
    @XmlAttribute(name = "childjobno")
    private String childjobno;
    @XmlAttribute(name = "orderno")
    private String orderno;
    @XmlAttribute(name = "sddate")
    private String sddate;
    @XmlAttribute(name = "sdoriginname")
    private String sdoriginname;
    @XmlAttribute(name = "pcs")
    private String pcs;
    @XmlAttribute(name = "weig")
    private String weig;
    @XmlAttribute(name = "servicetype")
    private String servicetype;
    @XmlAttribute(name = "signer")
    private String signer;
    @XmlAttribute(name = "okdatetime")
    private String okdatetime;
    @XmlAttribute(name = "picturePath")
    private String picturePath;
    @XmlAttribute(name = "destname")
    private String destname;
    @XmlAttribute(name = "scanstatusnow")
    private String scanstatusnow;

    @XmlElement(name = "podlist",nillable=true)
    private Podlist podlist;

    public String getIshaspod() {
        return ishaspod;
    }

    public void setIshaspod(String ishaspod) {
        this.ishaspod = ishaspod;
    }

    public String getIshas() {
        return ishas;
    }

    public void setIshas(String ishas) {
        this.ishas = ishas;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public String getChildjobno() {
        return childjobno;
    }

    public void setChildjobno(String childjobno) {
        this.childjobno = childjobno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getSddate() {
        return sddate;
    }

    public void setSddate(String sddate) {
        this.sddate = sddate;
    }

    public String getSdoriginname() {
        return sdoriginname;
    }

    public void setSdoriginname(String sdoriginname) {
        this.sdoriginname = sdoriginname;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getWeig() {
        return weig;
    }

    public void setWeig(String weig) {
        this.weig = weig;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getOkdatetime() {
        return okdatetime;
    }

    public void setOkdatetime(String okdatetime) {
        this.okdatetime = okdatetime;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getDestname() {
        return destname;
    }

    public void setDestname(String destname) {
        this.destname = destname;
    }

    public String getScanstatusnow() {
        return scanstatusnow;
    }

    public void setScanstatusnow(String scanstatusnow) {
        this.scanstatusnow = scanstatusnow;
    }

    public Podlist getPodlist() {
        return podlist;
    }

    public void setPodlist(Podlist podlist) {
        this.podlist = podlist;
    }
}
