package com.stosz.tms.model.imile;

import java.io.Serializable;
import java.util.Date;

public class ImileRequestDto implements Serializable{
    //客户订单号	String	20	必填
    private String logisticsId;

    //批次号	String	20	必填
    private String dicCode;

    //录单时间	Date	30	必填
    private Date orderCreateTime;

    //寄件人	String	30	必填
    private String senderName;

    //寄件公司	String	200	必填
    private String senderCompany;

    //寄件地址	String	400	必填
    private String senderAddress;

    //寄件电话	String	200	必填
    private String senderMobile;

    //寄件区/县	String	30
    private String senderCounty;

    //寄件市	String	50
    private String senderCity;

    //寄件省份	String	30
    private String senderProvince;

    //收件人	String	100	必填
    private String receiverName;

    //收件公司	String	200	必填
    private String receiverCompany;

    //收件人区/县	String	30	必填
    private String receiverCounty;

    //收件市	String	50	必填
    private String receiverCity;

    //收件省份	String	30	必填
    private String receiverProvince;

    //收件地址	String	400	必填
    private String receiverAddress;

    //收件电话	String	200	必填
    private String receiverMobile;

    //件数	Long	10	必填
    private Long pieceNumber;

    //录单重量	Double	10	必填
    private Double packageWeight;

    //支付方式	String	30	必填
    private String paymentType;

    //代收货款	Double	10
    private Double goodsPayment;

    //备注	String	200
    private String remark;

    //物品名称	String	100	必填
    private String goodsName;

    //数据来源	String	30	必填
    private String orderSource;

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderCompany() {
        return senderCompany;
    }

    public void setSenderCompany(String senderCompany) {
        this.senderCompany = senderCompany;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getSenderCounty() {
        return senderCounty;
    }

    public void setSenderCounty(String senderCounty) {
        this.senderCounty = senderCounty;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }

    public String getReceiverCounty() {
        return receiverCounty;
    }

    public void setReceiverCounty(String receiverCounty) {
        this.receiverCounty = receiverCounty;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public Long getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(Long pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public Double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Double packageWeight) {
        this.packageWeight = packageWeight;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getGoodsPayment() {
        return goodsPayment;
    }

    public void setGoodsPayment(Double goodsPayment) {
        this.goodsPayment = goodsPayment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }
}
