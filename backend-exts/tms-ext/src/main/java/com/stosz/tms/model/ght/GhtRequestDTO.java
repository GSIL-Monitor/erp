package com.stosz.tms.model.ght;

import java.io.Serializable;
import java.util.List;

/**
 * 柬埔寨ght 下单请求参数实体
 */
public class GhtRequestDTO implements Serializable{

    private String random;//随机数

    private String sign;

    private String timestamp;//时间戳

    private String userId;//寄件公司在物流商系统中的用户id

    private String cardNo;//订单号

    private String lan;//语言

    private List<Order> orders;

    private String custCode;//同userId

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
