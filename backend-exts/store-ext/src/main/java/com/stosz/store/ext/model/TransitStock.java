package com.stosz.store.ext.model;

import com.stosz.fsm.IFsmInstance;
import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;
import com.stosz.store.ext.enums.TransitStateEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄仓库表
 * @Created Time:2017/11/22 18:16
 * @Update Time:
 */
public class TransitStock extends AbstractParamEntity implements ITable, IFsmInstance {

    @DBColumn
    private Integer id;
    /**
     * 部门id
     */
    @DBColumn
    private Integer deptId;

    /**
     * 部门名称
     */
    @DBColumn
    private String deptName;
    /**
     * 仓库id
     */
    @DBColumn
    private Integer wmsId;
    /**
     * 仓库名称
     */
    @DBColumn
    private String stockName;
    @DBColumn
    private String trackingNoOld;
    @DBColumn
    private String trackingNoNew;
    @DBColumn
    private Long orderIdOld;
    @DBColumn
    private Long orderIdNew;

    /**
     * 库位
     */
    @DBColumn
    private String storageLocal;
    /**
     * 创建(入库)时间
     */
    @DBColumn
    private LocalDateTime createAt;
    @DBColumn
    private String creator;
    @DBColumn
    private Integer creatorId;
    /**
     * 更新时间
     */
    @DBColumn
    private LocalDateTime updateAt;
    /**
     * 拣货导出时间
     */
    @DBColumn
    private LocalDateTime exportAt;
    /**
     * 拣货导出人
     */
    @DBColumn
    private String exporter;
    /**
     * 内部名
     */
    @DBColumn
    private String innerName;
    /**
     * sku
     */
    @DBColumn
    private String sku;
    /**
     * （新订单）下单日期
     */
    @DBColumn
    private LocalDateTime orderAt;

    /**
     * 状态
     */
    @DBColumn
    private String state;

    @DBColumn
    private LocalDateTime stateTime;


    /**
     * rma申请单号
     */
    @DBColumn
    private Integer rmaId;

    /**
     * 历史状态
     */
    @DBColumn
    private String historyState;

    //冗余不持久化的字段
    //查询结果增加的字段

    /**
     * 地区
     */
    private transient String zoneName;

    /**
     * 库存状态名称
     */
    private transient String transitStateName;

    /**
     * 物流公司名称(原订单的)
     */
    private transient String logisticsNameOld;

    /**
     * 产品信息（产品标题）
     */
    private transient String productTitle;

    /**
     * 外文名称
     */
    private transient String foreignTitle;

    /**
     * 入库方式
     */
    private transient String inStorageType;

    /**
     * 库龄
     */
    private transient Integer storageAge;

    /**
     * 物流公司名称(新)
     */
    private transient String logisticsNameNew;

    /**
     * 物流状态(新)
     */
    private transient String trackingStatus;

    /**
     * 物流id
     */
    private transient String logisticsId;

    /**
     * 发货时间
     */
    private transient LocalDateTime trackingTime;

    //拣货导出时所需字段
    /**
     * 客户姓名
     */
    private transient String customerName;
    /**
     * 电话
     */
    private transient String mobile;
    /**
     * 总价
     */
    private transient BigDecimal orderAmount;
    /**
     * 产品数量(商品数量)
     */
    private transient Integer goodQty;
    /**
     * 用户留言
     */
    private transient String customerMessage;
    /**
     * 地址
     */
    private transient String address;
    /**
     * 邮编
     */
    private transient String zipCode;
    /**
     * 省/洲
     */
    private transient String province;
    /**
     * 城市
     */
    private transient String city;
    /**
     * 区域
     */
    private transient String area;

    //导出
    /**
     * 导出状态
     */
    private transient String exportStatus;
    /**
     * 导出结果描述
     */
    private transient String exportMsg;

    //搜索条件
    /**
     * 入库日期开始时间
     */
    private transient LocalDateTime inStorageStartTime;

    /**
     * 入库日期结束时间
     */
    private transient LocalDateTime inStorageEndTime;

    /**
     * 下单日期开始时间
     */
    private transient LocalDateTime orderStartTime;

    /**
     * 下单日期结束时间
     */
    private transient LocalDateTime orderEndTime;

    /**
     * 拣货导出日期开始时间
     */
    private transient LocalDateTime pickingStartTime;

    /**
     * 拣货导出日期结束时间
     */
    private transient LocalDateTime pickingEndTime;

    /**
     * 原运单号批量
     */
    private String trackingNoOldBat;

    /**
     * @return
     */
    private transient List<OrderItemSpuDTO> spuList;

    public List<OrderItemSpuDTO> getSpuList() {

        return spuList;
    }

    public void setSpuList(List<OrderItemSpuDTO> spuList) {
        this.spuList = spuList;
    }

    public String getHistoryState() {
        return historyState;
    }

    public void setHistoryState(String historyState) {
        this.historyState = historyState;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Integer getRmaId() {
        return rmaId;
    }

    public void setRmaId(Integer rmaId) {
        this.rmaId = rmaId;
    }

    public String getTrackingNoOldBat() {
        return trackingNoOldBat;
    }

    public void setTrackingNoOldBat(String trackingNoOldBat) {
        this.trackingNoOldBat = trackingNoOldBat;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Integer getParentId() {
        return null;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public LocalDateTime getStateTime() {
        return stateTime;
    }

    @Override
    public void setStateTime(LocalDateTime stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getWmsId() {
        return wmsId;
    }

    public void setWmsId(Integer wmsId) {
        this.wmsId = wmsId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getTrackingNoOld() {
        return trackingNoOld;
    }

    public void setTrackingNoOld(String trackingNoOld) {
        this.trackingNoOld = trackingNoOld;
    }

    public String getTrackingNoNew() {
        return trackingNoNew;
    }

    public void setTrackingNoNew(String trackingNoNew) {
        this.trackingNoNew = trackingNoNew;
    }

    public Long getOrderIdOld() {
        return orderIdOld;
    }

    public void setOrderIdOld(Long orderIdOld) {
        this.orderIdOld = orderIdOld;
    }

    public Long getOrderIdNew() {
        return orderIdNew;
    }

    public void setOrderIdNew(Long orderIdNew) {
        this.orderIdNew = orderIdNew;
    }

    public String getStorageLocal() {
        return storageLocal;
    }

    public void setStorageLocal(String storageLocal) {
        this.storageLocal = storageLocal;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getExportAt() {
        return exportAt;
    }

    public void setExportAt(LocalDateTime exportAt) {
        this.exportAt = exportAt;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getTransitStateName() {
        return TransitStateEnum.fromName(state).display();
    }

    public void setTransitStateName(String transitStateName) {
        this.transitStateName = transitStateName;
    }

    public String getLogisticsNameOld() {
        return logisticsNameOld;
    }

    public void setLogisticsNameOld(String logisticsNameOld) {
        this.logisticsNameOld = logisticsNameOld;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getForeignTitle() {
        return foreignTitle;
    }

    public void setForeignTitle(String foreignTitle) {
        this.foreignTitle = foreignTitle;
    }

    public String getInStorageType() {
        return inStorageType;
    }

    public void setInStorageType(String inStorageType) {
        this.inStorageType = inStorageType;
    }

    public Integer getStorageAge() {
        return storageAge;
    }

    public void setStorageAge(Integer storageAge) {
        this.storageAge = storageAge;
    }

    public String getLogisticsNameNew() {
        return logisticsNameNew;
    }

    public void setLogisticsNameNew(String logisticsNameNew) {
        this.logisticsNameNew = logisticsNameNew;
    }

    public String getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(String trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public LocalDateTime getTrackingTime() {
        return trackingTime;
    }

    public void setTrackingTime(LocalDateTime trackingTime) {
        this.trackingTime = trackingTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getGoodQty() {
        return goodQty;
    }

    public void setGoodQty(Integer goodQty) {
        this.goodQty = goodQty;
    }

    public String getCustomerMessage() {
        return customerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        this.customerMessage = customerMessage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(String exportStatus) {
        this.exportStatus = exportStatus;
    }

    public String getExportMsg() {
        return exportMsg;
    }

    public void setExportMsg(String exportMsg) {
        this.exportMsg = exportMsg;
    }

    public LocalDateTime getInStorageStartTime() {
        return inStorageStartTime;
    }

    public void setInStorageStartTime(LocalDateTime inStorageStartTime) {
        this.inStorageStartTime = inStorageStartTime;
    }

    public LocalDateTime getInStorageEndTime() {
        return inStorageEndTime;
    }

    public void setInStorageEndTime(LocalDateTime inStorageEndTime) {
        this.inStorageEndTime = inStorageEndTime;
    }

    public LocalDateTime getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(LocalDateTime orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public LocalDateTime getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(LocalDateTime orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public LocalDateTime getPickingStartTime() {
        return pickingStartTime;
    }

    public void setPickingStartTime(LocalDateTime pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    public LocalDateTime getPickingEndTime() {
        return pickingEndTime;
    }

    public void setPickingEndTime(LocalDateTime pickingEndTime) {
        this.pickingEndTime = pickingEndTime;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String getTable() {
        return "transit_stock";
    }

}
