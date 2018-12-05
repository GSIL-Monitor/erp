package com.stosz.store.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.store.ext.enums.TransferStateEnum;
import com.stosz.store.ext.enums.TransferTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author:yangqinghui
 * @Description:Transfer 调拨单表
 * @Created Time:2017/11/28 16:34
 * @Update Time:2017/11/28 16:34
 */
public class Transfer extends AbstractParamEntity implements ITable, Serializable, IFsmInstance {

    @DBColumn
    private Integer id;
    @DBColumn
    private Integer outWmsId;//调出仓库id
    @DBColumn
    private Integer inWmsId;//调入仓库id
    @DBColumn
    private String state;//状态 0：待审核 1,出仓部门审核 2,入仓部门审核 3,审核通过 4,审核不通过 5,已出库 6,已入库 7,完成
    @DBColumn
    private Integer type;//调拨类型 0:同仓调拨 1,普通仓调普通仓 2,转寄仓转普通仓 3,转寄仓转转寄仓 4,转寄仓同仓调拨
    @DBColumn
    private String description;//描述
    @DBColumn
    private Integer transferQty;//调拨数量
    @DBColumn
    private String approver;//审核人
    @DBColumn
    private LocalDateTime passTime;//审核通过时间
    @DBColumn
    private LocalDateTime outStockTime;//出库时间
    @DBColumn
    private LocalDateTime inStockTime;//入库时间
    @DBColumn
    private LocalDateTime stateTime;//状态更改时间
    @DBColumn
    private LocalDateTime createAt;//创建时间
    @DBColumn
    private LocalDateTime updateAt;//修改时间
    @DBColumn
    private String creator;//创建人
    @DBColumn
    private Integer creatorId;//创建人id

    @DBColumn
    private Integer createDeptId;//创建人部门id
    @DBColumn
    private Integer inDeptId;//调入部门id
    @DBColumn
    private Integer outDeptId;//调出部门id

    private transient TransferTypeEnum transferTypeEnum;

    private transient TransferStateEnum transferStateEnum;

    private transient String sku;//sku

    private transient String outWmsName;//调出仓库

    private transient String inWmsName;//调入仓库

    private transient String outDeptName;//调出部门名称

    private transient String inDeptName;//调入部门名称

    public Transfer() {
    }

    public TransferStateEnum getTransferStateEnum() {
        return state == null ? null : TransferStateEnum.fromName(state);
    }

    public void setTransferStateEnum(TransferStateEnum transferStateEnum) {
        this.transferStateEnum = transferStateEnum;
    }

    public String getOutDeptName() {
        return outDeptName;
    }

    public void setOutDeptName(String outDeptName) {
        this.outDeptName = outDeptName;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        this.inDeptName = inDeptName;
    }

    public Integer getCreateDeptId() {
        return createDeptId;
    }

    public void setCreateDeptId(Integer createDeptId) {
        this.createDeptId = createDeptId;
    }

    public Integer getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(Integer inDeptId) {
        this.inDeptId = inDeptId;
    }

    public Integer getOutDeptId() {
        return outDeptId;
    }

    public void setOutDeptId(Integer outDeptId) {
        this.outDeptId = outDeptId;
    }

    public String getOutWmsName() {
        return outWmsName;
    }

    public void setOutWmsName(String outWmsName) {
        this.outWmsName = outWmsName;
    }

    public String getInWmsName() {
        return inWmsName;
    }

    public void setInWmsName(String inWmsName) {
        this.inWmsName = inWmsName;
    }

    public TransferTypeEnum getTransferTypeEnum() {
        return type == null ? null : TransferTypeEnum.fromId(type);
    }

    public void setTransferTypeEnum(TransferTypeEnum transferTypeEnum) {
        this.transferTypeEnum = transferTypeEnum;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
    public String getState() {
        return this.state == null ? null : TransferStateEnum.fromName(state).name();
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOutWmsId() {
        return outWmsId;
    }

    public void setOutWmsId(Integer outWmsId) {
        this.outWmsId = outWmsId;
    }

    public Integer getInWmsId() {
        return inWmsId;
    }

    public void setInWmsId(Integer inWmsId) {
        this.inWmsId = inWmsId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTransferQty() {
        return transferQty;
    }

    public void setTransferQty(Integer transferQty) {
        this.transferQty = transferQty;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    public LocalDateTime getOutStockTime() {
        return outStockTime;
    }

    public void setOutStockTime(LocalDateTime outStockTime) {
        this.outStockTime = outStockTime;
    }

    public LocalDateTime getInStockTime() {
        return inStockTime;
    }

    public void setInStockTime(LocalDateTime inStockTime) {
        this.inStockTime = inStockTime;
    }

    public LocalDateTime getStateTime() {
        return stateTime;
    }

    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
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

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getTable() {
        return "transfer";
    }

}
