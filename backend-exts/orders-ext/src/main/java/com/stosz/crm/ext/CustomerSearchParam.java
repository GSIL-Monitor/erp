package com.stosz.crm.ext;

import com.stosz.crm.ext.enums.CodeTypeEnum;
import com.stosz.crm.ext.enums.CustomerCreditEnum;

import java.time.LocalDateTime;

public class CustomerSearchParam {

    private String telphone;

    private CustomerCreditEnum levelEnum;

    private String zoneCode;

    private Integer zoneId;

    private Integer acceptQty;

    private Integer rejectQty;

    private CodeTypeEnum codeType;

    private String keyWord;

    private Integer start;

    private Integer limit;

    private LocalDateTime minCreateAt;

    private LocalDateTime maxCreateAt;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public Integer getAcceptQty() {
        return acceptQty;
    }

    public void setAcceptQty(Integer acceptQty) {
        this.acceptQty = acceptQty;
    }

    public Integer getRejectQty() {
        return rejectQty;
    }

    public void setRejectQty(Integer rejectQty) {
        this.rejectQty = rejectQty;
    }

    public CustomerCreditEnum getLevelEnum() {
        return levelEnum;
    }

    public void setLevelEnum(CustomerCreditEnum levelEnum) {
        this.levelEnum = levelEnum;
    }

    public CodeTypeEnum getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeTypeEnum codeType) {
        this.codeType = codeType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public LocalDateTime getMinCreateAt() {
        return minCreateAt;
    }

    public void setMinCreateAt(LocalDateTime minCreateAt) {
        this.minCreateAt = minCreateAt;
    }

    public LocalDateTime getMaxCreateAt() {
        return maxCreateAt;
    }

    public void setMaxCreateAt(LocalDateTime maxCreateAt) {
        this.maxCreateAt = maxCreateAt;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }
}
