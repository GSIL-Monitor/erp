package com.stosz.purchase.ext.common;

import java.io.Serializable;
import java.util.List;

/**
 * 采购信息会写的单据实体
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/25]
 */
public class WmsWriteBackDto implements Serializable {

    private String purchaseNo;

    private Integer departmentId;

    private String warehouseId;

    private String totalReceived;

    private String inTime;

    private Integer type; //1，采购入库，2、采购退货

    private String remark;

    private List<WriteBackItemDto> data;

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(String totalReceived) {
        this.totalReceived = totalReceived;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<WriteBackItemDto> getData() {
        return data;
    }

    public void setData(List<WriteBackItemDto> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WmsWriteBackDto{" +
                "purchaseNo='" + purchaseNo + '\'' +
                ", departmentId=" + departmentId +
                ", warehouseId=" + warehouseId +
                ", totalReceived='" + totalReceived + '\'' +
                ", inTime=" + inTime +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", data=" + data +
                '}';
    }
}
