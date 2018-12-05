package com.stosz.olderp.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ErpWarehouseForbid extends AbstractParamEntity implements ITable, Serializable  {

    private Integer idForbid;

    /**
     * 地区
     */
    private Integer idZone;

    private Integer idWarehouse;

    private Integer idProduct;

    /**
     * 创建人
     */
    private Integer idUsers;

    private Date ctime;

    private static final long serialVersionUID = 1L;

    public Integer getIdForbid() {
        return idForbid;
    }

    public void setIdForbid(Integer idForbid) {
        this.idForbid = idForbid;
    }

    public Integer getIdZone() {
        return idZone;
    }

    public void setIdZone(Integer idZone) {
        this.idZone = idZone;
    }

    public Integer getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(Integer idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }


    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", idForbid=").append(idForbid);
        sb.append(", idZone=").append(idZone);
        sb.append(", idWarehouse=").append(idWarehouse);
        sb.append(", idProduct=").append(idProduct);
        sb.append(", idUsers=").append(idUsers);
        sb.append(", ctime=").append(ctime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String getTable() {
        return "erp_warehouse_forbid";
    }
}