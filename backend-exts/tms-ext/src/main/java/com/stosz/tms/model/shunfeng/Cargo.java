package com.stosz.tms.model.shunfeng;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "Cargo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cargo {

    @XmlAttribute(name = "name")
    private String name;//String(128) 是货物名称，如果需要生成电 子运单，则为必填。

    @XmlAttribute(name = "count")
    private Integer count;//Number(5) 条件货物数量 跨境件报关需要填写

    @XmlAttribute(name = "unit")
    private String unit;

    @XmlAttribute(name = "weight")
    private Double weight;//Number(16,3) 条件订单货物单位重量，包含子 母件，单位千克，精确到小 数点后 3 位跨境件报关需要 填写

    @XmlAttribute(name = "amount")
    private BigDecimal amount;//Number(17,3) 条件货物单价，精确到小数点后3 位，跨境件报关需要填
    /*货物单价的币别：
     CNY: 人民币
     HKD: 港币
     USD: 美元
     NTD: 新台币
     RUB: 卢布
     EUR: 欧元
     MOP: 澳门元
     SGD: 新元
     JPY: 日元
     KRW: 韩元
     MYR: 马币
     VND: 越南盾
     THB: 泰铢
     AUD: 澳大利亚元
     MNT: 图格里克 跨境件报关需要填写
    */
    @XmlAttribute(name = "currency")
    private String currency;//币种

    @XmlAttribute(name = "source_area")
    private String source_area;//String(5) 条件原产地国别，跨境件报关需 要填写

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSource_area() {
        return source_area;
    }

    public void setSource_area(String source_area) {
        this.source_area = source_area;
    }
}
