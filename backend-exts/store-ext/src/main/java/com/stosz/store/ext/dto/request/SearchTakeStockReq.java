package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 搜索盘点
 * @auther ChenShifeng
 * @Date Create time    2017/12/22
 */
public class SearchTakeStockReq implements Serializable {

    /**
     * 仓库
     */
    private Integer wmsId;

    /**
     * 产品名
     */
    private String innerName;

    /**
     * 采购单号
     */
    private Integer id;

    /**
     * 状态
     */
    private String state;

    /**
     * 审核人
     */
    private String approver;


    /**
     * 创建开始时间
     */
    private LocalDateTime createAtStartTime;

    /**
     * 创建结束时间
     */
    private LocalDateTime createAtEndTime;

    /**
     * sku
     */
    private String sku;

    /**
     * 起始位置
     */
    private Integer start = 0;

    /**
     * 显示条数
     */
    private Integer limit = 20;

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public LocalDateTime getCreateAtStartTime() {
        return createAtStartTime;
    }

    public void setCreateAtStartTime(LocalDateTime createAtStartTime) {
        this.createAtStartTime = createAtStartTime;
    }

    public LocalDateTime getCreateAtEndTime() {
        return createAtEndTime;
    }

    public void setCreateAtEndTime(LocalDateTime createAtEndTime) {
        this.createAtEndTime = createAtEndTime;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
