package com.stosz.tms.model.indonesiajt;

import java.io.Serializable;
import java.util.List;

public class LogisticsDto implements Serializable{

    private String eccompanyid;//电商标识 String(20)	Jollychic

    private String logisticproviderid;//物流公司代码	String(20)	JNT

    private String customerid;//客户标识	String(20)	Jollychic

    private String txlogisticid;//物流订单号	String(20)	Jollychic

    private String mailno;//运单编号	String(20)	JS0000001331

    private String ordertype;//订单类型	String(20)	0-COD,1-普通订单，3-退货单, 4-到付单

    private String servicetype;//服务类型	String(20)	0-自己联系 1-在线下单(上门揽收) 4-限时物流 8-快捷COD 16-快递保障 5-自提 6-送货上门

    private Sender sender;

    private Receiver receiver;

    private List<Item> items;

    private String weight;

    private String remark;

    private String createordertime;//订单创建时间

    private String sendstarttime;//物流公司上门取货时间段

    private String sendendtime;//物流公司上门取货时间段

    private String goodsvalue;//商品金额

    private String itemsvalue;//代收货款金额

    private String special;

    private String paytype;//0:现金，1：刷卡，2：一部分现金，一部分刷卡,默认为0

    public String getCreateordertime() {
        return createordertime;
    }

    public void setCreateordertime(String createordertime) {
        this.createordertime = createordertime;
    }

    public String getSendstarttime() {
        return sendstarttime;
    }

    public void setSendstarttime(String sendstarttime) {
        this.sendstarttime = sendstarttime;
    }

    public String getSendendtime() {
        return sendendtime;
    }

    public void setSendendtime(String sendendtime) {
        this.sendendtime = sendendtime;
    }

    public String getGoodsvalue() {
        return goodsvalue;
    }

    public void setGoodsvalue(String goodsvalue) {
        this.goodsvalue = goodsvalue;
    }

    public String getItemsvalue() {
        return itemsvalue;
    }

    public void setItemsvalue(String itemsvalue) {
        this.itemsvalue = itemsvalue;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getEccompanyid() {
        return eccompanyid;
    }

    public void setEccompanyid(String eccompanyid) {
        this.eccompanyid = eccompanyid;
    }

    public String getLogisticproviderid() {
        return logisticproviderid;
    }

    public void setLogisticproviderid(String logisticproviderid) {
        this.logisticproviderid = logisticproviderid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getTxlogisticid() {
        return txlogisticid;
    }

    public void setTxlogisticid(String txlogisticid) {
        this.txlogisticid = txlogisticid;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
