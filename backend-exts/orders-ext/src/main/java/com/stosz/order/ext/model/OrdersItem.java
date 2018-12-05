package com.stosz.order.ext.model;

import com.stosz.order.ext.dto.OrderSkuAttribute;
import com.stosz.order.ext.enums.ItemStatusEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单项信息实体类
 */
public class OrdersItem  extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Long	id	=	new Long(0);
    /**订单id*/
    @DBColumn
    private	Long	ordersId	=	new Long(0);
    /**产品spu*/
    @DBColumn
    private	String	spu	=	"";
    /**产品sku*/
    @DBColumn
    private	String	sku	=	"";
    /**数量*/
    @DBColumn
    private	Integer	qty	=	new Integer(0);
    /**活动id*/
    @DBColumn
    private	Integer	activityId	=	new Integer(0);
    /**单价*/
    @DBColumn
    private	java.math.BigDecimal	singleAmount	=	new java.math.BigDecimal(0);
    /**总价*/
    @DBColumn
    private	java.math.BigDecimal	totalAmount	=	new java.math.BigDecimal(0);
    /**产品id*/
    @DBColumn
    private	Long	productId	=	new Long(0);
    /**产品标题*/
    @DBColumn
    private	String	productTitle	=	"";
    /**内部名称*/
    @DBColumn
    private	String	innerTitle	=	"";
    /**
     * 属性值数组，用于展示
     */
    @DBColumn
    private String attrTitleArray = "";
    /**外文名称*/
    @DBColumn
    private	String	foreignTitle	=	"";
    /**带外文的总价*/
    @DBColumn
    private	String	foreignTotalAmount	=	"";
    /**属性名的json数组*/
    @DBColumn
    private	String	attrNameArray	=	"";
    /**属性值的json数组*/
    @DBColumn
    private	String	attrValueArray	=	"";
    /**订单项状态*/
    @DBColumn
    private	Integer	itemStatusEnum	= ItemStatusEnum.unAudit.ordinal();
    /**产品图片地址*/
    @DBColumn
    private	String	productImageUrl	=	"";
    /**创建时间*/
    @DBColumn
    private	java.time.LocalDateTime	createAt	=	java.time.LocalDateTime.now();
    /**更新时间*/
    @DBColumn
    private	java.time.LocalDateTime	updateAt	=	java.time.LocalDateTime.now();
    /**创建者id*/
    @DBColumn
    private	Integer	creatorId	=	new Integer(0);
    /**创建人*/
    @DBColumn
    private	String	creator	=	"系统";
    /**skuid*/
    @DBColumn
    private Integer skuId;

    @DBColumn
    private Integer warehouseId = 0;
    @DBColumn
    private Integer occupyQty = 0;
    @DBColumn
    private LocalDateTime occupyStockTime = LocalDateTime.now();

    /**
     * spu对应的数据集合
     */
    private List<OrderSkuAttribute> attrList = new ArrayList<OrderSkuAttribute>();

    public List<OrderSkuAttribute> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<OrderSkuAttribute> attrList) {
        this.attrList = attrList;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public String getInnerTitle() {
        return innerTitle;
    }

    public void setInnerTitle(String innerTitle) {
        this.innerTitle = innerTitle;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public OrdersItem(){}

    public void setId(Integer	id){
        this.id = Long.valueOf(id);
    }

    public Integer getId(){
        return id.intValue();
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public void setSpu(String	spu){
        this.spu = spu;
    }

    public String getSpu(){
        return spu;
    }

    public void setSku(String	sku){
        this.sku = sku;
    }

    public String getSku(){
        return sku;
    }

    public void setQty(Integer	qty){
        this.qty = qty;
    }

    public Integer getQty(){
        return qty;
    }

    public void setActivityId(Integer	activityId){
        this.activityId = activityId;
    }

    public Integer getActivityId(){
        return activityId;
    }

    public void setSingleAmount(java.math.BigDecimal	singleAmount){
        this.singleAmount = singleAmount;
    }

    public java.math.BigDecimal getSingleAmount(){
        return singleAmount;
    }

    public void setTotalAmount(java.math.BigDecimal	totalAmount){
        this.totalAmount = totalAmount;
    }

    public java.math.BigDecimal getTotalAmount(){
        return totalAmount;
    }

    public void setProductId(Long	productId){
        this.productId = productId;
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductTitle(String	productTitle){
        this.productTitle = productTitle;
    }

    public String getProductTitle(){
        return productTitle;
    }

    public void setForeignTitle(String	foreignTitle){
        this.foreignTitle = foreignTitle;
    }

    public String getForeignTitle(){
        return foreignTitle;
    }

    public void setForeignTotalAmount(String	foreignTotalAmount){
        this.foreignTotalAmount = foreignTotalAmount;
    }

    public String getForeignTotalAmount(){
        return foreignTotalAmount;
    }

    public void setAttrNameArray(String	attrNameArray){
        this.attrNameArray = attrNameArray;
    }

    public String getAttrNameArray(){
        return attrNameArray;
    }

    public void setAttrValueArray(String	attrValueArray){
        this.attrValueArray = attrValueArray;
    }

    public String getAttrValueArray(){
        return attrValueArray;
    }

    public void setItemStatusEnum(Integer	itemStatusEnum){
        this.itemStatusEnum = itemStatusEnum;
    }

    public Integer getItemStatusEnum(){
        return itemStatusEnum;
    }

    public void setProductImageUrl(String	productImageUrl){
        this.productImageUrl = productImageUrl;
    }

    public String getProductImageUrl(){
        return productImageUrl;
    }

    public void setCreateAt(java.time.LocalDateTime	createAt){
        this.createAt = createAt;
    }

    public java.time.LocalDateTime getCreateAt(){
        return createAt;
    }

    public void setUpdateAt(java.time.LocalDateTime	updateAt){
        this.updateAt = updateAt;
    }

    public java.time.LocalDateTime getUpdateAt(){
        return updateAt;
    }

    public void setCreatorId(Integer	creatorId){
        this.creatorId = creatorId;
    }

    public Integer getCreatorId(){
        return creatorId;
    }

    public void setCreator(String	creator){
        this.creator = creator;
    }

    public String getCreator(){
        return creator;
    }

    public String getAttrTitleArray() {
        return attrTitleArray;
    }

    public void setAttrTitleArray(String attrTitleArray) {
        this.attrTitleArray = attrTitleArray;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getOccupyQty() {
        return occupyQty;
    }

    public void setOccupyQty(Integer occupyQty) {
        this.occupyQty = occupyQty;
    }

    public LocalDateTime getOccupyStockTime() {
        return occupyStockTime;
    }

    public void setOccupyStockTime(LocalDateTime occupyStockTime) {
        this.occupyStockTime = occupyStockTime;
    }

    @Override
    public String getTable() {
        return "orders_item";
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (!(o instanceof OrdersItem)) return false;
        final OrdersItem other = (OrdersItem)o;
        if(this.sku.equals(other.getSku())&& this.qty==other.getQty())
            return true;
        else
            return false;

    }
}

//package com.stosz.order.ext.model;
//
//import com.stosz.plat.model.AbstractParamEntity;
//import com.stosz.plat.model.ITable;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
///**
// * @author
// */
//public class OrderItems  extends AbstractParamEntity implements ITable, Serializable {
//    /**
//     * 主键，自增
//     */
//    private Long id;
//
//    /**
//     * 订单id
//     */
//    private Long orderId;
//
//    /**
//     * 产品spu
//     */
//    private String spu;
//
//    /**
//     * 产品sku
//     */
//    private String sku;
//
//    /**
//     * 数量
//     */
//    private Integer qty;
//
//    /**
//     * 活动id
//     */
//    private Integer activityId;
//
//    /**
//     * 售价
//     */
//    private BigDecimal soldAmount;
//
//    /**
//     * 总价
//     */
//    private BigDecimal totalAmount;
//
//    /**
//     * 创建时间
//     */
//    private LocalDateTime createAt;
//
//    /**
//     * 更新时间
//     */
//    private LocalDateTime updateAt;
//
//    /**
//     * 产品id
//     */
//    private Long productId;
//
//    /**
//     * 产品标题
//     */
//    private String productTitle;
//
//    /**
//     * 外文名称
//     */
//    private String foreignTitle;
//
//    /**
//     * 带外文的总价
//     */
//    private String foreignTotalAmount;
//
//    /**
//     * 属性名的json数组
//     */
//    private String attrNameArray;
//
//    /**
//     * 属性值的json数组
//     */
//    private String attrValueArray;
//
//    /**
//     * 订单项商品的图片
//     */
//    private String mainImageUrl;
//
//    private static final long serialVersionUID = 1L;
//    private Integer status;
//
//    @Override
//    public Integer getId() {
//        return this.id.intValue();
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.id = (long)id;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getSpu() {
//        return spu;
//    }
//
//    public void setSpu(String spu) {
//        this.spu = spu;
//    }
//
//    public String getSku() {
//        return sku;
//    }
//
//    public void setSku(String sku) {
//        this.sku = sku;
//    }
//
//    public Integer getQty() {
//        return qty;
//    }
//
//    public void setQty(Integer qty) {
//        this.qty = qty;
//    }
//
//    public Integer getActivityId() {
//        return activityId;
//    }
//
//    public void setActivityId(Integer activityId) {
//        this.activityId = activityId;
//    }
//
//    public BigDecimal getSoldAmount() {
//        return soldAmount;
//    }
//
//    public void setSoldAmount(BigDecimal soldAmount) {
//        this.soldAmount = soldAmount;
//    }
//
//    public BigDecimal getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(BigDecimal totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public LocalDateTime getCreateAt() {
//        return createAt;
//    }
//
//    public void setCreateAt(LocalDateTime createAt) {
//        this.createAt = createAt;
//    }
//
//    public LocalDateTime getUpdateAt() {
//        return updateAt;
//    }
//
//    public void setUpdateAt(LocalDateTime updateAt) {
//        this.updateAt = updateAt;
//    }
//
//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public String getProductTitle() {
//        return productTitle;
//    }
//
//    public void setProductTitle(String productTitle) {
//        this.productTitle = productTitle;
//    }
//
//    public String getForeignTitle() {
//        return foreignTitle;
//    }
//
//    public void setForeignTitle(String foreignTitle) {
//        this.foreignTitle = foreignTitle;
//    }
//
//    public String getForeignTotalAmount() {
//        return foreignTotalAmount;
//    }
//
//    public void setForeignTotalAmount(String foreignTotalAmount) {
//        this.foreignTotalAmount = foreignTotalAmount;
//    }
//
//    public String getAttrNameArray() {
//        return attrNameArray;
//    }
//
//    public void setAttrNameArray(String attrNameArray) {
//        this.attrNameArray = attrNameArray;
//    }
//
//    public String getAttrValueArray() {
//        return attrValueArray;
//    }
//
//    public void setAttrValueArray(String attrValueArray) {
//        this.attrValueArray = attrValueArray;
//    }
//
//    public String getMainImageUrl() {
//        return mainImageUrl;
//    }
//
//    public void setMainImageUrl(String mainImageUrl) {
//        this.mainImageUrl = mainImageUrl;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getSimpleName());
//        sb.append(" [");
//        sb.append("Hash = ").append(hashCode());
//        sb.append(", id=").append(id);
//        sb.append(", orderId=").append(orderId);
//        sb.append(", spu=").append(spu);
//        sb.append(", sku=").append(sku);
//        sb.append(", qty=").append(qty);
//        sb.append(", activityId=").append(activityId);
//        sb.append(", soldAmount=").append(soldAmount);
//        sb.append(", totalAmount=").append(totalAmount);
//        sb.append(", createAt=").append(createAt);
//        sb.append(", updateAt=").append(updateAt);
//        sb.append(", productId=").append(productId);
//        sb.append(", productTitle=").append(productTitle);
//        sb.append(", foreignTitle=").append(foreignTitle);
//        sb.append(", foreignTotalAmount=").append(foreignTotalAmount);
//        sb.append(", attrNameArray=").append(attrNameArray);
//        sb.append(", attrValueArray=").append(attrValueArray);
//        sb.append(", serialVersionUID=").append(serialVersionUID);
//        sb.append("]");
//        return sb.toString();
//    }
//
//    @Override
//    public String getTable() {
//        return "orders_item";
//    }
//
//    public Integer getStatus() {
//        return status;
//    }
//}