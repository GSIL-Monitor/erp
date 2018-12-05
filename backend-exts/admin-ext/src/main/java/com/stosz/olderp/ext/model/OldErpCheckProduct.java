package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/10]
 */
public class OldErpCheckProduct extends AbstractParamEntity implements ITable, Serializable {
    @DBColumn
    private Integer id;
    @DBColumn
    private Integer idUsers;
    @DBColumn
    private String userName;
    @DBColumn
    private String title;
    @DBColumn
    private Integer idCategory;
    @DBColumn
    private Integer idClassify;
    @DBColumn
    private String thumbs;
    @DBColumn
    private String saleUrl;
    @DBColumn
    private String purchaseUrl;
    @DBColumn
    private Integer status;
    @DBColumn
    private String desc;
    @DBColumn
    private Integer idDepartment;
    @DBColumn
    private Integer idZone;
    @DBColumn
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getIdClassify() {
        return idClassify;
    }

    public void setIdClassify(Integer idClassify) {
        this.idClassify = idClassify;
    }

    public String getThumbs() {
        return thumbs;
    }

    public void setThumbs(String thumbs) {
        this.thumbs = thumbs;
    }

    public String getSaleUrl() {
        return saleUrl;
    }

    public void setSaleUrl(String saleUrl) {
        this.saleUrl = saleUrl;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String getTable() {
        return "erp_product_temp";
    }


}
