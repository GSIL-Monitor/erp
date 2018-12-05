package com.stosz.purchase.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseIn extends AbstractParamEntity implements ITable,Serializable{

    @DBColumn
    private Integer id;
    @DBColumn
    private LocalDateTime createAt;

    private LocalDateTime updateAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;
    @DBColumn
    private Integer purchaseId;
    @DBColumn
    private LocalDateTime instockTime;
    @DBColumn
    private Integer wmsId;
    @DBColumn
    private String wmsSysCode;
    @DBColumn
    private String wmsName;
    @DBColumn
    private Integer buDeptId;
    @DBColumn
    private String buDeptNo;
    @DBColumn
    private String buDeptName;
    @DBColumn
    private Integer instockUserId;
    @DBColumn
    private String instockUser;
    @DBColumn
    private String trakingNo;
    @DBColumn
    private Integer platId;
    @DBColumn
    private String platName;
    @DBColumn
    private Integer quantity;
    @DBColumn
    private Integer instockQty;
    @DBColumn
    private String platOrdersNo;
    @DBColumn
    private BigDecimal feightPrice;
    @DBColumn
    private String purchaseNo;


    @Override
    public Integer getId() {
        return id;
    }


    @Override
    public void setId(Integer id) {
        this.id = id;
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
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDateTime getInstockTime() {
        return instockTime;
    }

    public void setInstockTime(LocalDateTime instockTime) {
        this.instockTime = instockTime;
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

    public Integer getInstockUserId() {
        return instockUserId;
    }

    public void setInstockUserId(Integer instockUserId) {
        this.instockUserId = instockUserId;
    }

    public String getInstockUser() {
        return instockUser;
    }

    public void setInstockUser(String instockUser) {
        this.instockUser = instockUser;
    }

    public String getTrakingNo() {
        return trakingNo;
    }

    public void setTrakingNo(String trakingNo) {
        this.trakingNo = trakingNo;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getInstockQty() {
        return instockQty;
    }

    public void setInstockQty(Integer instockQty) {
        this.instockQty = instockQty;
    }

    public String getPlatOrdersNo() {
        return platOrdersNo;
    }

    public void setPlatOrdersNo(String platOrdersNo) {
        this.platOrdersNo = platOrdersNo;
    }

    public BigDecimal getFeightPrice() {
        return feightPrice;
    }

    public void setFeightPrice(BigDecimal feightPrice) {
        this.feightPrice = feightPrice;
    }


    @Override
    public String getTable() {
        return "purchase_in";
    }
}