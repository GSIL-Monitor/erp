package com.stosz.store.ext.model;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/11/28 17:17
 */
public class TransferInfo {

    private Integer outWmsId;//调出仓库id
    private Integer inWmsId;//调入仓库id
    private String[] ids;//运单号,转寄仓调出记录运单号或者sku加数量组合
    private String description;//备注

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

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
