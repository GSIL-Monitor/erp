package com.stosz.purchase.ext.model;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.model.Partner;
import com.stosz.purchase.ext.enums.ShippingTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购运单号实体
 * @author xiongchenyang
 * @version [1.0 , 2018/1/2]
 */
public class PurchaseTrackingNo extends AbstractParamEntity implements Serializable,ITable{

    @DBColumn
    private Integer id;//主键
    @DBColumn
    private String trackingNo;//运单号
    @DBColumn
    private Integer shippingId;//物流承运商id
    @DBColumn
    private String shippingCode;//物流承运商编码
    @DBColumn
    private String shippingName;//物流承运商名称
    @DBColumn
    private ShippingTypeEnum shippingTypeEnum;//物流运输类型。
    @DBColumn
    private BigDecimal shippingAmount;//运费
    @DBColumn
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @DBColumn
    private Integer creatorId;
    @DBColumn
    private String creator;

    private String purchaseNo;

    private List<Partner> partnerList;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public List<Partner> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<Partner> partnerList) {
        this.partnerList = partnerList;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public ShippingTypeEnum getShippingTypeEnum() {
        return shippingTypeEnum;
    }

    public void setShippingTypeEnum(ShippingTypeEnum shippingTypeEnum) {
        this.shippingTypeEnum = shippingTypeEnum;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    @Override
    public String getTable() {
        return "purchase_tracking_no";
    }
}
