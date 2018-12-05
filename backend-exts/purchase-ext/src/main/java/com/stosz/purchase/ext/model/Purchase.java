package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.product.ext.model.Product;
import com.stosz.purchase.ext.enums.PayMethodEnum;
import com.stosz.purchase.ext.enums.PurchaseState;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Purchase extends AbstractParamEntity implements ITable,Serializable, IFsmInstance {
    @DBColumn
    private Integer id;//主键id
    @DBColumn
    private String purchaseNo;//订单号
    @DBColumn
    private Integer supplierId;//供应商id
    private String supplierName;//供应商名称
    @DBColumn
    private Integer buyerId;//采购员id
    @DBColumn
    private String buyer;//采购员名称
    @DBColumn
    private BigDecimal amount;//总金额
    @DBColumn
    private Integer quantity = 0;//总的采购数量
    @DBColumn
    private Integer buDeptId;//事业部id
    @DBColumn
    private String buDeptNo;//事业部编号
    @DBColumn
    private String buDeptName;//事业部名称
    @DBColumn
    private Integer platId;//渠道id
    private String platName;//渠道名称
    @DBColumn
    private String platOrdersNo;//渠道订单号
    @DBColumn
    private BigDecimal factAmount;//实付总金额
    private String trackingNo;//运单号，多个时用逗号隔开
    @DBColumn
    private Integer wmsId;//仓库id，默认福永仓1
    @DBColumn
    private String wmsName;//仓库名称，默认福永仓
    @DBColumn
    private String wmsSysCode;//仓库编号，默认‘Y’
    @DBColumn
    private LocalDateTime factArrivalAt;//实际到货时间
    @DBColumn
    private String state;//状态
    @DBColumn
    private LocalDateTime createAt;//创建时间

    private LocalDateTime updateAt;//修改时间
    @DBColumn
    private Integer creatorId;//创建人id
    @DBColumn
    private String creator;//创建人
    @DBColumn
    private LocalDateTime stateTime;//状态时间
    @DBColumn
    private String memo;//备注
    @DBColumn
    private Integer purchaseQty;//生成采购单时，采购需求数
    @DBColumn
    private Integer avgSaleQty;//生成采购单时，3日平均销量
    @DBColumn
    private Integer pendingOrderQty;//生成采购单时，待审订单数
    @DBColumn
    private String buyerAccount;//买手账号
    private Integer auditorId;//财务审核人id
    private String auditor;//财务审核人
    private LocalDateTime auditTime;//审核时间
    private Integer payerId;//支付人id
    private String payer;//支付人
    private LocalDateTime paymentTime;//支付时间
    private LocalDateTime submit;//提交时间

    private PayMethodEnum payMethodEnum;//支付方式

    private Product  product;//冗余，做查询的时候用到
    private String sku;//冗余，做查询的时候用到
    private LocalDateTime maxPurchaseTime; //冗余，做查询的时候用到
    private LocalDateTime minPurchaseTime;//冗余，做查询的时候用到
    private Boolean hasTracking = false;//冗余，做查询的时候用到,是否勾选未填运单号
    private String productTitle;//冗余，做查询的时候用到

    private Integer sourceType;//来源类型，1:错货单

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getWmsSysCode() {
        return wmsSysCode;
    }

    public void setWmsSysCode(String wmsSysCode) {
        this.wmsSysCode = wmsSysCode;
    }

    public Boolean getHasTracking() {
        return hasTracking;
    }

    public void setHasTracking(Boolean hasTracking) {
        this.hasTracking = hasTracking;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
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

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getPayMethod(){return payMethodEnum.display();}

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo == null ? null : purchaseNo.trim();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer == null ? null : buyer.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity == null ? 0 :quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
        this.buDeptNo = buDeptNo == null ? null : buDeptNo.trim();
    }

    public String getBuDeptName() {
        return buDeptName;
    }

    public void setBuDeptName(String buDeptName) {
        this.buDeptName = buDeptName == null ? null : buDeptName.trim();
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName == null ? null : platName.trim();
    }

    public String getPlatOrdersNo() {
        return platOrdersNo;
    }

    public void setPlatOrdersNo(String platOrdersNo) {
        this.platOrdersNo = platOrdersNo ;
    }

    public BigDecimal getFactAmount() {
        return factAmount;
    }

    public void setFactAmount(BigDecimal factAmount) {
        this.factAmount = factAmount;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public PurchaseState getStateEnum(){
        return this.state == null ? null : PurchaseState.fromName(state);
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public LocalDateTime getFactArrivalAt() {
        return factArrivalAt;
    }

    public void setFactArrivalAt(LocalDateTime factArrivalAt) {
        this.factArrivalAt = factArrivalAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
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
        this.creator = creator == null ? null : creator.trim();
    }

    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getPayerId() {
        return payerId;
    }

    public void setPayerId(Integer payerId) {
        this.payerId = payerId;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public PayMethodEnum getPayMethodEnum() {
        return payMethodEnum;
    }

    public void setPayMethodEnum(PayMethodEnum payMethodEnum) {
        this.payMethodEnum = payMethodEnum;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getPurchaseQty() {
        return purchaseQty == null ? 0 : purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public Integer getAvgSaleQty() {
        return avgSaleQty == null ? 0 : avgSaleQty;
    }

    public void setAvgSaleQty(Integer avgSaleQty) {
        this.avgSaleQty = avgSaleQty;
    }

    public Integer getPendingOrderQty() {
        return pendingOrderQty == null ? 0 : pendingOrderQty;
    }

    public void setPendingOrderQty(Integer pendingOrderQty) {
        this.pendingOrderQty = pendingOrderQty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public LocalDateTime getSubmit() {
        return submit;
    }

    public void setSubmit(LocalDateTime submit) {
        this.submit = submit;
    }

    public LocalDateTime getMaxPurchaseTime() {
        return maxPurchaseTime;
    }

    public void setMaxPurchaseTime(LocalDateTime maxPurchaseTime) {
        this.maxPurchaseTime = maxPurchaseTime;
    }

    public LocalDateTime getMinPurchaseTime() {
        return minPurchaseTime;
    }

    public void setMinPurchaseTime(LocalDateTime minPurchaseTime) {
        this.minPurchaseTime = minPurchaseTime;
    }

    @Override
    public String getTable() {
        return "purchase";
    }
}