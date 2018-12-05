package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:56
 */
public class WmsTransferReq implements Serializable {

    private String transferNo;//
    private String type;//类型
    private String outWarehouseId;//调出仓库id
    private String inWarehouseId;//调入仓库id
    private String shippingName;//调出货主
    private String shippingNo;//调入货主
    private String outDepartment;//调出仓库id
    private String inDepartment;//调入仓库id
    private String outTime;//调出货主
    private String remark;//调入货主
    private List<WmsReqSku> data;


    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOutWarehouseId() {
        return outWarehouseId;
    }

    public void setOutWarehouseId(String outWarehouseId) {
        this.outWarehouseId = outWarehouseId;
    }

    public String getInWarehouseId() {
        return inWarehouseId;
    }

    public void setInWarehouseId(String inWarehouseId) {
        this.inWarehouseId = inWarehouseId;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getOutDepartment() {
        return outDepartment;
    }

    public void setOutDepartment(String outDepartment) {
        this.outDepartment = outDepartment;
    }

    public String getInDepartment() {
        return inDepartment;
    }

    public void setInDepartment(String inDepartment) {
        this.inDepartment = inDepartment;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<WmsReqSku> getData() {
        return data;
    }

    public void setData(List<WmsReqSku> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WmsTransferReq{" +
                "transferNo='" + transferNo + '\'' +
                ", type='" + type + '\'' +
                ", outWarehouseId='" + outWarehouseId + '\'' +
                ", inWarehouseId='" + inWarehouseId + '\'' +
                ", shippingName='" + shippingName + '\'' +
                ", shippingNo='" + shippingNo + '\'' +
                ", outDepartment='" + outDepartment + '\'' +
                ", inDepartment='" + inDepartment + '\'' +
                ", outTime='" + outTime + '\'' +
                ", remark='" + remark + '\'' +
                ", data=" + data +
                '}';
    }
}
