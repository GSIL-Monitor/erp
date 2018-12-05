package com.stosz.tms.model.pakistan;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable{
    private String SKU;//必填	SKU	SKU0001
    private String EnName;//必填	英文品名	Reflective clothes
    private String CnName;//选填	中文品名	反光衣
    private Integer	 MaterialQuantity;//必填	数量(/个)	1
    private BigDecimal Price;//必填	申报价值(USD/个)	10.00
    private BigDecimal Weight;//	必填	重量(KG/个)	0.5
    private BigDecimal Length;//选填	长(/CM)	0.5
    private BigDecimal Width;//选填	宽(/CM)	0.5
    private BigDecimal High;//选填	高(/CM)	0.5
    private String ProducingArea;//选填	产地	CN
    private String HSCode;//选填	海关编码	HS8888888

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getEnName() {
        return EnName;
    }

    public void setEnName(String enName) {
        EnName = enName;
    }

    public String getCnName() {
        return CnName;
    }

    public void setCnName(String cnName) {
        CnName = cnName;
    }

    public Integer getMaterialQuantity() {
        return MaterialQuantity;
    }

    public void setMaterialQuantity(Integer materialQuantity) {
        MaterialQuantity = materialQuantity;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public BigDecimal getWeight() {
        return Weight;
    }

    public void setWeight(BigDecimal weight) {
        Weight = weight;
    }

    public BigDecimal getLength() {
        return Length;
    }

    public void setLength(BigDecimal length) {
        Length = length;
    }

    public BigDecimal getWidth() {
        return Width;
    }

    public void setWidth(BigDecimal width) {
        Width = width;
    }

    public BigDecimal getHigh() {
        return High;
    }

    public void setHigh(BigDecimal high) {
        High = high;
    }

    public String getProducingArea() {
        return ProducingArea;
    }

    public void setProducingArea(String producingArea) {
        ProducingArea = producingArea;
    }

    public String getHSCode() {
        return HSCode;
    }

    public void setHSCode(String HSCode) {
        this.HSCode = HSCode;
    }
}
