package com.stosz.order.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.order.ext.enums.*;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司 退换货实体类
 */
public class OrdersRmaBill extends AbstractParamEntity implements ITable, Serializable, IFsmInstance {
    /**
     * 业务主键
     */
    @DBColumn
    private Integer id;

    /**
     * 建站订单号
     */
    @DBColumn
    private String merchantOrderNo;

    /**
     * 默认是订单中的运单号，处于待发货的时候可以修改成新的运单号
     */
    @DBColumn
    private String trackingNo;

    /**
     * 物流企业id
     */
    @DBColumn
    private Integer logisticsId;

    /**
     * 物流企业名称
     */
    @DBColumn
    private String logisticName;
    /**
     * 仓库ID
     */
    @DBColumn
    private Integer  warehouseId;
    /**
     * 仓库名称
     */
    @DBColumn
    private String  warehouseName;
    /**
     * 币种名称
     */
    @DBColumn
    private String currencyName;
    /**
     * 订单总价
     */
    @DBColumn
    private BigDecimal orderAmount;

    /**
     * 左货币符号
     */
    @DBColumn
    private String leftSymbol;

    /**
     * 右货币符号
     */
    @DBColumn
    private String rightSymbol;

    /**
     * 部门id
     */
    @DBColumn
    private Integer seoDepartmentId;

    /**
     * 页面显示的部门+负责人
     */
    @DBColumn
    private String departmentUserInfo;

    /**
     * 站点来源
     */
    @DBColumn
    private String webUrl;

    /**
     * 0退货 1拒收
     */
    @DBColumn
    private ChangeTypeEnum changeTypeEnum;

    /**
     * 退换货的原因 0 质量原因 1 货不对版 2 个人原因 3 丢件 4 清关失败 5 其它
     */
    @DBColumn
    private ChangeReasonEnum changeReasonEnum;

    /**
     * 5 其它时此项必填
     */
    @DBColumn
    private String changeReasonOtherMemo;

    /**
     * 退款方式 0未设置 1物流自退
     */
    @DBColumn
    private ChangeWayEnum changeWayEnum;

    /**
     * 是否回收商品0 不回收 1回收
     */
    @DBColumn
    private RecycleGoodsEnum recycleGoodsEnum;

    /**
     * 售后来源0 售后邮件 1问题件 2物流自退
     */
    @DBColumn
    private RmaSourceEnum rmaSourceEnum;
    /**
     * 退货单的状态 0 草稿 1 待审核 2  待收货 3 收货完成 4 审核不通过 5取消
     拒收状态 2、待收货 3 收货完成 5 取消
     */
    @DBColumn
    private OrdersRmaStateEnum rmaStatusEnum;

    /**
     * 订单下单时间
     */
    @DBColumn
    private LocalDateTime purchaseAt;

    /**
     * 付款类型 0 货到付款 1 在线支付
     */
    @DBColumn
    private PayMethodEnum payMethodEnum;

    /**
     * 退款金额
     */
    @DBColumn
    private BigDecimal refundAmount;

    /**
     * 申请人id
     */
    @DBColumn
    private Integer applyUserId;

    /**
     * 申请人姓名
     */
    @DBColumn
    private String applyUserName;

    /**
     * 申请时间
     */
    @DBColumn
    private LocalDateTime applyTime;

    /**
     * 申请备注
     */
    @DBColumn
    private String applyMemo;

    /**
     * 问题备注描述
     */
    @DBColumn
    private String questionMemo;

    /**
     * 审核人id
     */
    @DBColumn
    private Integer checkUserId;

    /**
     * 审核人姓名
     */
    @DBColumn
    private String checkUserName;

    /**
     * 审核时间
     */
    @DBColumn
    private LocalDateTime checkTime;

    /**
     * 审核备注
     */
    @DBColumn
    private String checkMemo;

    /**
     * 订单id
     */
    @DBColumn
    private Long ordersId;

    /**
     * 退货OR拒收日期
     */
    @DBColumn
    private LocalDateTime changeAt;

    /**
     * 创建时间
     */
    @DBColumn
    private LocalDateTime createAt;

    /**
     * 更新时间
     */
    @DBColumn
    private LocalDateTime updateAt;

    /**
     * 创建者id
     */
    @DBColumn
    private Integer creatorId;

    /**
     * 创建人
     */
    @DBColumn
    private String creator;

    /**
     * 带货币符号的退款金额
     */
    @DBColumn
    private String refundAmountShow;

    /**
     * 状态机时间
     */
    @DBColumn
    private LocalDateTime stateTime;

    /**
     * 退款单号(id)
     */
    @DBColumn
    private Long refundId;
    /**
     * 发货运单号
     */
    @DBColumn
    private String orderTrackingNo;

    /**
     * 客户收款账号
     */
    @DBColumn
    private String customerGetAccount;
    /**
     *退款物流企业id
     */
    @DBColumn
    private Integer refundLogisticsId;
    /**
     * 退款物流企业名称
     */
    @DBColumn
    private String refundLogisticName;
    /**
     * 凭据图片json数组,field:url,orderNo
     */
    @DBColumn
    private String evidenceImageJsonArray;

    @DBColumn
    private Integer zoneId;

    public String getRefundLogisticName() {
        return refundLogisticName;
    }

    public void setRefundLogisticName(String refundLogisticName) {
        this.refundLogisticName = refundLogisticName;
    }

    public Integer getRefundLogisticsId() {
        return refundLogisticsId;
    }

    public void setRefundLogisticsId(Integer refundLogisticsId) {
        this.refundLogisticsId = refundLogisticsId;
    }

    public String getOrderTrackingNo() {
        return orderTrackingNo;
    }

    public void setOrderTrackingNo(String orderTrackingNo) {
        this.orderTrackingNo = orderTrackingNo;
    }

    private static final long serialVersionUID = 1L;

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getChangeReasonOtherMemo() {
        return changeReasonOtherMemo;
    }

    public void setChangeReasonOtherMemo(String changeReasonOtherMemo) {
        this.changeReasonOtherMemo = changeReasonOtherMemo;
    }

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticName() {
        return logisticName;
    }

    public void setLogisticName(String logisticName) {
        this.logisticName = logisticName;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getSeoDepartmentId() {
        return seoDepartmentId;
    }

    public void setSeoDepartmentId(Integer seoDepartmentId) {
        this.seoDepartmentId = seoDepartmentId;
    }

    public String getDepartmentUserInfo() {
        return departmentUserInfo;
    }

    public void setDepartmentUserInfo(String departmentUserInfo) {
        this.departmentUserInfo = departmentUserInfo;
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

    public PayMethodEnum getPayMethodEnum() {
        return payMethodEnum;
    }

    public void setPayMethodEnum(PayMethodEnum payMethodEnum) {
        this.payMethodEnum = payMethodEnum;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundAmountShow() {
        return refundAmountShow;
    }

    public void setRefundAmountShow(String refundAmountShow) {
        this.refundAmountShow = refundAmountShow;
    }

    public ChangeWayEnum getChangeWayEnum() {
        return changeWayEnum;
    }

    public void setChangeWayEnum(ChangeWayEnum changeWayEnum) {
        this.changeWayEnum = changeWayEnum;
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

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyMemo() {
        return applyMemo;
    }

    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }

    public String getQuestionMemo() {
        return questionMemo;
    }

    public void setQuestionMemo(String questionMemo) {
        this.questionMemo = questionMemo;
    }

    public Integer getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Integer checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalDateTime checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckMemo() {
        return checkMemo;
    }

    public void setCheckMemo(String checkMemo) {
        this.checkMemo = checkMemo;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public LocalDateTime getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(LocalDateTime changeAt) {
        this.changeAt = changeAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public Integer getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public Integer getId() {
        return id.intValue();
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public Integer getParentId() {
        return getId();
    }

    @Override
    public String getState() {
        return rmaStatusEnum.name();
    }

    @Override
    public void setState(String state) {
        rmaStatusEnum = OrdersRmaStateEnum.fromName(state);
    }

    @Override
    public LocalDateTime getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public String getCustomerGetAccount() {
        return customerGetAccount;
    }

    public void setCustomerGetAccount(String customerGetAccount) {
        this.customerGetAccount = customerGetAccount;
    }

    public String getEvidenceImageJsonArray() {
        return evidenceImageJsonArray;
    }

    public void setEvidenceImageJsonArray(String evidenceImageJsonArray) {
        this.evidenceImageJsonArray = evidenceImageJsonArray;
    }

    public String getLeftSymbol() {
        return leftSymbol;
    }

    public void setLeftSymbol(String leftSymbol) {
        this.leftSymbol = leftSymbol;
    }

    public String getRightSymbol() {
        return rightSymbol;
    }

    public void setRightSymbol(String rightSymbol) {
        this.rightSymbol = rightSymbol;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public LocalDateTime getPurchaseAt() {
        return purchaseAt;
    }

    public void setPurchaseAt(LocalDateTime purchaseAt) {
        this.purchaseAt = purchaseAt;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String getTable() {
        return "orders_rma_bill";
    }
}