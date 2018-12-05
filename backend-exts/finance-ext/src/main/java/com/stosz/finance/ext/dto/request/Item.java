package com.stosz.finance.ext.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/8 16:24
 */
public class Item implements Serializable{
    
    private Integer changeBillItem;//发生单据详情id
    private Integer deptId;//部门id
    private String deptName;//部门名称
    private String sku;//sku
    private Integer quantity;//数量
    private BigDecimal amount;//金额

    public Integer getChangeBillItem() {
        return changeBillItem;
    }

    public void setChangeBillItem(Integer changeBillItem) {
        this.changeBillItem = changeBillItem;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
