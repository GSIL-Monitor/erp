package com.stosz.store.ext.dto.request;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 搜索报废
 * @auther ChenShifeng
 * @Date Create time    2017/12/22
 */
public class SearchInvalidReq implements Serializable {

    /**
     * 报废单号
     */
    private Integer id;

    /**
     * 转寄仓id
     */
    private Integer transitId;

    /**
     * 创建人
     */
    private String creator;


    /**
     * 创建开始时间
     */
    private LocalDateTime createAtStartTime;

    /**
     * 创建结束时间
     */
    private LocalDateTime createAtEndTime;

    /**
     * 起始位置
     */
    private Integer start = 0;

    /**
     * 显示条数
     */
    private Integer limit = 20;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransitId() {
        return transitId;
    }

    public void setTransitId(Integer transitId) {
        this.transitId = transitId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
