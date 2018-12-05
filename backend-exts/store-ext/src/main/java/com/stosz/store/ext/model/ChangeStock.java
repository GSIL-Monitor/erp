package com.stosz.store.ext.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:出入仓，仓库数据调整接口相关参数
 * @Created Time:2017/11/23 14:57
 * @Update Time:
 */
public class ChangeStock implements Serializable {

    /**仓库id**/
    private Integer wmsId;
    /**部门id**/
    private Integer deptId;
    /**部门编号**/
    private String deptNo;
    /**部门名称**/
    private String deptName;
    /** 产品spu **/
    private String spu;
    /** 产品sku **/
    private String sku;
    /** 在库数的改变量（增加记正,减少记负） **/
    private int instockChangeQty;
    /** 可用数的改变量（增加记正,减少记负） **/
    private int usableChangeQty;
    /** 占用数的改变量（增加记正,减少记负）**/
    private int occupyChangeQty;
    /** 在途数的改变量（增加记正,减少记负）**/
    private int intransitChangeQty;

    /** 是否需要记录出入库流水（true：则必须有以下信息 false：不需要以下信息）**/
    private Boolean isRecord;
    /** 业务单号**/
    private String voucherNo;
    /** 业务单号改变的数量**/
    private int quantity;
    /** 业务单号涉及数量的进出状态**/
    private Integer state;
    /** 业务单号涉及金额**/
    private BigDecimal amount;
    /** 业务单号类型**/
    private Integer type;
    /** 业务单号时间**/
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

    public int getInstockChangeQty() {
        return instockChangeQty;
    }

    public void setInstockChangeQty(int instockChangeQty) {
        this.instockChangeQty = instockChangeQty;
    }

    public int getUsableChangeQty() {
        return usableChangeQty;
    }

    public void setUsableChangeQty(int usableChangeQty) {
        this.usableChangeQty = usableChangeQty;
    }

    public int getOccupyChangeQty() {
        return occupyChangeQty;
    }

    public void setOccupyChangeQty(int occupyChangeQty) {
        this.occupyChangeQty = occupyChangeQty;
    }

    public int getIntransitChangeQty() {
        return intransitChangeQty;
    }

    public void setIntransitChangeQty(int intransitChangeQty) {
        this.intransitChangeQty = intransitChangeQty;
    }

    public Boolean getRecord() {
        return isRecord;
    }

    public void setRecord(Boolean record) {
        isRecord = record;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
        return "ChangeStock{" +
                "wmsId=" + wmsId +
                ", deptId=" + deptId +
                ", deptNo=" + deptNo +
                ", deptName=" + deptName +
                ", spu='" + spu + '\'' +
                ", sku='" + sku + '\'' +
                ", instockChangeQty=" + instockChangeQty +
                ", usableChangeQty=" + usableChangeQty +
                ", occupyChangeQty=" + occupyChangeQty +
                ", intransitChangeQty=" + intransitChangeQty +
                ", isRecord=" + isRecord +
                ", voucherNo='" + voucherNo + '\'' +
                ", quantity=" + quantity +
                ", state='" + state + '\'' +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", changeAt=" + changeAt +
                '}';
    }
}
