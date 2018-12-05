package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.purchase.ext.enums.PurchaseItemState;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseItem extends AbstractParamEntity implements ITable,Serializable, IFsmInstance {

    @DBColumn
    private Integer id;//主键id
    @DBColumn
    private Integer purchaseId;//采购单号
    @DBColumn
    private Integer deptId;//部门ID
    @DBColumn
    private String deptNo;//部门编号
    @DBColumn
    private String deptName;//部门名称
    @DBColumn
    private String sku;//sku
    @DBColumn
    private String spu;//spu
    @DBColumn
    private BigDecimal price;//采购单价
    @DBColumn
    private Integer quantity;//采购数量
    @DBColumn
    private Integer intransitCancleQty;//在途采退数
    @DBColumn
    private Integer instockQty;//质检合格数，采购入库数
    @DBColumn
    private Integer qcfailQty;//质检不合格数
    @DBColumn
    private Integer purchaseQty;//采购需求数
    @DBColumn
    private Integer avgSaleQty;//三日平均销量
    @DBColumn
    private Integer pendingOrderQty;//待审单数
    @DBColumn
    private String state;//采购明细状态
    @DBColumn
    private LocalDateTime createAt;//创建时间

    private LocalDateTime updateAt;//修改时间
    @DBColumn
    private BigDecimal amount;//采购金额
    @DBColumn
    private LocalDateTime stateTime;//采购状态时间
    @DBColumn
    private Integer auditorId;//审核人id
    @DBColumn
    private String auditor;//审核人
    @DBColumn
    private Integer auditTime;//审核时间
    @DBColumn
    private String memo;//备注
    @DBColumn
    private String productTitle;//产品标题
    @DBColumn
    private String skuTitle;//sku标题
    @DBColumn
    private String purchaseNo;//采购单号
    
    public String startTime;//开始时间
    
    private String endTime;//结束时间

    private  Integer waitInStockQty;//待入库数

    private BigDecimal needPayment;//应付金额 仓库到货后应付货款

    private BigDecimal nonPayment;//未付金额 应付款-已付款

    private BigDecimal alreadyPayment;//已付金额 财务实际付款

    private Integer usableQty;//可用库存数
    

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
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

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity == null ? 0 : quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIntransitCancleQty() {
        return intransitCancleQty == null ? 0 : intransitCancleQty;
    }

    public void setIntransitCancleQty(Integer intransitCancleQty) {
        this.intransitCancleQty = intransitCancleQty;
    }

    public Integer getInstockQty() {
        return instockQty == null ? 0 : instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public Integer getQcfailQty() {
        return qcfailQty == null ? 0 : qcfailQty;
    }

    public void setQcfailQty(Integer qcfailQty) {
        this.qcfailQty = qcfailQty;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public PurchaseItemState getStateEnum(){
        return this.state == null ? null : PurchaseItemState.fromName(this.state);
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
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

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
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

    public Integer getUsableQty() {
        return usableQty == null ? 0 : usableQty;
    }

    public void setUsableQty(Integer usableQty) {
        this.usableQty = usableQty;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public BigDecimal getNeedPayment() {
        return needPayment;
    }

    public void setNeedPayment(BigDecimal needPayment) {
        this.needPayment = needPayment;
    }

    public BigDecimal getNonPayment() {
        return nonPayment;
    }

    public void setNonPayment(BigDecimal nonPayment) {
        this.nonPayment = nonPayment;
    }

    public BigDecimal getAlreadyPayment() {
        return alreadyPayment;
    }

    public void setAlreadyPayment(BigDecimal alreadyPayment) {
        this.alreadyPayment = alreadyPayment;
    }

    public Integer getWaitInStockQty() {
        return waitInStockQty == null ? 0 : waitInStockQty;
    }

    public void setWaitInStockQty(Integer waitInStockQty) {
        this.waitInStockQty = waitInStockQty;
    }

    @Override
    public Integer getParentId() {
        return null;
    }


    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getTable() {
        return "purchase_item";
    }
}