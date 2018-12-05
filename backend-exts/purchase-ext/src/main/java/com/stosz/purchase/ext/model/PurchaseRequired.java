package com.stosz.purchase.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 采购需求实体
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/7]
 */
public class PurchaseRequired extends AbstractParamEntity implements Serializable, ITable, Cloneable {

    public static final Logger logger = LoggerFactory.getLogger(PurchaseRequired.class);
    private static final long serialVersionUID = -8602067265134972420L;

    @DBColumn
    private Integer id;

    @DBColumn
    private Integer buDeptId;// 事业部ID

    @DBColumn
    private String buDeptName;// 事业部名称

    @DBColumn
    private String buDeptNo;// 事业部编号
    @DBColumn
    private Integer deptId;// 部门ID
    @DBColumn
    private String deptName;// 部门名称
    @DBColumn
    private String deptNo;// 部门编号
    @DBColumn
    private Integer supplierId;// 供应商ID
    @DBColumn
    private String spu;
    @DBColumn
    private String sku;
    @DBColumn
    private Integer orderRequiredQty;// 订单需求数
    @DBColumn
    private Integer stockQty;// 可用库存数
    @DBColumn
    private Integer buyerId;// 采购员ID
    @DBColumn
    private String buyer;// 采购员
    @DBColumn
    private Integer intransitQty;// 在途数
    @DBColumn
    private Integer wmsId;
    @DBColumn
    private String wmsName;
    @DBColumn
    private String wmsSysCode;//仓库编码
    @DBColumn
    private Integer planQty;// 计划采购数
    @DBColumn
    private Integer purchaseQty;// 采购需求数
    @DBColumn
    private Integer avgSaleQty;// 3日平均交易
    @DBColumn
    private Integer pendingOrderQty;// 待审订单数

    private Integer state;// 状态 1 查询当天数据 0 查询明天数据， null 查询所有数据  根据状态判断是要查询当天的还是所有的
    @DBColumn
    private Date createAt;
    @DBColumn
    private Date updateAt;
    @DBColumn
    private Date requiredAppearedTime;

    private Integer productId;// 产品ID

    private String productTitle;// 产品标题

    private String mainImageUrl;// 产品图片

    private String supplierName;// 供应商名
    @DBColumn
    private String skuTitle;// sku标题

    private Integer changeStatus;

    private Boolean hasStock; //显示数据，true:有库存，false:库存不足，null:全部

    private List<PurchaseRequired> childRequiredList;

    private LocalDateTime displayAt;//展现时间

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
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

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuDeptId() {
        return buDeptId;
    }

    public void setBuDeptId(Integer buDeptId) {
        this.buDeptId = buDeptId;
    }

    public String getBuDeptName() {
        return buDeptName;
    }

    public void setBuDeptName(String buDeptName) {
        this.buDeptName = buDeptName == null ? null : buDeptName.trim();
    }

    public String getBuDeptNo() {
        return buDeptNo;
    }

    public void setBuDeptNo(String buDeptNo) {
        this.buDeptNo = buDeptNo == null ? null : buDeptNo.trim();
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
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu == null ? null : spu.trim();
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public Integer getOrderRequiredQty() {
        return orderRequiredQty;
    }

    public void setOrderRequiredQty(Integer orderRequiredQty) {
        this.orderRequiredQty = orderRequiredQty;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
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

    public Integer getIntransitQty() {
        return intransitQty;
    }

    public void setIntransitQty(Integer intransitQty) {
        this.intransitQty = intransitQty;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Date getRequiredAppearedTime() {
        return requiredAppearedTime;
    }

    public void setRequiredAppearedTime(Date requiredAppearedTime) {
        this.requiredAppearedTime = requiredAppearedTime;
    }

    @Override
    public String getTable() {
        return "purchase_required";
    }

    public List<PurchaseRequired> getChildRequiredList() {
        return childRequiredList;
    }

    public void setChildRequiredList(List<PurchaseRequired> childRequiredList) {
        this.childRequiredList = childRequiredList;
    }

    public Boolean getHasStock() {
        return hasStock;
    }

    public void setHasStock(Boolean hasStock) {
        this.hasStock = hasStock;
    }

    public String getWmsName() {
        return wmsName;
    }

    public void setWmsName(String wmsName) {
        this.wmsName = wmsName;
    }

    public String getWmsSysCode() {
        return wmsSysCode;
    }

    public void setWmsSysCode(String wmsSysCode) {
        this.wmsSysCode = wmsSysCode;
    }

    public LocalDateTime getDisplayAt() {
        return displayAt;
    }

    public void setDisplayAt(LocalDateTime displayAt) {
        this.displayAt = displayAt;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public PurchaseRequired cloneValue() {
        try {
            return (PurchaseRequired) this.clone();
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}