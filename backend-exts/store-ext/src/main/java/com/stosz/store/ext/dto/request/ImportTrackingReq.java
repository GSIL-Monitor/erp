package com.stosz.store.ext.dto.request;

import java.io.Serializable;

/**
 * @Description: 导入运单
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
public class ImportTrackingReq implements Serializable {
    /**
     * 仓库id
     */
    private Integer wmsId;

    /**
     * 仓库名称
     */
    private String stockName;

    /**
     * 运单信息
     * 格式：运单,库位|1111,1号库位|2222|333,4号库位
     */
    private String track;


    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

}
