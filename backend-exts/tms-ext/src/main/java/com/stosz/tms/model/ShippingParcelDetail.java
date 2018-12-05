package com.stosz.tms.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ShippingParcelDetail extends AbstractParamEntity implements ITable {
    @DBColumn
    private Integer id;

    @DBColumn
    private Integer parcelId;

    @DBColumn
    private Long orderItemId;

    @DBColumn
    private String sku;

    @DBColumn
    private String productTitle;

    @DBColumn
    private String productTitleEn;

    @DBColumn
    private Integer orderDetailQty;

    @DBColumn
    private BigDecimal price;

    @DBColumn
    private BigDecimal totalAmount;

    @DBColumn
    private BigDecimal discountAmount;

    @DBColumn
    private BigDecimal realAmount;

    @DBColumn
    private String inlandhscode;

    @DBColumn
    private String foreignhscode;

    @DBColumn
    private BigDecimal weight;

    @DBColumn
    private String unit;

    @DBColumn
    private Date createAt;

    @DBColumn
    private Date updateAt;

    @DBColumn
    private String creator;

    @DBColumn
    private Integer creatorId;

    public String getProductTitleEn() {
        return productTitleEn;
    }

    public void setProductTitleEn(String productTitleEn) {
        this.productTitleEn = productTitleEn;
    }

    private List<Integer> parcelIdList;

    public List<Integer> getParcelIdList() {
        return parcelIdList;
    }

    public void setParcelIdList(List<Integer> parcelIdList) {
        this.parcelIdList = parcelIdList;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle == null ? null : productTitle.trim();
    }

    public Integer getOrderDetailQty() {
        return orderDetailQty;
    }

    public void setOrderDetailQty(Integer orderDetailQty) {
        this.orderDetailQty = orderDetailQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public String getInlandhscode() {
        return inlandhscode;
    }

    public void setInlandhscode(String inlandhscode) {
        this.inlandhscode = inlandhscode == null ? null : inlandhscode.trim();
    }

    public String getForeignhscode() {
        return foreignhscode;
    }

    public void setForeignhscode(String foreignhscode) {
        this.foreignhscode = foreignhscode == null ? null : foreignhscode.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getParcelId() {
        return parcelId;
    }

    public void setParcelId(Integer parcelId) {
        this.parcelId = parcelId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTable() {
        return "shipping_parcel_detail";
    }
}