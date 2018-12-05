package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.util.List;

public class AddTakeStockReq implements Serializable{

    private List<String> trackings;// 运单号

    private String memo;//备注

    private Integer takeStockId;//盘点单id

    private String stockName;//仓库名

    private Integer wmsId;//仓库id

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public Integer getTakeStockId() {
        return takeStockId;
    }

    public void setTakeStockId(Integer takeStockId) {
        this.takeStockId = takeStockId;
    }

    public List<String> getTrackings() {
        return trackings;
    }

    public void setTrackings(List<String> trackings) {
        this.trackings = trackings;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
