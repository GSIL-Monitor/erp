package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.purchase.ext.enums.ErrorGoodsItemState;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 错货申请单明细的实体
 * @author xiongchenyang
 * @version [1.0 , 2018/1/4]
 */
public class ErrorGoodsItem extends AbstractParamEntity implements Serializable,ITable,IFsmInstance {

    @DBColumn
    private Integer id ;//主键id
    @DBColumn
    private Integer errorId;//错货申请单id
    @DBColumn
    private String errorNo;//错货申请单号
    @DBColumn
    private String spu;
    @DBColumn
    private String originalSku;//原始sku
    @DBColumn
    private String realSku;//真实到货sku
    @DBColumn
    private Integer deptId;//小组id
    @DBColumn
    private String deptNo;//小组编号
    @DBColumn
    private String deptName;//小组名称
    @DBColumn
    private Integer quantity;//错货数
    @DBColumn
    private BigDecimal amount;//错货总价
    @DBColumn
    private String state;//错货状态
    @DBColumn
    private LocalDateTime stateTime;//状态修改时间；
    @DBColumn
    private String creator;//创建人
    @DBColumn
    private Integer creatorId;//创建人id
    @DBColumn
    private String memo;//备注
    @DBColumn
    private String auditor;//审核人；
    @DBColumn
    private Integer auditorId;//审核人id
    @DBColumn
    private LocalDateTime auditorTime;//审核时间
    @DBColumn
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @DBColumn
    private BigDecimal price;// 采购单价
    @DBColumn
    private String productTitle;
    @DBColumn
    private String originalSkuTitle;
    @DBColumn
    private String realSkuTitle;
    @DBColumn
    private Integer originalPurchaseItemId;

    public Integer getOriginalPurchaseItemId() {
        return originalPurchaseItemId;
    }

    public void setOriginalPurchaseItemId(Integer originalPurchaseItemId) {
        this.originalPurchaseItemId = originalPurchaseItemId;
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

    public Integer getErrorId() {
        return errorId;
    }

    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getOriginalSku() {
        return originalSku;
    }

    public void setOriginalSku(String originalSku) {
        this.originalSku = originalSku;
    }

    public String getRealSku() {
        return realSku;
    }

    public void setRealSku(String realSku) {
        this.realSku = realSku;
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
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    public void setStateEnum(ErrorGoodsItemState errorGoodsItemState){
        this.state = errorGoodsItemState.name();
    }

    public ErrorGoodsItemState getStateEnum(){
        return this.state == null ? null : ErrorGoodsItemState.fromName(this.state);
    }

    @Override
    public LocalDateTime getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public LocalDateTime getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(LocalDateTime auditorTime) {
        this.auditorTime = auditorTime;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOriginalSkuTitle() {
        return originalSkuTitle;
    }

    public void setOriginalSkuTitle(String originalSkuTitle) {
        this.originalSkuTitle = originalSkuTitle;
    }

    public String getRealSkuTitle() {
        return realSkuTitle;
    }

    public void setRealSkuTitle(String realSkuTitle) {
        this.realSkuTitle = realSkuTitle;
    }

    @Override
    public String getTable() {
        return "error_goods_item";
    }
}
