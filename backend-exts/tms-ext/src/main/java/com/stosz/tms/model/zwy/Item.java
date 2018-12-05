package com.stosz.tms.model.zwy;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Serializable {

    //String(100) Mandatory 品名 英文
    @XmlElement(name = "productNameEN",nillable=true)
    private String productNameEN;

    //String(100) Mandatory 品名 中文
    @XmlElement(name = "productNameCN",nillable=true)
    private String productNameCN;

    //String(50) Optional 规格型号
//    @XmlElement(name = "specification",nillable=true)
//    private String specification;

    //String(5) Optional 单位
    @XmlElement(name = "unit",nillable=true)
    private String unit;

    //int Mandatory 此品名数量
    @XmlElement(name = "quantity",nillable=true)
    private Integer quantity;

    //String(10) Optional HS 编码
//    @XmlElement(name = "HScode",nillable=true)
//    private String HScode;

    //Long Mandatory 此品名海关申报总价，单位美元，保留两位小数
    @XmlElement(name = "declareValue",nillable=true)
    private Long declareValue;

    //Long Mandatory 此品名总重量，单位 KG，保留三位小数
    @XmlElement(name = "productWeight",nillable=true)
    private Long productWeight;

    //String(500) Optional 商品 URL
//    @XmlElement(name = "productURL",nillable=true)
//    private Long productURL;

    //String(200) Optional 备注
//    @XmlElement(name = "comments",nillable=true)
//    private String comments;

    public String getProductNameEN() {
        return productNameEN;
    }

    public void setProductNameEN(String productNameEN) {
        this.productNameEN = productNameEN;
    }

    public String getProductNameCN() {
        return productNameCN;
    }

    public void setProductNameCN(String productNameCN) {
        this.productNameCN = productNameCN;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getDeclareValue() {
        return declareValue;
    }

    public void setDeclareValue(Long declareValue) {
        this.declareValue = declareValue;
    }

    public Long getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Long productWeight) {
        this.productWeight = productWeight;
    }
}
