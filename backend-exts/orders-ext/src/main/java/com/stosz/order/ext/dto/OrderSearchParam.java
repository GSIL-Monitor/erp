package com.stosz.order.ext.dto;

import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.enums.PayMethodEnum;
import com.stosz.order.ext.enums.TelCodeState;
import com.stosz.plat.enums.TimeRegionEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangqian
 * 订单列表的搜索条件
 */
public class OrderSearchParam {

    // Begin.....................通过预处理 keyWord 生成字段
    //模糊
    private String tel;
    //精确
    private String email;
    //精确
    private String ip;
    //模糊
    private String address;
    //精确
    private String domain;

    //被限制的地区，如果为空则不限制地区，不为空则则只能看到指定的
    private List<Integer> zoneIds;
    //权限类型(2：个人，1：部门，0：全公司) 0时仅仅能看到userId，1时能看到departmentIds部门，2不会有部门过滤
    private Integer authorityType;
    //部门权限
    private List<Integer> departmentIds;

    //当前用户部门
    private Integer deptId;

    //当前用户编号
    private Integer userId;

    //拥有权限的部门编号
    private List<Integer> authorityDepts;


    //搜索的部门编号
    private List<Integer> searchDepts;



    /**
     * 隐藏域，运单号批量查询
     */
    private String trackingNoBatch;

    /**
     * 隐藏域，手机批量查询
     */
    private String telBatch;

    /**
     * 隐藏域，商户流水号批量查询
     */
    private String merchantOrderNoBatch;

    /**
     * 隐藏域，订单号批量查询
     */
    private String orderIdBatch;


    /**
     * 默认查询开始时间（showTime是时间控件，timeRegion是下拉框的时间选择（三个月，半年，一年，所有））
     */
    private LocalDateTime timeRegionBegin;


    /**
     * 默认查询结束时间
     */
    private LocalDateTime timeRegionEnd;

    //end.......................................




    /**
     * 关键词(电话，邮箱，域名，IP，送货地址)
     */
    private String keyWord;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 建站订单号
     */
    private Long merchantOrderNo;

    /**
     * 运单号
     */
    private String trackingNo;

    /**
     * 手机验证码类型
     */
    private TelCodeState codeType;

    /**
     * 支付方式
     */
    private PayMethodEnum payMethod;

    /**
     * 发货仓编号
     */
    private Integer warehouseId;


    /**
     *  物流公司编号
     */
    private Integer logisticId;

    /**
     * 订单状态
     */
    private OrderStateEnum orderState;

    /**
     * 时间区域(2017-12-12 新增)
     */
    private TimeRegionEnum timeRegion;

    /**
     * 地区编号
     */
    private Integer zoneId;


    /**
     * 地区编码
     */
    private String zoneCode;

//    /**
//     * 部门编号（废弃）
//     */
//    private Integer buDepartmentId;


    /**
     * 部门编号
     */
    private Integer departmentId;

    /**
     * 产品名
     */
    private String title;

    /**
     * sku
     */
    private String sku;

    /**
     * spu
     */
    private String spu;

    /**
     * 客户姓名
     */
    private String customerName;

    /**
     * 展示起始时间
     */
    private LocalDateTime minShowTime;

    /**
     * 展示结束时间
     */
    private LocalDateTime maxShowTime;

    /**
     * 起始位置
     */
    private Integer start = 0;

    /**
     * 显示条数
     */
    private Integer limit = 20;


    /**
     * 排序(升序asc, 降序desc)
     */
    private String sortOrder;

    /**
     * 排序字段(暂无用，现在只有根据展示时间排序)
     */
    private String sortName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTrackingNoBatch() {
        return trackingNoBatch;
    }

    public void setTrackingNoBatch(String trackingNoBatch) {
        this.trackingNoBatch = trackingNoBatch;
    }

    public String getTelBatch() {
        return telBatch;
    }

    public Long getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(Long merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public void setTelBatch(String telBatch) {
        this.telBatch = telBatch;
    }

    public String getMerchantOrderNoBatch() {
        return merchantOrderNoBatch;
    }

    public void setMerchantOrderNoBatch(String merchantOrderNoBatch) {
        this.merchantOrderNoBatch = merchantOrderNoBatch;
    }

    public Integer getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(Integer authorityType) {
        this.authorityType = authorityType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public TelCodeState getCodeType() {
        return codeType;
    }

    public void setCodeType(TelCodeState codeType) {
        this.codeType = codeType;
    }

    public PayMethodEnum getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethodEnum payMethod) {
        this.payMethod = payMethod;
    }

    public OrderStateEnum getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderStateEnum orderState) {
        this.orderState = orderState;
    }


    public LocalDateTime getTimeRegionBegin() {
        return timeRegionBegin;
    }

    public void setTimeRegionBegin(LocalDateTime timeRegionBegin) {
        this.timeRegionBegin = timeRegionBegin;
    }

    public LocalDateTime getTimeRegionEnd() {
        return timeRegionEnd;
    }

    public void setTimeRegionEnd(LocalDateTime timeRegionEnd) {
        this.timeRegionEnd = timeRegionEnd;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    //    public Integer getBuDepartmentId() {
//        return buDepartmentId;
//    }
//
//    public void setBuDepartmentId(Integer buDepartmentId) {
//        this.buDepartmentId = buDepartmentId;
//    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getLogisticId() {
        return logisticId;
    }

    public void setLogisticId(Integer logisticId) {
        this.logisticId = logisticId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSpu() {
        return spu;
    }

    public void setSpu(String spu) {
        this.spu = spu;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getMinShowTime() {
        return minShowTime;
    }

    public void setMinShowTime(LocalDateTime minShowTime) {
        this.minShowTime = minShowTime;
    }

    public LocalDateTime getMaxShowTime() {
        return maxShowTime;
    }

    public void setMaxShowTime(LocalDateTime maxShowTime) {
        this.maxShowTime = maxShowTime;
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

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
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

    public TimeRegionEnum getTimeRegion() {
        return timeRegion;
    }

    public void setTimeRegion(TimeRegionEnum timeRegion) {
        this.timeRegion = timeRegion;
    }

    public String getOrderIdBatch() {
        return orderIdBatch;
    }

    public void setOrderIdBatch(String orderIdBatch) {
        this.orderIdBatch = orderIdBatch;
    }


    public List<Integer> getAuthorityDepts() {
        return authorityDepts;
    }

    public void setAuthorityDepts(List<Integer> authorityDepts) {
        this.authorityDepts = authorityDepts;
    }

    public List<Integer> getSearchDepts() {
        return searchDepts;
    }

    public void setSearchDepts(List<Integer> searchDepts) {
        this.searchDepts = searchDepts;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
