package com.stosz.order.ext.dto;

import com.google.common.collect.Lists;
import com.stosz.order.ext.enums.OrderTypeEnum;
import com.stosz.order.ext.enums.PayMethodEnum;
import com.stosz.plat.model.DBColumn;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @auther carter
 * create time    2017-11-17
 * 保存订单数据模型,最大粒度的分成四个部分；
 */
public class OrderSaveDto {

    private Long orderId = new Long(0);
    private OrderInfo orderInfo = new OrderInfo();
    private OrderLinkInfo orderLinkInfo = new OrderLinkInfo();
    private OrderWebInfo  orderWebInfo  = new OrderWebInfo();
    private List<OrderItemInfo> orderItemInfoList = Lists.newLinkedList();




    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderLinkInfo getOrderLinkInfo() {
        return orderLinkInfo;
    }

    public void setOrderLinkInfo(OrderLinkInfo orderLinkInfo) {
        this.orderLinkInfo = orderLinkInfo;
    }

    public OrderWebInfo getOrderWebInfo() {
        return orderWebInfo;
    }

    public void setOrderWebInfo(OrderWebInfo orderWebInfo) {
        this.orderWebInfo = orderWebInfo;
    }

    public List<OrderItemInfo> getOrderItemInfoList() {
        return orderItemInfoList;
    }

    public void setOrderItemInfoList(List<OrderItemInfo> orderItemInfoList) {
        this.orderItemInfoList = orderItemInfoList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public class OrderInfo{
        /**建站传过来的订单号*/
        private	String	merchantOrderNo	=	"";
        /**正常订单或者内部订单*/
        private	Integer	orderTypeEnum	= OrderTypeEnum.normal.ordinal();
        /**投放区域id*/
        private	String	zoneCode	=	"";
        /**币种*/
        private	String	currencyCode	=	"";
        /**订单导入时，获取到当前订单币种与人民币的汇率n            来自产品中心的汇率表*/
        private	java.math.BigDecimal	orderExchangeRate	=	new java.math.BigDecimal(1.0);
        /**订单金额 外币金额*/
        private	java.math.BigDecimal	orderAmount	=	new java.math.BigDecimal(0);
        /**商品数量*/
        private	Integer	goodsQty	=	new Integer(0);
        /**下单ip地址*/
        private	String	ip	=	"";
        /**客户留言*/
        private	String	customerMeassage	=	"";
        /**下单时间*/
        private	java.time.LocalDateTime	purchaserAt	=	java.time.LocalDateTime.now();
        /**用户接受到的验证码*/
        private	String	getCode	=	"";
        /**用户输入的验证码*/
        private	String	inputCode	=	"";
        /**优化师的id*/
        private	Integer	seoUserId	=	new Integer(0);
        /**创建者id*/
        private	Integer	creatorId	=	new Integer(0);
        /**创建人*/
        private	String	creator	=	"系统";
        /**支付方式。1：在线支付 。 2：货到付款*/
        private	Integer	paymentMethod	= PayMethodEnum.cod.ordinal();

        public String getMerchantOrderNo() {
            return merchantOrderNo;
        }

        public void setMerchantOrderNo(String merchantOrderNo) {
            this.merchantOrderNo = merchantOrderNo;
        }

        public Integer getOrderTypeEnum() {
            return orderTypeEnum;
        }

        public void setOrderTypeEnum(Integer orderTypeEnum) {
            this.orderTypeEnum = orderTypeEnum;
        }

        public String getZoneCode() {
            return zoneCode;
        }

        public void setZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public BigDecimal getOrderExchangeRate() {
            return orderExchangeRate;
        }

        public void setOrderExchangeRate(BigDecimal orderExchangeRate) {
            this.orderExchangeRate = orderExchangeRate;
        }

        public BigDecimal getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(BigDecimal orderAmount) {
            this.orderAmount = orderAmount;
        }

        public Integer getGoodsQty() {
            return goodsQty;
        }

        public void setGoodsQty(Integer goodsQty) {
            this.goodsQty = goodsQty;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCustomerMeassage() {
            return customerMeassage;
        }

        public void setCustomerMeassage(String customerMeassage) {
            this.customerMeassage = customerMeassage;
        }

        public LocalDateTime getPurchaserAt() {
            return purchaserAt;
        }

        public void setPurchaserAt(LocalDateTime purchaserAt) {
            this.purchaserAt = purchaserAt;
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

        public Integer getSeoUserId() {
            return seoUserId;
        }

        public void setSeoUserId(Integer seoUserId) {
            this.seoUserId = seoUserId;
        }

        public Integer getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public Integer getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(Integer paymentMethod) {
            this.paymentMethod = paymentMethod;
        }
    }
    public class OrderLinkInfo{
        /**名字*/
        private	String	firstName	=	"";
        /**姓*/
        private	String	lastName	=	"";
        /**国家*/
        private	String	country	=	"";
        /**电话*/
        private	String	telphone	=	"";
        /**邮箱*/
        private	String	email	=	"";
        /**省/洲*/
        private	String	province	=	"";
        /**城市*/
        private	String	city	=	"";
        /**区域*/
        private	String	area	=	"";
        /**地址*/
        private	String	address	=	"";
        /**邮编*/
        private	String	zipcode	=	"";

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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
    }
    public class OrderItemInfo{

        /**产品spu*/
        private	String	spu	=	"";
        /**产品sku*/
        private	String	sku	=	"";
        /**数量*/
        private	Integer	qty	=	new Integer(0);
        /**单价*/
        private	java.math.BigDecimal	singleAmount	=	new java.math.BigDecimal(0);
        /**总价*/
        private	java.math.BigDecimal	totalAmount	=	new java.math.BigDecimal(0);
        /**产品id*/
        private	Long	productId	=	new Long(0);
        /**外文名称*/
        private	String	foreignTitle	=	"";
        /**带外文的总价*/
        private	String	foreignTotalAmount	=	"";
        /**属性名的json数组*/
        private	String	attrNameArray	=	"";
        /**属性值的json数组*/
        private	String	attrValueArray	=	"";

        public String getSpu() {
            return spu;
        }

        public void setSpu(String spu) {
            this.spu = spu;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public BigDecimal getSingleAmount() {
            return singleAmount;
        }

        public void setSingleAmount(BigDecimal singleAmount) {
            this.singleAmount = singleAmount;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getForeignTitle() {
            return foreignTitle;
        }

        public void setForeignTitle(String foreignTitle) {
            this.foreignTitle = foreignTitle;
        }

        public String getForeignTotalAmount() {
            return foreignTotalAmount;
        }

        public void setForeignTotalAmount(String foreignTotalAmount) {
            this.foreignTotalAmount = foreignTotalAmount;
        }

        public String getAttrNameArray() {
            return attrNameArray;
        }

        public void setAttrNameArray(String attrNameArray) {
            this.attrNameArray = attrNameArray;
        }

        public String getAttrValueArray() {
            return attrValueArray;
        }

        public void setAttrValueArray(String attrValueArray) {
            this.attrValueArray = attrValueArray;
        }
    }
    public class OrderWebInfo{

        /**设备agent*/
        @DBColumn
        private	String	userAgent	=	"";
        /**订单来源 fb tw等*/
        @DBColumn
        private	String	orderFrom	=	"";
        /**ip地址*/
        @DBColumn
        private	String	ip	=	"";
        /**下单域名*/
        @DBColumn
        private	String	webDomain	=	"";

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public String getOrderFrom() {
            return orderFrom;
        }

        public void setOrderFrom(String orderFrom) {
            this.orderFrom = orderFrom;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getWebDomain() {
            return webDomain;
        }

        public void setWebDomain(String webDomain) {
            this.webDomain = webDomain;
        }
    }


}
