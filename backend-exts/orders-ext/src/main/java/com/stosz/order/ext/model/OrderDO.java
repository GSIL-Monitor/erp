package com.stosz.order.ext.model;

import com.google.common.collect.Lists;
import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.PayMethodEnum;
import com.stosz.order.ext.enums.PayStateEnum;
import com.stosz.order.ext.enums.TelCodeState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 订单列表DO
 */
public class OrderDO {

    /**
     * 订单编号（ERP）
     */
    private Integer id = 0;


    /**
     * 订单编号（建站编号，前端显示此项）
     */
    private String merchantOrderNo = "";

    /**
     * 下单IP所有在的地区
     */
    private String area = "";

    /**
     * 下单区域名称
     */
    private String zoneName ="";

    /**
     * 区域ID
     */
    private Integer zoneId;
    /**
     *  国家
     */
    private String country;
    /**
     * ip地址
     */
    private String ip ="";

    /**
     * ip名称
     */
    private String ipName ="";

    /**
     * ip购买数量
     */
    private int ipOrderQty = 0;

    /**
     * 域名
     */
    private String domain ="";

    /**
     * 订单状态
     */
    private OrderStateEnum orderStatusEnum ;


    /**
     * 支付状态
     */
    private PayStateEnum payState = PayStateEnum.unPaid;

    /**
     * 支付方法
     */
    private PayMethodEnum paymentMethod = PayMethodEnum.cod;

    /**
     * firstName
     */
    private String firstName = "";

    /**
     * lastName
     */
    private String lastName="";

    /**
     * 广告员名称
     */
    private String seoUserName="";


    /**
     * 广告元部门名称
     */
    private String departmentUserInfo = "";

    /**
     * 手机号
     */
    private String telphone="";


    /**
     * 邮箱
     */
    private String email="";

    /**
     * 系统发送的验证码（原）
     */
    private String getCode="";

    /**
     * 输入验证码（验）
     */
    private String inputCode="";

    /**
     * 验证码状态
     */
    private TelCodeState codeType = TelCodeState.succ;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount = new BigDecimal("0");
    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 订单金额，包括了单位符号(用于展示)
     */
    private String amountShow="";

    /**
     * 数量
     */
    private int goodsQty=0;

    /**
     * 订单标题（暂不用）
     */
    private String orderTitle="";

    /**
     * 地址
     */
    private String address="";

    /**
     * 省
     */
    private String provice="";
    /**
     * 城市
     */
    private String city="";
    /**
     * 邮编
     */
    private String zipcode="";

    /**
     * 客户留言
     */
    private String customerMessage="";

    /**
     * 备注
     */
    private String memo="";

    /**
     * 运单号
     */
    private String trackingNo="";

    /**
     * 仓库编号
     */
    private Integer warehouseId =0;
    /**
     * 客户ID
     */
    private  Integer customerId;

    /**
     * 物流公司编号
     */
    private String logisticsId="";

    /**
     * 物流状态(展示)
     */
    private String trackingStatusShow="";

    /**
     * 创建时间（展示时间）
     */
    private LocalDateTime createAt;

    /**
     * spu列表 { title：spu标题，sku:{attr: sku属性, qty: sku数量}}
     */
    private List<OrderItemSpuDTO> spuList = Lists.newLinkedList();

    /**
     * 黑名单字段，没有则为空
     */
    private String blackFields="";

    /**
     * 重复信息，没有则为空
     */
    private String repeatInfo="";

    /**
     * 快递公司名称
     */
    private String logisticsName = "";

    /**
     * 仓库名称
     */
    private String warehouseName = "";
    /**
     * 下单时间
     */
    private Date purchaserAt;
    /**
     * 是否有退货记录
     */
    private String hasRmaRecord;

    public String getHasRmaRecord() {
        return hasRmaRecord;
    }

    public void setHasRmaRecord(String hasRmaRecord) {
        this.hasRmaRecord = hasRmaRecord;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getIpOrderQty() {
        return ipOrderQty;
    }

    public void setIpOrderQty(int ipOrderQty) {
        this.ipOrderQty = ipOrderQty;
    }

    public String getBlackFields() {
        return blackFields;
    }

    public void setBlackFields(String blackFields) {
        this.blackFields = blackFields;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getCountry() {
        return country;
    }

    public Date getPurchaserAt() {
        return purchaserAt;
    }

    public void setPurchaserAt(Date purchaserAt) {
        this.purchaserAt = purchaserAt;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public OrderStateEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStateEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public void setPayState(PayStateEnum payState) {
        this.payState = payState;
    }

    public PayMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PayMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getGetCode() {
        return getCode;
    }

    public void setGetCode(String getCode) {
        this.getCode = getCode;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(int goodsQty) {
        this.goodsQty = goodsQty;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public List<OrderItemSpuDTO> getSpuList() {
        return spuList;
    }

    public void setSpuList(List<OrderItemSpuDTO> spuList) {
        this.spuList = spuList;
    }

    public String getRepeatInfo() {
        return repeatInfo;
    }

    public void setRepeatInfo(String repeatInfo) {
        this.repeatInfo = repeatInfo;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSeoUserName() {
        return seoUserName;
    }

    public void setSeoUserName(String seoUserName) {
        this.seoUserName = seoUserName;
    }

    public PayStateEnum getPayState() {
        return payState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmountShow() {
        return amountShow;
    }

    public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
    }

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }


    public String getTrackingStatusShow() {
        return trackingStatusShow;
    }

    public void setTrackingStatusShow(String trackingStatusShow) {
        this.trackingStatusShow = trackingStatusShow;
    }

    public TelCodeState getCodeType() {
        return codeType;
    }

    public void setCodeType(TelCodeState codeType) {
        this.codeType = codeType;
    }

    public String getDepartmentUserInfo() {
        return departmentUserInfo;
    }

    public void setDepartmentUserInfo(String departmentUserInfo) {
        this.departmentUserInfo = departmentUserInfo;
    }
}
