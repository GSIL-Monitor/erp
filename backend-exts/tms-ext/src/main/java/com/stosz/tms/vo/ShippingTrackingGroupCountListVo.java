package com.stosz.tms.vo;

public class ShippingTrackingGroupCountListVo {

    private Integer shippingWayId;

    private String shippingWayName;

    private Integer productType;

    private int allCount;

    private int unusedCount;

    private int usedCount;

    private int disableCount;

    public Integer getShippingWayId() {
        return shippingWayId;
    }

    public void setShippingWayId(Integer shippingWayId) {
        this.shippingWayId = shippingWayId;
    }

    public String getShippingWayName() {
        return shippingWayName;
    }

    public void setShippingWayName(String shippingWayName) {
        this.shippingWayName = shippingWayName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getUnusedCount() {
        return unusedCount;
    }

    public void setUnusedCount(int unusedCount) {
        this.unusedCount = unusedCount;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public int getDisableCount() {
        return disableCount;
    }

    public void setDisableCount(int disableCount) {
        this.disableCount = disableCount;
    }
}
