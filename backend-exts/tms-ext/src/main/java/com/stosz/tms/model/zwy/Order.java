package com.stosz.tms.model.zwy;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlElement(name = "OrderNo",nillable=true)
    private String OrderNo; //订单号

    @XmlElement(name = "desCity",nillable=true)
    private String desCity; //String(50) Mandatory 目的城市名

    @XmlElement(name = "desSreet",nillable=true)
    private String desSreet; //String(50) Mandatory 目的城市名

    @XmlElement(name = "agent",nillable=true)
    private String agent; //String(8) Mandatory 渠道(详见四、大包规则)

//    @XmlElement(name = "servicetype",nillable=true)
//    private String servicetype;//String(100) Optional 服务类型(详见四、大包规则)

    @XmlElement(name = "cod",nillable=true)
    private String cod; //String(8) Optional 是否货到付款（Y 是， N 否）

    @XmlElement(name = "codValue",nillable=true)
    private Long codValue; //Long Optional 代收金额，货到付款则必须填写,否则可以为空

    @XmlElement(name = "codCurrency",nillable=true)
    private String codCurrency; //String(50) 币种编码， （国家及币种编码

    @XmlElement(name = "desCountry",nillable=true)
    private String desCountry;//String(2) Mandatory 目的国家二字码（见国家及二字码.xlsx）

//    @XmlElement(name = "trackingNo",nillable=true)
//    private String trackingNo; //String(50) Optional 追踪号

    @XmlElement(name = "sac_id",nillable=true)
    private String sac_id; // String(3) Mandatory 口岸(外运提供)

    @XmlElement(name = "shipperEN",nillable=true)
    private String shipperEN;// String(50) Mandatory 发件人 英文

//    @XmlElement(name = "shippingCompanyEN",nillable=true)
//    private String shippingCompanyEN;//String(100) Optional 发件公司 英文

//    @XmlElement(name = "shippingCompanyCN",nillable=true)
//    private String shippingCompanyCN; //String(100) Optional 发件公司 中文

    @XmlElement(name = "shipperAddressEN",nillable=true)
    private String shipperAddressEN; //String(600) Mandatory 发件人地址 英文

    @XmlElement(name = "depCity",nillable=true)
    private String depCity; //String(80) Mandatory 始发城市 英文

    @XmlElement(name = "depState",nillable=true)
    private String depState; //String(80) Optional 始发省 英文

    @XmlElement(name = "shipperPhone",nillable=true)
    private String shipperPhone; //String(50) Mandatory 发件人电话

//    @XmlElement(name = "receiveCompanyEN",nillable=true)
//    private String receiveCompanyEN; //String(100) Optional 发件公司 英文

    @XmlElement(name = "receiverEN",nillable=true)
    private String receiverEN; //String(50) Mandatory 收件人 英文

    @XmlElement(name = "receiverAddressEN",nillable=true)
    private String receiverAddressEN; //String(600) Mandatory 收件人地址 英文

    @XmlElement(name = "receiverPost",nillable=true)
    private String receiverPost; //String(20) Mandatory邮编 （不同渠道对邮编有不同要求，详见附录 五)

    @XmlElement(name = "receiverPhone",nillable=true)
    private String receiverPhone; //String(50) Mandatory 收件人电话

//    @XmlElement(name = "receiverEmail",nillable=true)
//    private String receiverEmail; //String(100) Optional 收件人邮箱

//    @XmlElement(name = "cargoType",nillable=true)
//    private String cargoType; //String(2) Optional 货物类型： MN 文件； ND 包裹

    @XmlElement(name = "declareValue",nillable=true)
    private Long declareValue; //Long Mandatory 海关申报总价值，单位美元，保留两位小数

    @XmlElement(name = "actualWeight",nillable=true)
    private Long actualWeight; //Long Mandatory 预报总实重，单位 KG，保留三位小数

    @XmlElement(name = "pieces",nillable=true)
    private Integer pieces; //int Mandatory 总件数

//    @XmlElement(name = "desCity3C",nillable=true)
//    private String desCity3C; //String(3) Optional 目的地城市三字码，见“城市三字码.xlsx

    @XmlElement(name = "desState",nillable=true)
    private String desState; //String(10) Optional 目的省

    @XmlElement(name = "desSteet",nillable=true)
    private String desSteet; //String(100) Optional 目的街道

//    @XmlElement(name = "Status",nillable=true)
//    private String Status; //返回的状态

//    @XmlElement(name = "SinoairNO",nillable=true)
//    private String SinoairNO; //返回的运单号

//    @XmlElement(name = "pdfUrl",nillable=true)
//    private String pdfUrl; //返回的扩展

//    @XmlElement(name = "Comments",nillable=true)
//    private String Comments; //返回的备注

//    @XmlElement(name = "reasonCode",nillable=true)
//    private String reasonCode; //返回的错误状态码

    @XmlElement(name = "Items",nillable=true)
    private Items items;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getDesCity() {
        return desCity;
    }

    public void setDesCity(String desCity) {
        this.desCity = desCity;
    }

    public String getDesSreet() {
        return desSreet;
    }

    public void setDesSreet(String desSreet) {
        this.desSreet = desSreet;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Long getCodValue() {
        return codValue;
    }

    public void setCodValue(Long codValue) {
        this.codValue = codValue;
    }

    public String getCodCurrency() {
        return codCurrency;
    }

    public void setCodCurrency(String codCurrency) {
        this.codCurrency = codCurrency;
    }

    public String getDesCountry() {
        return desCountry;
    }

    public void setDesCountry(String desCountry) {
        this.desCountry = desCountry;
    }

    public String getSac_id() {
        return sac_id;
    }

    public void setSac_id(String sac_id) {
        this.sac_id = sac_id;
    }

    public String getShipperEN() {
        return shipperEN;
    }

    public void setShipperEN(String shipperEN) {
        this.shipperEN = shipperEN;
    }

    public String getShipperAddressEN() {
        return shipperAddressEN;
    }

    public void setShipperAddressEN(String shipperAddressEN) {
        this.shipperAddressEN = shipperAddressEN;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getDepState() {
        return depState;
    }

    public void setDepState(String depState) {
        this.depState = depState;
    }

    public String getShipperPhone() {
        return shipperPhone;
    }

    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }

    public String getReceiverEN() {
        return receiverEN;
    }

    public void setReceiverEN(String receiverEN) {
        this.receiverEN = receiverEN;
    }

    public String getReceiverAddressEN() {
        return receiverAddressEN;
    }

    public void setReceiverAddressEN(String receiverAddressEN) {
        this.receiverAddressEN = receiverAddressEN;
    }

    public String getReceiverPost() {
        return receiverPost;
    }

    public void setReceiverPost(String receiverPost) {
        this.receiverPost = receiverPost;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Long getDeclareValue() {
        return declareValue;
    }

    public void setDeclareValue(Long declareValue) {
        this.declareValue = declareValue;
    }

    public Long getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Long actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public String getDesState() {
        return desState;
    }

    public void setDesState(String desState) {
        this.desState = desState;
    }

    public String getDesSteet() {
        return desSteet;
    }

    public void setDesSteet(String desSteet) {
        this.desSteet = desSteet;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
