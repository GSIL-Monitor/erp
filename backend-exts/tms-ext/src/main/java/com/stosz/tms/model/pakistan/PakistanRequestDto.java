package com.stosz.tms.model.pakistan;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 皇家巴基斯坦下单请求实体
 */
public class PakistanRequestDto implements Serializable{

    private Integer Type;//必须	1为仓储订单 2为普通订单	2
    private Integer INorOut;//必须	0 出口 1 进口	0
    private String WarehouseId;//选填	仓储订单为必填	302
    private String CsRefNo;//选填	客户参考号(可填平台订单号/单号)	201710100001
    private String CustomerId;//必填	客户ID	80000
    private String ChannelId;//必须	渠道ID	91
    private String Sender;//选填	发件人	PFCexpress
    private String SendAddress;//选填	发件人地址	San Fernando
    private String SendPhone;//选填	发件人电话	400-66566-88888
    private String SendEmail;//选填	发件人邮箱	sales@pfcexpress.com
    private String SendCompany;//选填	发件人公司	pfc.inc
    private String ShipToName;//必填	收件人	zhangshan
    private String ShipToCountry;//必填	收件人国家二字码	US
    private String ShipToState;//必填	收件人州	UT
    private String ShipToCity;//必填	收件人城市	Cedar City
    private String ShipToAdress1;//必填	收件人地址1	1225 W Harding Ave Apt 32
    private String ShipToAdress2;//选填	收件人地址2
    private String ShipToZipCode;//必须	收件人邮编	84720
    private String ShipToCompanyName;//选填	收件人公司	Company.inc
    private String ShipToEmail;//选填	收件人邮编	Email@me.com
    private Integer OrderStatus;//必须	订单状态 1草稿 3确认	1
    private String TrackingNo;//选填	追踪号
    private String BatteryFlag;//选填	是否带电池 0 不带 1 带点 默认0	0
    private BigDecimal CODFee;//选填	代收货款金额	500.00
    private String IDCardNo;//选填	身份证号码 进口订单 必填
    private String Remark;//可选	备注
    private List<Product> Products;//必须	订单明细
    private String ShipToPhoneNumber;//ShipToPhoneNumber

    public String getShipToPhoneNumber() {
        return ShipToPhoneNumber;
    }

    public void setShipToPhoneNumber(String shipToPhoneNumber) {
        ShipToPhoneNumber = shipToPhoneNumber;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getINorOut() {
        return INorOut;
    }

    public void setINorOut(Integer INorOut) {
        this.INorOut = INorOut;
    }

    public String getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        WarehouseId = warehouseId;
    }

    public String getCsRefNo() {
        return CsRefNo;
    }

    public void setCsRefNo(String csRefNo) {
        CsRefNo = csRefNo;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getChannelId() {
        return ChannelId;
    }

    public void setChannelId(String channelId) {
        ChannelId = channelId;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getSendAddress() {
        return SendAddress;
    }

    public void setSendAddress(String sendAddress) {
        SendAddress = sendAddress;
    }

    public String getSendPhone() {
        return SendPhone;
    }

    public void setSendPhone(String sendPhone) {
        SendPhone = sendPhone;
    }

    public String getSendEmail() {
        return SendEmail;
    }

    public void setSendEmail(String sendEmail) {
        SendEmail = sendEmail;
    }

    public String getSendCompany() {
        return SendCompany;
    }

    public void setSendCompany(String sendCompany) {
        SendCompany = sendCompany;
    }

    public String getShipToName() {
        return ShipToName;
    }

    public void setShipToName(String shipToName) {
        ShipToName = shipToName;
    }

    public String getShipToCountry() {
        return ShipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        ShipToCountry = shipToCountry;
    }

    public String getShipToState() {
        return ShipToState;
    }

    public void setShipToState(String shipToState) {
        ShipToState = shipToState;
    }

    public String getShipToCity() {
        return ShipToCity;
    }

    public void setShipToCity(String shipToCity) {
        ShipToCity = shipToCity;
    }

    public String getShipToAdress1() {
        return ShipToAdress1;
    }

    public void setShipToAdress1(String shipToAdress1) {
        ShipToAdress1 = shipToAdress1;
    }

    public String getShipToAdress2() {
        return ShipToAdress2;
    }

    public void setShipToAdress2(String shipToAdress2) {
        ShipToAdress2 = shipToAdress2;
    }

    public String getShipToZipCode() {
        return ShipToZipCode;
    }

    public void setShipToZipCode(String shipToZipCode) {
        ShipToZipCode = shipToZipCode;
    }

    public String getShipToCompanyName() {
        return ShipToCompanyName;
    }

    public void setShipToCompanyName(String shipToCompanyName) {
        ShipToCompanyName = shipToCompanyName;
    }

    public String getShipToEmail() {
        return ShipToEmail;
    }

    public void setShipToEmail(String shipToEmail) {
        ShipToEmail = shipToEmail;
    }

    public Integer getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getTrackingNo() {
        return TrackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        TrackingNo = trackingNo;
    }

    public String getBatteryFlag() {
        return BatteryFlag;
    }

    public void setBatteryFlag(String batteryFlag) {
        BatteryFlag = batteryFlag;
    }

    public BigDecimal getCODFee() {
        return CODFee;
    }

    public void setCODFee(BigDecimal CODFee) {
        this.CODFee = CODFee;
    }

    public String getIDCardNo() {
        return IDCardNo;
    }

    public void setIDCardNo(String IDCardNo) {
        this.IDCardNo = IDCardNo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }
}
