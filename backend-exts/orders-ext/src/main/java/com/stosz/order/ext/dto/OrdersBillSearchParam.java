package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangqian
 * 订单列表的搜索条件
 */
public class OrdersBillSearchParam {

    //部门权限
    private List<Integer> departmentIds;
    //地区权限
    private List<Integer> zoneIds;
    //退货/拒收单ID
    private String rmaId;
    //订单ID
    private String orderId;
    /**
     * 建站的订单编号
     */
    private String merchantOrderNo;
    /**
     * 站点来源
     */
    private String webUrl;
    /**
     * 0退货 1拒收
     */
    private ChangeTypeEnum changeTypeEnum;
    /**
     * 是否回收商品0 不回收 1回收
     */
    private RecycleGoodsEnum recycleGoodsEnum;
    /**
     * 售后来源0 售后邮件 1问题件 2物流自退
     */
    private RmaSourceEnum rmaSourceEnum;
    /**
     * 退换货的原因 0 质量原因 1 货不对版 2 个人原因 3 丢件 4 清关失败 5 其它
     */
    private ChangeReasonEnum changeReasonEnum;
    /**
     * 退货单的状态 0 草稿 1 待审核 2  待收货 3 收货完成 4 审核不通过 5取消
     * 拒收状态 2、待收货 3 收货完成 5 取消
     */
    private OrdersRmaStateEnum rmaStatusEnum;

    /**
     * 申请起始时间
     */
    private LocalDateTime applyStartTime;

    /**
     * 申请结束时间
     */
    private LocalDateTime applyEndTime;

    /**
     * 用户权限
     *************************************/
    private Integer creatorId;
    /**
     * 起始位置
     */
    private Integer start = 0;

    /**
     * 显示条数
     */
    private Integer limit = 20;

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getRmaId() {
        return rmaId;
    }

    public void setRmaId(String rmaId) {
        this.rmaId = rmaId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    /**
     * 排序(升序asc, 降序desc)
     */
    private String sortOrder;

    /**
     * 排序字段(暂无用，现在只有根据展示时间排序)
     */
    private String sortName;


    public List<Integer> getDepartmentIds() {
        return departmentIds;
    }


    public void setDepartmentIds(List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }


    public List<Integer> getZoneIds() {
        return zoneIds;
    }


    public void setZoneIds(List<Integer> zoneIds) {
        this.zoneIds = zoneIds;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public ChangeTypeEnum getChangeTypeEnum() {
        return changeTypeEnum;
    }

    public void setChangeTypeEnum(ChangeTypeEnum changeTypeEnum) {
        this.changeTypeEnum = changeTypeEnum;
    }

    public RecycleGoodsEnum getRecycleGoodsEnum() {
        return recycleGoodsEnum;
    }

    public void setRecycleGoodsEnum(RecycleGoodsEnum recycleGoodsEnum) {
        this.recycleGoodsEnum = recycleGoodsEnum;
    }

    public RmaSourceEnum getRmaSourceEnum() {
        return rmaSourceEnum;
    }

    public void setRmaSourceEnum(RmaSourceEnum rmaSourceEnum) {
        this.rmaSourceEnum = rmaSourceEnum;
    }

    public ChangeReasonEnum getChangeReasonEnum() {
        return changeReasonEnum;
    }

    public void setChangeReasonEnum(ChangeReasonEnum changeReasonEnum) {
        this.changeReasonEnum = changeReasonEnum;
    }

    public OrdersRmaStateEnum getRmaStatusEnum() {
        return rmaStatusEnum;
    }

    public void setRmaStatusEnum(OrdersRmaStateEnum rmaStatusEnum) {
        this.rmaStatusEnum = rmaStatusEnum;
    }

    public LocalDateTime getApplyStartTime() {
        return applyStartTime;
    }


    public void setApplyStartTime(LocalDateTime applyStartTime) {
        this.applyStartTime = applyStartTime;
    }


    public LocalDateTime getApplyEndTime() {
        return applyEndTime;
    }


    public void setApplyEndTime(LocalDateTime applyEndTime) {
        this.applyEndTime = applyEndTime;
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


    public String getSortOrder() {
        return sortOrder;
    }


    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }


    public String getSortName() {
        return sortName;
    }


    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

}
