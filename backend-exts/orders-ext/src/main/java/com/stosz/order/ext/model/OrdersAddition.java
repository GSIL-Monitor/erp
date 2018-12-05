package com.stosz.order.ext.model;

import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 订单附加信息实体类
 */
public class OrdersAddition  extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Long	id	=	new Long(0);
    /**浏览器*/
    @DBColumn
    private	String	browser	=	"";
    /**停留时间,拿不到设置为0单位毫秒；*/
    @DBColumn
    private	Integer	browseSecond	=	new Integer(0);
    /**设备*/
    @DBColumn
    private	String	equipment	=	"";
    /**设备agent*/
    @DBColumn
    private	String	userAgent	=	"";
    /**订单来源 fb tw等*/
    @DBColumn
    private	String	orderFrom	=	"";
    /**订单id*/
    @DBColumn
    private	Long	ordersId	=	new Long(0);
    /**ip地址*/
    @DBColumn
    private	String	ip	=	"";
    /**ip地址对应的地址名称*/
    @DBColumn
    private	String	ipName	=	"";
    /**黑名单层级*/
    @DBColumn
    private	Integer	blackLevelEnum	=	new Integer(0);
    /**黑名单类型*/
    @DBColumn
    private	Integer	blackTypeEnum	=	new Integer(0);
    /**下单的二级地址*/
    @DBColumn
    private	String	webUrl	=	"";
    /**下单域名*/
    @DBColumn
    private	String	webDomain	=	"";
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

    public OrdersAddition(){}

    public void setId(Integer	id){
        this.id = Long.valueOf(id);
    }

    public Integer getId(){
        return id.intValue();
    }

    public void setBrowser(String	browser){
        this.browser = browser;
    }

    public String getBrowser(){
        return browser;
    }

    public void setBrowseSecond(Integer	browseSecond){
        this.browseSecond = browseSecond;
    }

    public Integer getBrowseSecond(){
        return browseSecond;
    }

    public void setEquipment(String	equipment){
        this.equipment = equipment;
    }

    public String getEquipment(){
        return equipment;
    }

    public void setUserAgent(String	userAgent){
        this.userAgent = userAgent;
    }

    public String getUserAgent(){
        return userAgent;
    }

    public void setOrderFrom(String	orderFrom){
        this.orderFrom = orderFrom;
    }

    public String getOrderFrom(){
        return orderFrom;
    }

    public void setOrdersId(Long	orderId){
        this.ordersId = orderId;
    }

    public Long getOrdersId(){
        return ordersId;
    }

    public void setIp(String	ip){
        this.ip = ip;
    }

    public String getIp(){
        return ip;
    }

    public void setIpName(String	ipName){
        this.ipName = ipName;
    }

    public String getIpName(){
        return ipName;
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

    public void setWebUrl(String	webUrl){
        this.webUrl = webUrl;
    }

    public String getWebUrl(){
        return webUrl;
    }

    public void setWebDomain(String	webDomain){
        this.webDomain = webDomain;
    }

    public String getWebDomain(){
        return webDomain;
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

    @Override
    public String getTable() {
        return "orders_addition";
    }

}
