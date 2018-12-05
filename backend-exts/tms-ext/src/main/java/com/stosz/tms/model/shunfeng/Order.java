package com.stosz.tms.model.shunfeng;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable {

//    @XmlElement(name = "Cargo",nillable=true)
//    private Cargo cargo;

    @XmlElement(name = "Cargos",nillable=true)
    private Cargos cargos;

    @XmlElement(name = "AddedService",nillable=true)
    private AddedService addedService;

    @XmlAttribute(name = "orderid")
    private String orderid;//String(64) 是 客户订单号

    @XmlAttribute(name = "j_company")
    private String j_company;//String(100) 条件 _SYSTEM 寄件方公司名称，如果需要 生成电子运单，则为必填。

    @XmlAttribute(name = "j_contact")
    private String j_contact;//String(100) 条件 _SYSTEM 寄件方联系人，如果需要生成电子运单，则为必填。

    @XmlAttribute(name = "j_tel")
    private String j_tel;//String(20) 条件 _SYSTEM寄件方联系电话，如果需要 生成电子运单，则为必填。

    @XmlAttribute(name = "j_mobile")
    private String j_mobile;//String(20) 否 寄件方手机

    @XmlAttribute(name = "j_shippercode")
    private String j_shippercode;//String(30) 条件 _SYSTEM寄件方国家/城市代码，如果 是跨境件，则此字段为必 填。

    @XmlAttribute(name = "j_country")
    private String j_country;//String(30) 否 寄方国家

    @XmlAttribute(name = "j_province")
    private String j_province;//String(30) 否 _SYSTEM寄件方所在省份 字段填写要求：必须是标准的省名称称谓 如：广东省，如果是直辖市，请直接传北 京、上海等

    @XmlAttribute(name = "j_city")
    private String j_city;//String(100) 否 _SYSTEM寄件方所在城市名称，字段 填写要求：必须是标准的城市称谓 如：深圳市。

    @XmlAttribute(name = "j_county")
    private String j_county;//String(30) 否寄件人所在县/区，必须是标 准的县/区称谓，示例：“福 田区”。

    @XmlAttribute(name = "j_address")
    private String j_address;//String(200) 条件 _SYSTEM寄件方详细地址，包括省市 区，示例：“广东省深圳市 福田区新洲十一街万基商务大厦 10 楼” ，如果需要生 成电子运单，则为必填。

    @XmlAttribute(name = "j_post_code")
    private String j_post_code;//String(25) 条件寄方邮编，跨境件必填（中 国大陆，港澳台互寄除 外）。

    @XmlAttribute(name = "d_deliverycode")
    private String d_deliverycode;//String(30) 条件到件方代码，如果是跨境 件，则要传这个字段，用于 表示到方国家的城市。如果 此国家整体是以代理商来提供服务的，则此字段可能需 要传国家编码。具体商务沟 通中双方约定。

    @XmlAttribute(name = "d_country")
    private String d_country;//String(30) 否 到方国家

    @XmlAttribute(name = "d_company")
    private String d_company;//String(100) 是 到件方公司名称

    @XmlAttribute(name = "d_contact")
    private String d_contact;//String(100) 是 到件方联系人

    @XmlAttribute(name = "d_tel")
    private String d_tel;//String(20) 是 到件方联系电话

    @XmlAttribute(name = "d_mobile")
    private String d_mobile;//String(20) 否 到件方手机

    @XmlAttribute(name = "d_province")
    private String d_province;

    @XmlAttribute(name = "d_city")
    private String d_city;

    @XmlAttribute(name = "d_county")
    private String d_county;

    @XmlAttribute(name = "d_address")
    private String d_address;

    @XmlAttribute(name = "d_post_code")
    private String d_post_code;//String(25) 条件到方邮编，跨境件必填（中 国大陆，港澳台互寄除 外）。

    @XmlAttribute(name = "custid")
    private String custid;//String(20) 条件 _SYSTEM 顺丰月结卡号

    @XmlAttribute(name = "pay_method")
    private Integer pay_method;//Number(1) 否 1_SYSTEM 付款方式： 1:寄方付 2:收方付 3:第三方付

    @XmlAttribute(name = "express_type")
    private String express_type;//String(5) 否 1_SYSTEM 快件产品类别，详见附录《快件产品类别表》 ，只有 在商务上与顺丰约定的类别方可使用。

    @XmlAttribute(name = "parcel_quantity")
    private Integer parcel_quantity;//Number(5) 否 1包裹数，一个包裹对应一个 运单号，如果是大于 1 个包 裹，则返回则按照子母件的 方式返回母运单号和子运单号。

    @XmlAttribute(name = "cargo_total_weight")
    private Double cargo_total_weight;//Number(10,3) 否订单货物总重量，包含子母 件，单位千克，精确到小数 点后 3 位，如果提供此值必须>0 。

    @XmlAttribute(name = "declared_value")
    private BigDecimal declared_value;//Number(15, 3) 条件客户订单货物总声明价值，包含子母件，精确到小数点 后 3 位。如果是跨境件，则 必填

    @XmlAttribute(name = "declared_value_currency")
    private String declared_value_currency;//String(5) 否如果目的 地是中国大陆的，则默认为 CNY，否 则默认为USD

    @XmlAttribute(name = "sendstarttime")
    private Date sendstarttime;//Date 否BSP 接收到 XML报文的时 间

    @XmlAttribute(name = "remark")
    private String remark;

    public AddedService getAddedService() {
        return addedService;
    }

    public void setAddedService(AddedService addedService) {
        this.addedService = addedService;
    }

//    public Cargo getCargo() {
//        return cargo;
//    }
//
//    public void setCargo(Cargo cargo) {
//        this.cargo = cargo;
//    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getJ_company() {
        return j_company;
    }

    public void setJ_company(String j_company) {
        this.j_company = j_company;
    }

    public String getJ_contact() {
        return j_contact;
    }

    public void setJ_contact(String j_contact) {
        this.j_contact = j_contact;
    }

    public String getJ_tel() {
        return j_tel;
    }

    public void setJ_tel(String j_tel) {
        this.j_tel = j_tel;
    }

    public String getJ_mobile() {
        return j_mobile;
    }

    public void setJ_mobile(String j_mobile) {
        this.j_mobile = j_mobile;
    }

    public String getJ_shippercode() {
        return j_shippercode;
    }

    public void setJ_shippercode(String j_shippercode) {
        this.j_shippercode = j_shippercode;
    }

    public String getJ_country() {
        return j_country;
    }

    public void setJ_country(String j_country) {
        this.j_country = j_country;
    }

    public String getJ_province() {
        return j_province;
    }

    public void setJ_province(String j_province) {
        this.j_province = j_province;
    }

    public String getJ_city() {
        return j_city;
    }

    public void setJ_city(String j_city) {
        this.j_city = j_city;
    }

    public String getJ_county() {
        return j_county;
    }

    public void setJ_county(String j_county) {
        this.j_county = j_county;
    }

    public String getJ_address() {
        return j_address;
    }

    public void setJ_address(String j_address) {
        this.j_address = j_address;
    }

    public String getJ_post_code() {
        return j_post_code;
    }

    public void setJ_post_code(String j_post_code) {
        this.j_post_code = j_post_code;
    }

    public String getD_deliverycode() {
        return d_deliverycode;
    }

    public void setD_deliverycode(String d_deliverycode) {
        this.d_deliverycode = d_deliverycode;
    }

    public String getD_country() {
        return d_country;
    }

    public void setD_country(String d_country) {
        this.d_country = d_country;
    }

    public String getD_company() {
        return d_company;
    }

    public void setD_company(String d_company) {
        this.d_company = d_company;
    }

    public String getD_contact() {
        return d_contact;
    }

    public void setD_contact(String d_contact) {
        this.d_contact = d_contact;
    }

    public String getD_tel() {
        return d_tel;
    }

    public void setD_tel(String d_tel) {
        this.d_tel = d_tel;
    }

    public String getD_mobile() {
        return d_mobile;
    }

    public void setD_mobile(String d_mobile) {
        this.d_mobile = d_mobile;
    }

    public String getD_province() {
        return d_province;
    }

    public void setD_province(String d_province) {
        this.d_province = d_province;
    }

    public String getD_city() {
        return d_city;
    }

    public void setD_city(String d_city) {
        this.d_city = d_city;
    }

    public String getD_county() {
        return d_county;
    }

    public void setD_county(String d_county) {
        this.d_county = d_county;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }

    public String getD_post_code() {
        return d_post_code;
    }

    public void setD_post_code(String d_post_code) {
        this.d_post_code = d_post_code;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getExpress_type() {
        return express_type;
    }

    public void setExpress_type(String express_type) {
        this.express_type = express_type;
    }

    public String getDeclared_value_currency() {
        return declared_value_currency;
    }

    public void setDeclared_value_currency(String declared_value_currency) {
        this.declared_value_currency = declared_value_currency;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPay_method() {
        return pay_method;
    }

    public void setPay_method(Integer pay_method) {
        this.pay_method = pay_method;
    }

    public Integer getParcel_quantity() {
        return parcel_quantity;
    }

    public void setParcel_quantity(Integer parcel_quantity) {
        this.parcel_quantity = parcel_quantity;
    }

    public Double getCargo_total_weight() {
        return cargo_total_weight;
    }

    public void setCargo_total_weight(Double cargo_total_weight) {
        this.cargo_total_weight = cargo_total_weight;
    }

    public BigDecimal getDeclared_value() {
        return declared_value;
    }

    public void setDeclared_value(BigDecimal declared_value) {
        this.declared_value = declared_value;
    }

    public Date getSendstarttime() {
        return sendstarttime;
    }

    public void setSendstarttime(Date sendstarttime) {
        this.sendstarttime = sendstarttime;
    }

    public Cargos getCargos() {
        return cargos;
    }

    public void setCargos(Cargos cargos) {
        this.cargos = cargos;
    }

//    public static void main(String[] args) throws JAXBException {
//
//        Order order = new Order();
//        Cargo cargo = new Cargo();
//        cargo.setName("cargo");
//        order.setRemark("111");
//        order.setCargo(cargo);
//
//        StringWriter writer = new StringWriter();
//
//        JAXBContext jc = JAXBContext.newInstance(Order.class);
//        Marshaller ma = jc.createMarshaller();
//
//        ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        // 去掉生成xml的默认报文头
//        ma.setProperty(Marshaller.JAXB_FRAGMENT, true);
//        ma.marshal(order, writer);
//
//        logger.info(writer.toString());
//
//    }
}
