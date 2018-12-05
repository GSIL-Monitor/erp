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
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单表实体类
 */
public class Orders extends AbstractParamEntity implements ITable, Serializable, IFsmInstance {

    /**
     * 自增主键
     */
    @DBColumn
    private Long id = new Long(0);
    /**
     * 商户编号
     */
    @DBColumn
    private MerchantEnum merchantEnum = MerchantEnum.single;
    /**
     * 建站传过来的商户订单编号
     */
    @DBColumn
    private String merchantOrderNo = "";
    /**
     * 正常订单或者内部订单
     */
    @DBColumn
    private Integer orderTypeEnum = new Integer(0);
    /**
     * 投放区域id
     */
    @DBColumn
    private String zoneCode = "";
    /**
     * 投放区域id
     */
    @DBColumn
    private Integer zoneId = 0;
    /**
     * 投放区域名称
     */
    @DBColumn
    private String zoneName = "";
    /**
     * 币种
     */
    @DBColumn
    private String currencyCode = "";
    /**
     * 订单导入时，获取到当前订单币种与人民币的汇率n            来自产品中心的汇率表
     */
    @DBColumn
    private java.math.BigDecimal orderExchangeRate = new java.math.BigDecimal(0);
    /**
     * 订单金额 外币金额
     */
    @DBColumn
    private java.math.BigDecimal orderAmount = new java.math.BigDecimal(0);
    /**
     * 显示金额（包含货币代码）
     */
    @DBColumn
    private String amountShow = "";
    /**
     * 客服确认金额 默认=订单金额 外币金额
     */
    @DBColumn
    private java.math.BigDecimal confirmAmount = getOrderAmount();
    /**
     * 商品数量
     */
    @DBColumn
    private Integer goodsQty = new Integer(0);
    /**
     * 下单ip地址
     */
    @DBColumn
    private String ip = "";

    /**
     * 下单ip地址名称
     */
    @DBColumn
    private String ipName = "";
    /**
     * 客户留言
     */
    @DBColumn
    private String customerMessage = "";
    /**
     * 备注
     */
    @DBColumn
    private String memo = "";
    /**
     * 下单时间
     */
    @DBColumn
    private java.time.LocalDateTime purchaserAt = java.time.LocalDateTime.now();
    /**
     * 用户接受到的验证码
     */
    @DBColumn
    private String getCode = "";
    /**
     * 用户输入的验证码
     */
    @DBColumn
    private String inputCode = "";
    /**
     * 订单状态
     */
    @DBColumn
    private OrderStateEnum orderStatusEnum = OrderStateEnum.waitAudit;

    /**
     * 优化师部门id
     */
    @DBColumn
    private Integer seoDepartmentId = new Integer(0);
    /**
     * 事业部部门id
     */
    @DBColumn
    private Integer buDepartmentId = new Integer(0);
    /**
     * 黑名单字段
     */
    @DBColumn
    private String blackFields = "";
    /**
     * 重复信息
     */
    @DBColumn
    private String repeatInfo = "";
    /**
     * 验证码的类型
     */
    @DBColumn
    private Integer codeType = TelCodeState.unValidate.ordinal();
    /**
     * ip当天订单数量
     */
    @DBColumn
    private Integer ipOrderQty = new Integer(0);
    /**
     * 优化师的id
     */
    @DBColumn
    private Integer seoUserId = new Integer(0);
    /**
     * 优化师姓名
     */
    @DBColumn
    private String seoUserName = "";
    /**
     * 创建时间
     */
    @DBColumn
    private java.time.LocalDateTime createAt = java.time.LocalDateTime.now();
    /**
     * 更新时间
     */
    @DBColumn
    private java.time.LocalDateTime updateAt = java.time.LocalDateTime.now();

    /**
     * 状态机时间
     */
    @DBColumn
    private java.time.LocalDateTime stateTime = java.time.LocalDateTime.now();
    /**
     * 创建者id
     */
    @DBColumn
    private Integer creatorId = new Integer(0);
    /**
     * 创建人
     */
    @DBColumn
    private String creator = "系统";

    /**
     * 支付方式。
     */
    @DBColumn
    private Integer paymentMethod = PayMethodEnum.cod.ordinal();

    /**
     * 支付状态 0：未支付，1：已支付
     */
    @DBColumn
    private Integer payState = PayStateEnum.unPaid.ordinal();

    /**
     * 老erp的订单id
     */
    @DBColumn
    private Long oldId = new Long(0);
    /**
     * 仓库ID
     */
    @DBColumn
    private Integer warehouseId = 0;
    @DBColumn
    private String warehouseName = "";//发货仓名称
    @DBColumn
    private String trackingNo = "";//运单号
    @DBColumn
    private String logisticsName = "";//快递公司名称
    @DBColumn
    private Integer logisticsId = 0;//物流公司id
    @DBColumn
    private String trackingMemo = "";//运单备注信息
    // TODO: 2017/12/12
    @DBColumn
    private Integer trackingStatus = -1;//运单状态
    @DBColumn
    private String warehouseMemo = "";//仓库备注信息
    // TODO: 2017/12/12  
    @DBColumn
    private WarehouseTypeEnum warehouseTypeEnum = WarehouseTypeEnum.normal;//仓库种类

    @DBColumn
    private String warehouseCode = "";//仓库编码


    @DBColumn
    private String departmentUserInfo = "";//部门信息

    @DBColumn
    private Integer invalidReasonType = null;//审核不通过原因
    @DBColumn
    private LocalDateTime auditTime = java.time.LocalDateTime.now();//审核时间
    @DBColumn
    private LocalDateTime deliveryTime = java.time.LocalDateTime.now();//发货时间
    /**
     * 域名
     */
    @DBColumn
    private String domain = "";

    @DBColumn
    private String comboId = "0";//套餐编号

    @DBColumn
    private String comboName = "";//套餐名称

    @DBColumn
    private String trackingStatusShow = "";//物流状态显示

    @DBColumn
    private LocalDateTime assignProductTime;

    @DBColumn
    private OrderCancelReasonEnum cancelReasonEnum;

    public OrderCancelReasonEnum getCancelReasonEnum() {
        return cancelReasonEnum;
    }

    public void setCancelReasonEnum(OrderCancelReasonEnum cancelReasonEnum) {
        this.cancelReasonEnum = cancelReasonEnum;
    }

    public LocalDateTime getAssignProductTime() {
        return assignProductTime;
    }

    public void setAssignProductTime(LocalDateTime assignProductTime) {
        this.assignProductTime = assignProductTime;
    }

    public String getDepartmentUserInfo() {
        return departmentUserInfo;
    }

    public void setDepartmentUserInfo(String departmentUserInfo) {
        this.departmentUserInfo = departmentUserInfo;
    }


    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getWarehouseName() {
        return warehouseName;
    }


    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }


    public String getTrackingNo() {
        return trackingNo;
    }


    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getLogisticsName() {
        return logisticsName;
    }


    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }


    public Integer getLogisticsId() {
        return logisticsId;
    }


    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }


    public String getTrackingMemo() {
        return trackingMemo;
    }


    public void setTrackingMemo(String trackingMemo) {
        this.trackingMemo = trackingMemo;
    }


    public Integer getTrackingStatus() {
        return trackingStatus;
    }


    public void setTrackingStatus(Integer trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public String getTrackingStatusShow() {
        return trackingStatusShow;
    }

    public void setTrackingStatusShow(String trackingStatusShow) {
        this.trackingStatusShow = trackingStatusShow;
    }

    public String getWarehouseMemo() {
        return warehouseMemo;
    }


    public void setWarehouseMemo(String warehouseMemo) {
        this.warehouseMemo = warehouseMemo;
    }


    public WarehouseTypeEnum getWarehouseTypeEnum() {
        return warehouseTypeEnum;
    }

    public void setWarehouseTypeEnum(WarehouseTypeEnum warehouseTypeEnum) {
        this.warehouseTypeEnum = warehouseTypeEnum;
    }

    public String getAmountShow() {
        return amountShow;
    }

    public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
    }



    public Integer getWarehouseId() {
        return warehouseId;
    }


    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * 订单标题
     */
    @DBColumn
    private String orderTitle = "";

    /**
     * 订单内部标题
     */
    @DBColumn
    private String orderInnerTitle = "";

    private BigDecimal weight;// 总量  wms出库，订单状态变为已发货的时候，需要通知TMS进行物流轨迹抓取

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Orders() {
    }

    public void setId(Integer id) {
        this.id = Long.valueOf(id);
    }

    public Integer getId() {
        return id.intValue();
    }


    /**
     * 订单来源（用于订单摘要）
     */
    public String orderFrom;

    /**
     * parentId
     */
    @Override
    public Integer getParentId() {
        return getId();
    }

    /**
     * state
     */
    @Override
    public String getState() {
        if(null == orderStatusEnum) return "";
        return orderStatusEnum.name();
    }

    @Override
    public void setState(String state) {
        orderStatusEnum = OrderStateEnum.fromName(state);
    }

    /**
     * stateTime
     */
    @Override
    public LocalDateTime getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setOrderTypeEnum(Integer orderTypeEnum) {
        this.orderTypeEnum = orderTypeEnum;
    }

    public Integer getOrderTypeEnum() {
        return orderTypeEnum;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setOrderExchangeRate(java.math.BigDecimal orderExchangeRate) {
        this.orderExchangeRate = orderExchangeRate;
    }

    public java.math.BigDecimal getOrderExchangeRate() {
        return orderExchangeRate;
    }

    public void setOrderAmount(java.math.BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public java.math.BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setConfirmAmount(java.math.BigDecimal confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public java.math.BigDecimal getConfirmAmount() {
        return confirmAmount;
    }

    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
    }

    public Integer getGoodsQty() {
        return goodsQty;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setPurchaserAt(java.time.LocalDateTime purchaserAt) {
        this.purchaserAt = purchaserAt;
    }

    public java.time.LocalDateTime getPurchaserAt() {
        return purchaserAt;
    }

    public void setGetCode(String getCode) {
        this.getCode = getCode;
    }

    public String getGetCode() {
        return getCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setOrderStatusEnum(OrderStateEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public OrderStateEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public void setSeoDepartmentId(Integer seoDepartmentId) {
        this.seoDepartmentId = seoDepartmentId;
    }

    public Integer getSeoDepartmentId() {
        return seoDepartmentId;
    }

    public void setBuDepartmentId(Integer buDepartmentId) {
        this.buDepartmentId = buDepartmentId;
    }

    public Integer getBuDepartmentId() {
        return buDepartmentId;
    }

    public void setBlackFields(String blackFields) {
        this.blackFields = blackFields;
    }

    public String getBlackFields() {
        return blackFields;
    }

    public void setRepeatInfo(String repeatInfo) {
        this.repeatInfo = repeatInfo;
    }

    public String getRepeatInfo() {
        return repeatInfo;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setIpOrderQty(Integer ipOrderQty) {
        this.ipOrderQty = ipOrderQty;
    }

    public Integer getIpOrderQty() {
        return ipOrderQty;
    }

    public void setSeoUserId(Integer seoUserId) {
        this.seoUserId = seoUserId;
    }

    public Integer getSeoUserId() {
        return seoUserId;
    }

    public void setCreateAt(java.time.LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public java.time.LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setUpdateAt(java.time.LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public java.time.LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }

    public String getSeoUserName() {
        return seoUserName;
    }

    public void setSeoUserName(String seoUserName) {
        this.seoUserName = seoUserName;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderInnerTitle() {
        return orderInnerTitle;
    }

    public void setOrderInnerTitle(String orderInnerTitle) {
        this.orderInnerTitle = orderInnerTitle;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    @Override
    public String getTable() {
        return "orders";
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getInvalidReasonType() {
        return invalidReasonType;
    }

    public void setInvalidReasonType(Integer invalidReasonType) {
        this.invalidReasonType = invalidReasonType;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }


    public MerchantEnum getMerchantEnum() {
        return merchantEnum;
    }

    public void setMerchantEnum(MerchantEnum merchantEnum) {
        this.merchantEnum = merchantEnum;
    }

    public String getComboId() {
        return comboId;
    }

    public void setComboId(String comboId) {
        this.comboId = comboId;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }


    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
