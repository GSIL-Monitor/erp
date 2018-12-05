package com.stosz.order.ext.model;

import com.google.common.base.Strings;
import com.stosz.order.ext.enums.BlackLevelEnum;
import com.stosz.order.ext.enums.BlackTypeEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单联系信息实体类
 */
public class OrdersLink extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Long	id	=	new Long(0);
    /**名字*/
    @DBColumn
    private	String	firstName	=	"";
    /**姓*/
    @DBColumn
    private	String	lastName	=	"";
    /**国家*/
    @DBColumn
    private	String	country	=	"";
    /**电话*/
    @DBColumn
    private	String	telphone	=	"";
    /**邮箱*/
    @DBColumn
    private	String	email	=	"";
    /**省/洲*/
    @DBColumn
    private	String	province	=	"";
    /**城市*/
    @DBColumn
    private	String	city	=	"";
    /**区域*/
    @DBColumn
    private	String	area	=	"";
    /**地址*/
    @DBColumn
    private	String	address	=	"";
    /**邮编*/
    @DBColumn
    private	String	zipcode	=	"";
    /**黑名单级别*/
    @DBColumn
    private	Integer	blackLevelEnum	= BlackLevelEnum.other.ordinal();
    /**黑名单类型*/
    @DBColumn
    private	Integer	blackTypeEnum	= BlackTypeEnum.other.ordinal();
    /**订单id*/
    @DBColumn
    private	Long	ordersId	=	new Long(0);
    /**客户id*/
    @DBColumn
    private	Long	customerId	=	new Long(0);
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
    /**对应的老erp的订单id*/
    @DBColumn
    private Long oldId = 0L;


    
    public OrdersLink(){}

    public void setId(Integer	id){
        this.id = Long.valueOf(id);
    }

    public Integer getId(){
        return id.intValue();
    }

    public void setFirstName(String	firstName){
        if(Strings.isNullOrEmpty(firstName))
            firstName="";
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String	lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setCountry(String	country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    public void setTelphone(String	telphone){
        this.telphone = telphone;
    }

    public String getTelphone(){
        return telphone;
    }

    public void setEmail(String	email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setProvince(String	province){
        this.province = province;
    }

    public String getProvince(){
        return province;
    }

    public void setCity(String	city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setArea(String	area){
        this.area = area;
    }

    public String getArea(){
        return area;
    }

    public void setAddress(String	address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setZipcode(String	zipcode){
        this.zipcode = zipcode;
    }

    public String getZipcode(){
        return zipcode;
    }

    public void setBlackLevelEnum(Integer	blackLevelEnum){
        this.blackLevelEnum = blackLevelEnum;
    }

    public Integer getBlackLevelEnum(){
        return blackLevelEnum;
    }

    public void setBlackTypeEnum(Integer	blackTypeEnum){
        this.blackTypeEnum = blackTypeEnum;
    }

    public Integer getBlackTypeEnum(){
        return blackTypeEnum;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }

    public void setCustomerId(Long	customerId){
        this.customerId = customerId;
    }

    public Long getCustomerId(){
        return customerId;
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

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }


    @Override
    public String getTable() {
        return "orders_link";
    }

}

//package com.stosz.order.ext.model;
//
//import com.stosz.plat.model.AbstractParamEntity;
//import com.stosz.plat.model.ITable;
//
//import java.io.Serializable;
//
///**
// * @author
// */
//public class OrdersLink extends AbstractParamEntity implements ITable, Serializable {
//
//
//    private Long id;
//
//    /**
//     * 名字
//     */
//    private String firstName;
//
//    /**
//     * 姓
//     */
//    private String lastName;
//
//    /**
//     * 国家
//     */
//    private String country;
//
//    /**
//     * 电话
//     */
//    private String telphone;
//
//    /**
//     * 邮箱
//     */
//    private String email;
//
//    /**
//     * 省/洲
//     */
//    private String province;
//
//    /**
//     * 城市
//     */
//    private String city;
//
//    /**
//     * 区域
//     */
//    private String area;
//
//    /**
//     * 地址
//     */
//    private String address;
//
//    /**
//     * 邮编
//     */
//    private String zipcode;
//
//    /**
//     * 黑名单级别
//     */
//    private Integer blackLevelEnum;
//
//    /**
//     * 黑名单类型
//     */
//    private Integer blackTypeEnum;
//
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public Integer getId() {
//        return id.intValue();
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.id = (long)id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getTelphone() {
//        return telphone;
//    }
//
//    public void setTelphone(String telphone) {
//        this.telphone = telphone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getArea() {
//        return area;
//    }
//
//    public void setArea(String area) {
//        this.area = area;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getZipcode() {
//        return zipcode;
//    }
//
//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
//
//    public Integer getBlackLevelEnum() {
//        return blackLevelEnum;
//    }
//
//    public void setBlackLevelEnum(Integer blackLevelEnum) {
//        this.blackLevelEnum = blackLevelEnum;
//    }
//
//    public Integer getBlackTypeEnum() {
//        return blackTypeEnum;
//    }
//
//    public void setBlackTypeEnum(Integer blackTypeEnum) {
//        this.blackTypeEnum = blackTypeEnum;
//    }
//
//    @Override
//    public String getTable() {
//        return "customers";
//    }
//}