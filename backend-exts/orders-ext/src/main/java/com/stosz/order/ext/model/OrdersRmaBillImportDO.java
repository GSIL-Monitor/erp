package com.stosz.order.ext.model;

import com.stosz.order.ext.dto.ModelProp;
import com.stosz.order.ext.dto.ModelTitle;
import com.stosz.order.ext.enums.ChangeReasonEnum;
import com.stosz.order.ext.enums.ChangeWayEnum;
import com.stosz.order.ext.enums.RecycleGoodsEnum;
import com.stosz.order.ext.enums.RmaSourceEnum;

import java.io.Serializable;

/**
 * RMA导入实体
 */
@ModelTitle(name = "RMA导入实体")
public class OrdersRmaBillImportDO implements Serializable {
    /**
     * 订单号
     */
    @ModelProp(name = "订单号", importIndex = 0, nullable = true)
    private Integer orderId;
    /**
     * 默认是订单中的运单号，处于待发货的时候可以修改成新的运单号
     */
    @ModelProp(name = "发货运单号", importIndex = 1, nullable = true)
    private String trackingNo;
    /**
     * 售后来源
     */
    @ModelProp(name = "售后来源", importIndex = 2, nullable = true)
    private String rmaSourceEnum;
    /**
     * 退款方式 0未设置 1物流自退
     */
    @ModelProp(name = "退款方式", importIndex = 3, nullable = true)
    private String changeWayEnum;
    /**
     * 退换货的原因 0 质量原因 1 货不对版 2 个人原因 3 丢件 4 清关失败 5 其它
     */
    @ModelProp(name = "退货原因", importIndex = 4, nullable = true)
    private String changeReasonEnum;
    /**
     * 5 其它时此项必填
     */
    @ModelProp(name = "说明", importIndex = 5, nullable = false)
    private String changeReasonOtherMemo;

    /**
     * 是否回收商品0 不回收 1回收
     */
    @ModelProp(name = "是否回收", importIndex = 6, nullable = true)
    private String recycleGoodsEnum;

    /**
     * 问题备注描述
     */
    @ModelProp(name = "备注", importIndex = 7, nullable = false)
    private String applyMemo;

    /**
     * 售后来源
     */
    private RmaSourceEnum sourceEnum = RmaSourceEnum.logisticregression;
    /**
     * 退货方式
     */
    private ChangeWayEnum wayEnum = ChangeWayEnum.logisticsBack;
    /**
     * 换货原因
     */
    private ChangeReasonEnum reasonEnum = ChangeReasonEnum.quality;
    /**
     * 是否回收
     */
    private RecycleGoodsEnum goodsEnum = RecycleGoodsEnum.yes;


    /**
     * 导入错误原因
     */
    private String errorMsg;


    public String getRmaSourceEnum() {
        return rmaSourceEnum;
    }

    public void setRmaSourceEnum(String rmaSourceEnum) {
        this.rmaSourceEnum = rmaSourceEnum;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getChangeReasonEnum() {
        return changeReasonEnum;
    }

    public void setChangeReasonEnum(String changeReasonEnum) {
        this.changeReasonEnum = changeReasonEnum;
    }

    public String getChangeReasonOtherMemo() {
        return changeReasonOtherMemo;
    }

    public void setChangeReasonOtherMemo(String changeReasonOtherMemo) {
        this.changeReasonOtherMemo = changeReasonOtherMemo;
    }

    public String getChangeWayEnum() {
        return changeWayEnum;
    }

    public void setChangeWayEnum(String changeWayEnum) {
        this.changeWayEnum = changeWayEnum;
    }

    public String getRecycleGoodsEnum() {
        return recycleGoodsEnum;
    }

    public void setRecycleGoodsEnum(String recycleGoodsEnum) {
        this.recycleGoodsEnum = recycleGoodsEnum;
    }


    public String getApplyMemo() {
        return applyMemo;
    }

    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }

    public RmaSourceEnum getSourceEnum() {
        return sourceEnum;
    }

    public void setSourceEnum(RmaSourceEnum sourceEnum) {
        this.sourceEnum = sourceEnum;
    }

    public ChangeWayEnum getWayEnum() {
        return wayEnum;
    }

    public void setWayEnum(ChangeWayEnum wayEnum) {
        this.wayEnum = wayEnum;
    }

    public ChangeReasonEnum getReasonEnum() {
        return reasonEnum;
    }

    public void setReasonEnum(ChangeReasonEnum reasonEnum) {
        this.reasonEnum = reasonEnum;
    }

    public RecycleGoodsEnum getGoodsEnum() {
        return goodsEnum;
    }

    public void setGoodsEnum(RecycleGoodsEnum goodsEnum) {
        this.goodsEnum = goodsEnum;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}