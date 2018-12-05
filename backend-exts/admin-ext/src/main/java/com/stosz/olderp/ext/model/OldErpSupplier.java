package com.stosz.olderp.ext.model;

import java.io.Serializable;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/27]
 */
public class OldErpSupplier  implements Serializable {

    /**
     * 主键id
     */
    private Integer idSupplier;
    /**
     * 部门id
     */
    private Integer idDepartment;
    /**
     * 标题
     */
    private String title;
    /**
     * 电话
     */
    private String phone;
    /**
     * 地址
     */
    private String address;
    /**
     * URL
     */
    private String supplierUrl;

    public Integer getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Integer idSupplier) {
        this.idSupplier = idSupplier;
    }

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplierUrl() {
        return supplierUrl;
    }

    public void setSupplierUrl(String supplierUrl) {
        this.supplierUrl = supplierUrl;
    }

    @Override
    public String toString() {
        return "OldErpSupplier{" +
                "idSupplier=" + idSupplier +
                ", idDepartment=" + idDepartment +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", supplierUrl='" + supplierUrl + '\'' +
                '}';
    }
}
