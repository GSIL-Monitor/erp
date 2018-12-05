package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:出入仓，仓库数据调整接口相关参数
 * @Created Time:2017/11/23 14:57
 * @Update Time:
 */
public class StockChangeReq implements Serializable{

    /**
     * 仓库id
     **/
    private Integer wmsId;
    /**
     * 部门id
     **/
    private Integer deptId;
    /**
     * 部门编号
     **/
    private String deptNo;
    /**
     * 部门名称
     **/
    private String deptName;
    /**
     * 产品spu
     **/
    private String spu;
    /**
     * 产品sku
     **/
    private String sku;
    /**
     * 改变数量（统一记正数）
     **/
    private int changeQty;
    /**
     * 操作类型
     **/
    private String type;
    /**
     * 业务单号
     **/
    private String voucherNo;
    /**
     * 业务单号涉及金额
     **/
    private BigDecimal amount;
    /**
     * 业务单号时间
     **/
    private LocalDateTime changeAt;

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
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

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(int changeQty) {
        this.changeQty = changeQty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(LocalDateTime changeAt) {
        this.changeAt = changeAt;
    }

    @Override
    public String toString() {
        return "StockChangeReq{" +
                "wmsId=" + wmsId +
                ", deptId=" + deptId +
                ", deptNo=" + deptNo +
                ", deptName=" + deptName +
                ", spu='" + spu + '\'' +
                ", sku='" + sku + '\'' +
                ", changeQty=" + changeQty +
                ", type='" + type + '\'' +
                ", voucherNo='" + voucherNo + '\'' +
                ", amount=" + amount +
                ", changeAt=" + changeAt +
                '}';
    }
}
