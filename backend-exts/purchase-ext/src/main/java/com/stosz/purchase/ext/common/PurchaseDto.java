package com.stosz.purchase.ext.common;

import com.stosz.plat.model.DBColumn;
import com.stosz.product.ext.model.Product;
import com.stosz.purchase.ext.enums.PayMethodEnum;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
public class PurchaseDto implements Serializable{
    /**
     * id
     */
    private Integer id;
    /**
     * 采购单号
     */
    private String purchaseNo;
    /**
     * 供应商id
     */
    private Integer supplierId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 采购员id
     */
    private Integer buyerId;
    /**
     * 采购员
     */
    private String buyer;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 事业部id
     */
    private Integer buDeptId;
    /**
     * 事业部编号
     */
    private String buDeptNo;
    /**
     * 事业部名称
     */
    private String buDeptName;
    /**
     * 平台id
     */
    private Integer platId;
    /**
     * 平台名称
     */
    private String platName;
    /**
     * 渠道订单号
     */
    private String platOrdersNo;
    /**
     * 实付金额
     */
    private BigDecimal factAmount;
    /**
     * 运单号
     */
    private String trackingNo;
    /**
     * wmsID
     */
    private Integer wmsId;
    /**
     * wms名称
     */
    private String wmsName;
    /**
     * wms编号
     */
    private String wmsSysCode;
    /**
     * 实际到货时间
     */
    private LocalDateTime factArrivalAt;
    /**
     * 采购单状态
     */
    private String state;
    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Integer creatorId;

    private String creator;

    private LocalDateTime stateTime;

    private Integer auditorId;

    private String auditor;

    private LocalDateTime auditTime;

    private Integer payerId;

    private String payer;

    private PayMethodEnum payMethodEnum;

    private LocalDateTime paymentTime;

    private String memo;

    private LocalDateTime purchaseTime;

    private List<PurchaseItem> items;

    private List<PurchaseSkuDto> purchaseSkuDtoList;

    private Product product;

    private Integer purchaseQty;//生成采购单时，采购需求数
    private Integer avgSaleQty;//生成采购单时，3日平均销量
    private Integer pendingOrderQty;//生成采购单时，待审订单数

    private String buyerAccount;//买手账号

    private Integer sourceType;//单据来源，1:错货单

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getId() {
        return id;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
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
        this.buyer = buyer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
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
        this.buDeptNo = buDeptNo;
    }

    public String getBuDeptName() {
        return buDeptName;
    }

    public void setBuDeptName(String buDeptName) {
        this.buDeptName = buDeptName;
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
        this.platName = platName;
    }

    public String getPlatOrdersNo() {
        return platOrdersNo;
    }

    public void setPlatOrdersNo(String platOrdersNo) {
        this.platOrdersNo = platOrdersNo;
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
        this.state = state;
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

    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public List<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItem> items) {
        this.items = items;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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


    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPayTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<PurchaseSkuDto> getPurchaseSkuDtoList() {
        return purchaseSkuDtoList;
    }

    public Integer getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public Integer getAvgSaleQty() {
        return avgSaleQty;
    }

    public void setAvgSaleQty(Integer avgSaleQty) {
        this.avgSaleQty = avgSaleQty;
    }

    public Integer getPendingOrderQty() {
        return pendingOrderQty;
    }

    public void setPendingOrderQty(Integer pendingOrderQty) {
        this.pendingOrderQty = pendingOrderQty;
    }

    public void setPurchaseSkuDtoList(List<PurchaseSkuDto> purchaseSkuDtoList) {
        this.purchaseSkuDtoList = purchaseSkuDtoList;
    }

    public String getWmsSysCode() {
        return wmsSysCode;
    }

    public void setWmsSysCode(String wmsSysCode) {
        this.wmsSysCode = wmsSysCode;
    }

    public void setPurchase(Purchase purchase){
         this.id = purchase.getId();
         this.purchaseNo = purchase.getPurchaseNo();
         this.supplierId = purchase.getSupplierId();
         this.buyerId = purchase.getBuyerId();
         this.buyer = purchase.getBuyer();
         this.amount = purchase.getAmount();
         this.quantity = purchase.getQuantity();
         this.buDeptId = purchase.getBuDeptId();
         this.buDeptNo = purchase.getBuDeptNo();
         this.buDeptName = purchase.getBuDeptName();
         this.platId = purchase.getPlatId();
         this.platName = purchase.getPlatName();
         this.platOrdersNo = purchase.getPlatOrdersNo();
         this.factAmount = purchase.getFactAmount();
         this.trackingNo = purchase.getTrackingNo();
         this.wmsId = purchase.getWmsId();
         this.factArrivalAt = purchase.getFactArrivalAt();
         this.state = purchase.getState();
         this.createAt = purchase.getCreateAt();
         this.updateAt= purchase.getUpdateAt();
         this.creatorId = purchase.getCreatorId();
         this.creator = purchase.getCreator();
         this.stateTime = purchase.getStateTime();
         this.supplierName = purchase.getSupplierName();
         this.wmsName = purchase.getWmsName();
         this.wmsSysCode = purchase.getWmsSysCode();
         this.auditor = purchase.getAuditor();
         this.auditorId = purchase.getAuditorId();
         this.auditTime = purchase.getAuditTime();
         this.paymentTime = purchase.getPaymentTime();
         this.payer  = purchase.getPayer();
         this.payerId = purchase.getPayerId();
         this.payMethodEnum = purchase.getPayMethodEnum();
         this.memo = purchase.getMemo();
         this.purchaseQty = purchase.getPurchaseQty();
         this.avgSaleQty = purchase.getAvgSaleQty();
         this.pendingOrderQty = purchase.getPendingOrderQty();
         this.buyerAccount = purchase.getBuyerAccount();
         this.sourceType = purchase.getSourceType();
    }

    public Purchase getPurchase(){
        Purchase purchase = new Purchase();
        purchase.setId(this.id );
        purchase.setPurchaseNo(this.purchaseNo);
        purchase.setSupplierId(this.supplierId);
        purchase.setBuyerId(this.buyerId);
        purchase.setBuyer(this.buyer);
        purchase.setAmount(this.amount);
        purchase.setQuantity(this.quantity);
        purchase.setBuDeptId(this.buDeptId);
        purchase.setBuDeptNo(this.buDeptNo);
        purchase.setBuDeptName(this.buDeptName);
        purchase.setPlatId(this.platId);
        purchase.setPlatName(this.platName);
        purchase.setPlatOrdersNo(this.platOrdersNo);
        purchase.setFactAmount(this.factAmount);
        purchase.setTrackingNo(this.trackingNo);
        purchase.setWmsId(this.wmsId);
        purchase.setFactArrivalAt(this.factArrivalAt);
        purchase.setState(this.state);
        purchase.setCreateAt(this.createAt);
        purchase.setUpdateAt(this.updateAt);
        purchase.setCreatorId(this.creatorId);
        purchase.setCreator(this.creator);
        purchase.setStateTime(this.stateTime);
        purchase.setSupplierName(this.supplierName);
        purchase.setWmsName(this.wmsName);
        purchase.setAuditorId(this.auditorId);
        purchase.setAuditor(this.auditor);
        purchase.setAuditTime(this.auditTime);
        purchase.setPayer(this.payer);
        purchase.setPayerId(this.payerId);
        purchase.setPayMethodEnum(this.payMethodEnum);
        purchase.setPaymentTime(this.paymentTime);
        purchase.setMemo(this.memo);
        purchase.setWmsSysCode(this.wmsSysCode);
        purchase.setPurchaseQty(this.purchaseQty);
        purchase.setPendingOrderQty(this.pendingOrderQty);
        purchase.setAvgSaleQty(this.avgSaleQty);
        purchase.setBuyerAccount(this.buyerAccount);
        purchase.setSourceType(this.sourceType);
        return purchase;
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

    public String getPayMethod(){
        return this.payMethodEnum.display();
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "id=" + id +
                ", purchaseNo='" + purchaseNo + '\'' +
                ", supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", buyerId=" + buyerId +
                ", buyer='" + buyer + '\'' +
                ", amount=" + amount +
                ", quantity=" + quantity +
                ", buDeptId=" + buDeptId +
                ", buDeptNo='" + buDeptNo + '\'' +
                ", buDeptName='" + buDeptName + '\'' +
                ", platId=" + platId +
                ", platName='" + platName + '\'' +
                ", platOrdersNo='" + platOrdersNo + '\'' +
                ", factAmount=" + factAmount +
                ", trackingNo='" + trackingNo + '\'' +
                ", wmsId=" + wmsId +
                ", wmsName='" + wmsName + '\'' +
                ", wmsSysCode='" + wmsSysCode + '\'' +
                ", factArrivalAt=" + factArrivalAt +
                ", state='" + state + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", creatorId=" + creatorId +
                ", creator='" + creator + '\'' +
                ", stateTime=" + stateTime +
                ", auditorId=" + auditorId +
                ", auditor='" + auditor + '\'' +
                ", auditTime=" + auditTime +
                ", payerId=" + payerId +
                ", payer='" + payer + '\'' +
                ", payMethodEnum=" + payMethodEnum +
                ", paymentTime=" + paymentTime +
                ", memo='" + memo + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", items=" + items +
                ", purchaseSkuDtoList=" + purchaseSkuDtoList +
                ", product=" + product +
                ", purchaseQty=" + purchaseQty +
                ", avgSaleQty=" + avgSaleQty +
                ", pendingOrderQty=" + pendingOrderQty +
                ", buyerAccount='" + buyerAccount + '\'' +
                ", sourceType=" + sourceType +
                '}';
    }
}
