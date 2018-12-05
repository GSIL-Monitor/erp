package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.model.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 错货申请单的实体
 * @author xiongchenyang
 * @version [1.0 , 2018/1/4]
 */
public class ErrorGoods  extends AbstractParamEntity implements Serializable,ITable,IFsmInstance {

    @DBColumn
    private Integer id ;//主键id
    @DBColumn
    private String no;//错货申请单编号
    @DBColumn
    private String originalPurchaseNo;//原始采购单号
    @DBColumn
    private String newPurchaseNo;//新采购单号
    @DBColumn
    private String purchaseReturnedNo;//采购退货单号
    @DBColumn
    private Integer buDeptId; //事业部id
    @DBColumn
    private String buDeptNo; //事业部编号
    @DBColumn
    private String buDeptName; //事业部名称
    @DBColumn
    private String state;// 状态
    @DBColumn
    private LocalDateTime stateTime;//状态时间
    @DBColumn
    private Integer creatorId;//创建人id
    @DBColumn
    private String creator;//创建人
    @DBColumn
    private LocalDateTime createAt;//创建时间
    private LocalDateTime updateAt;//修改时间

    private String spu;//目前只有一个，以后可能会有多个
    private String productTitle;//产品标题

    private List<ErrorGoodsItem> errorGoodsItemList;//错货单明细

    private Integer originalPurchaseId;//原采购单id

    @DBColumn
    private Integer quantity;//错货总数
    @DBColumn
    private BigDecimal amount;//错货总价
    @DBColumn
    private Integer supplierId;//供应商id

    private String sku;
    private String supplierName;

    @DBColumn
    private String memo;//备注
    @DBColumn
    private Integer wmsId;
    @DBColumn
    private String wmsSysCode;
    @DBColumn
    private String wmsName;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOriginalPurchaseNo() {
        return originalPurchaseNo;
    }

    public void setOriginalPurchaseNo(String originalPurchaseNo) {
        this.originalPurchaseNo = originalPurchaseNo;
    }

    public String getNewPurchaseNo() {
        return newPurchaseNo;
    }

    public void setNewPurchaseNo(String newPurchaseNo) {
        this.newPurchaseNo = newPurchaseNo;
    }

    public String getPurchaseReturnedNo() {
        return purchaseReturnedNo;
    }

    public void setPurchaseReturnedNo(String purchaseReturnedNo) {
        this.purchaseReturnedNo = purchaseReturnedNo;
    }

    public Integer getBuDeptId() {
        return buDeptId;
    }

    public void setBuDeptId(Integer buDeptId) {
        this.buDeptId = buDeptId;
    }

    public String getBuDeptNo() {
        return buDeptNo;
    }

    public void setBuDeptNo(String buDeptNo) {
        this.buDeptNo = buDeptNo;
    }

    public String getBuDeptName() {
        return buDeptName;
    }

    public void setBuDeptName(String buDeptName) {
        this.buDeptName = buDeptName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
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

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getWmsSysCode() {
        return wmsSysCode;
    }

    public void setWmsSysCode(String wmsSysCode) {
        this.wmsSysCode = wmsSysCode;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public List<ErrorGoodsItem> getErrorGoodsItemList() {
        return errorGoodsItemList;
    }

    public void setErrorGoodsItemList(List<ErrorGoodsItem> errorGoodsItemList) {
        this.errorGoodsItemList = errorGoodsItemList;
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

    public Integer getOriginalPurchaseId() {
        return originalPurchaseId;
    }

    public void setOriginalPurchaseId(Integer originalPurchaseId) {
        this.originalPurchaseId = originalPurchaseId;
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

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getTable() {
        return "error_goods";
    }
}
