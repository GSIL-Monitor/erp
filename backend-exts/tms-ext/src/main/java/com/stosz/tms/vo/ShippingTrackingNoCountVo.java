package com.stosz.tms.vo;

public class ShippingTrackingNoCountVo {

    private Integer allCount; //运单号全部数量

    private Integer unusedCount; //未使用数量


    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(Integer unusedCount) {
        this.unusedCount = unusedCount;
    }
}
