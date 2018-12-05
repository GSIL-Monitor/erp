package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author 
 */
public class OrdersRmaBillItem extends AbstractParamEntity implements ITable, Serializable {

    @DBColumn
    private Integer id;

    /**
     * 退货拒收单的id
     */
    @DBColumn
    private Integer ordersRmaBillId;

    /**
     * 商品的spu
     */
    @DBColumn
    private String spu;

    /**
     * 商品的sku信息
     */
    @DBColumn
    private String sku;

    /**
     * 属性值数组
     */
    @DBColumn
    private String attrTitleArray="";

    /**
     * 产品id
     */
    @DBColumn
    private Integer productId;

    /**
     * 产品名称
     */
    @DBColumn
    private String productTitle;

    /**
     * 产品图片url
     */
    @DBColumn
    private String productImgUrl;

    /**
     * 订单项的条目ID
     */
    @DBColumn
    private Integer ordersItemId;

    /**
     * 订单项数量
     */
    @DBColumn
    private Integer ordersItemQty;

    /**
     * 可退数量
     */
    @DBColumn
    private Integer ordersItemReturnQty;

    /**
     * 申请数量
     */
    @DBColumn
    private Integer ordersItemApplyQty;

    /**
     * 单价
     */
    @DBColumn
    private BigDecimal singleAmount;

    /**
     * 总价
     */
    @DBColumn
    private BigDecimal totalAmount;

    /**
     * 检测结果 默认全部是良品 
     */
    @DBColumn
    private String detectionResult="良品";

    /**
     * 入库货位
     */
    @DBColumn
    private String storageLocation="";

    /**
     * 入库单号
     */
    @DBColumn
    private String inStorageNo="";

    /**
     * 仓库入库(货)数量
     */
    @DBColumn
    private Integer inQty;

    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @DBColumn
    private LocalDateTime updateAt;

    /**
     * 创建者id
     */
    @DBColumn
    private Integer creatorId;

    /**
     * 创建人
     */
    @DBColumn
    private String creator;
    /**
     * 内部名称
     */
    @DBColumn
    private String innerTitle="";


    private static final long serialVersionUID = 1L;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersRmaBillId() {
        return ordersRmaBillId;
    }

    public void setOrdersRmaBillId(Integer ordersRmaBillId) {
        this.ordersRmaBillId = ordersRmaBillId;
    }

    public String getInnerTitle() {
        return innerTitle;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getAttrTitleArray() {
        return attrTitleArray;
    }

    public void setAttrTitleArray(String attrTitleArray) {
        this.attrTitleArray = attrTitleArray;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public Integer getOrdersItemId() {
        return ordersItemId;
    }

    public void setOrdersItemId(Integer ordersItemId) {
        this.ordersItemId = ordersItemId;
    }

    public Integer getOrdersItemQty() {
        return ordersItemQty;
    }

    public void setOrdersItemQty(Integer ordersItemQty) {
        this.ordersItemQty = ordersItemQty;
    }

    public Integer getOrdersItemReturnQty() {
        return ordersItemReturnQty;
    }

    public void setOrdersItemReturnQty(Integer ordersItemReturnQty) {
        this.ordersItemReturnQty = ordersItemReturnQty;
    }

    public Integer getOrdersItemApplyQty() {
        return ordersItemApplyQty;
    }

    public void setOrdersItemApplyQty(Integer ordersItemApplyQty) {
        this.ordersItemApplyQty = ordersItemApplyQty;
    }

    public BigDecimal getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(BigDecimal singleAmount) {
        this.singleAmount = singleAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDetectionResult() {
        return detectionResult;
    }

    public void setDetectionResult(String detectionResult) {
        this.detectionResult = detectionResult;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getInStorageNo() {
        return inStorageNo;
    }

    public void setInStorageNo(String inStorageNo) {
        this.inStorageNo = inStorageNo;
    }

    public Integer getInQty() {
        return inQty;
    }

    public void setInQty(Integer inQty) {
        this.inQty = inQty;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ordersRmaBillId=").append(ordersRmaBillId);
        sb.append(", spu=").append(spu);
        sb.append(", sku=").append(sku);
        sb.append(", attrTitleArray=").append(attrTitleArray);
        sb.append(", productId=").append(productId);
        sb.append(", productTitle=").append(productTitle);
        sb.append(", productImgUrl=").append(productImgUrl);
        sb.append(", ordersItemId=").append(ordersItemId);
        sb.append(", ordersItemQty=").append(ordersItemQty);
        sb.append(", ordersItemReturnQty=").append(ordersItemReturnQty);
        sb.append(", ordersItemApplyQty=").append(ordersItemApplyQty);
        sb.append(", singleAmount=").append(singleAmount);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", detectionResult=").append(detectionResult);
        sb.append(", storageLocation=").append(storageLocation);
        sb.append(", inStorageNo=").append(inStorageNo);
        sb.append(", inQty=").append(inQty);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", creator=").append(creator);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String getTable() {
        return "orders_rma_bill_item";
    }
}