package com.stosz.store.ext.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description: 调拨单导出 excel
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class ExportTransfer implements Serializable {

    private Integer id;
    private String type;//调拨类型 0:同仓调拨 1,普通仓调普通仓 2,转寄仓转普通仓 3,转寄仓转转寄仓

    private String state;//状态 0：待审核 1,出仓部门审核 2,入仓部门审核 3,审核通过 4,审核不通过 5,已出库 6,已入库 7,完成

    private Integer outWmsId;//调出仓库id

    private String outDept;//调出部门

    private Integer inWmsId;//调入仓库id

    private String inDept;//调入部门

    private Integer transferQty;//调拨数量

    private String productInfo;//产品信息

    private String description;//描述

    private String creator;//创建人

    private LocalDateTime createAt;//创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getOutWmsId() {
        return outWmsId;
    }

    public void setOutWmsId(Integer outWmsId) {
        this.outWmsId = outWmsId;
    }

    public String getOutDept() {
        return outDept;
    }

    public void setOutDept(String outDept) {
        this.outDept = outDept;
    }

    public Integer getInWmsId() {
        return inWmsId;
    }

    public void setInWmsId(Integer inWmsId) {
        this.inWmsId = inWmsId;
    }

    public String getInDept() {
        return inDept;
    }

    public void setInDept(String inDept) {
        this.inDept = inDept;
    }

    public Integer getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(Integer transferQty) {
        this.transferQty = transferQty;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
