package com.stosz.purchase.ext.model.finance;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 应付款单明细
 */
public class PayableItem extends AbstractParamEntity implements Serializable,ITable{
    @DBColumn
    private Integer id;//发生单据详情id
    @DBColumn
    private Integer deptId;//部门id
    @DBColumn
    private String deptNo;//部门编号
    @DBColumn
    private String spu;//spu
    @DBColumn
    private String productTitle;//产品标题
    @DBColumn
    private String deptName;//部门名称
    @DBColumn
    private String sku;//sku
    @DBColumn
    private String skuTitle;//sku标题
    @DBColumn
    private Integer quantity;//数量
    @DBColumn
    private BigDecimal amount;//金额

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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    @Override
    public String getTable() {
        return "payable_item";
    }
}
