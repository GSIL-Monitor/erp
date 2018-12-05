package com.stosz.order.ext.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author wangqian
 * 建站传过来的订单数据实体
 */
public class FrontOrderDTO {

    /**
     * 验证key
     */
    private String key;

    /**
     * 订单来源（单品还是综合或其它）
     */
    private Integer merchant_enum;

    /**
     * 套餐编号
     */
    private String combo_id;

    /**
     * 套餐名称
     */
    private String combo_name;

    /**
     * 订单编号
     */
    private String order_id;

    /**
     * 订单域名
     */
    private String web_url;

    /**
     * 姓名
     */
    private String first_name;

    /**
     * 名
     */
    private String last_name;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 留言
     */
    private String remark;

    /**
     * 邮编
     */
    private String zipcode;

    /**
     * 国家名称
     */
    private String country;

    /**
     * 国家id
     */
    private int id_country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String area;

    /**
     * 产品投放区域ID
     */
    private int id_zone;

    /**
     * 产品投放区域编码
     */
    private String code_zone;

    /**
     * （已废弃）产品优化师部门ID
     */
    private int id_department;

    /**
     * 产品优化师部门ID
     */
    private int oa_id_department;

    /**
     * 优化师ID
     */
    private int id_users;

    // TODO: 2017/11/9  未明确
    /**
     *
     */
    private int identify;

    /**
     * 总价格
     */
    private int  grand_total;

    /**
     * 货币符号
     */
    private String currency_code;

    /**
     * 购买时间
     */
    private LocalDateTime date_purchase;

    /**
     * 0 货到付款 1在线支付
     */
    private int payment_method;

    /**
     * 购买状态
     */
    private String payment_status;

    /**
     * 购买详情
     */
    private String payment_details;

    /**
     * 订单创建时间
     */
    private LocalDateTime created_at;

    /**
     * 购买用户Ip
     */
    private String ip;

    /**
     * 用户代理信息
     */
    private String  user_agent;

    /**
     * 来源
     */
    private String http_referer;

    /**
     * 数量
     */
    private int number;

    /**
     * 扩展字段
     */
    private List<String> expends;

    /**
     * 浏览器信息
     */
    private Web_Info web_info;



    /**
     * 产品网址
     */
    private String website;

    /**
     * 优化师登录名
     */
    private String username;


    /**
     * 订单数据
     */
    private List<OrderData> products;





    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
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

    public int getId_zone() {
        return id_zone;
    }

    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }

    public String getCode_zone() {
        return code_zone;
    }

    public void setCode_zone(String code_zone) {
        this.code_zone = code_zone;
    }

    @Deprecated
    public int getId_department() {
        return id_department;
    }

    @Deprecated
    public void setId_department(int id_department) {
        this.id_department = id_department;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public int getIdentify() {
        return identify;
    }

    public void setIdentify(int identify) {
        this.identify = identify;
    }

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public LocalDateTime getDate_purchase() {
        return date_purchase;
    }

    public void setDate_purchase(LocalDateTime date_purchase) {
        this.date_purchase = date_purchase;
    }

    public int getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(int payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_details() {
        return payment_details;
    }

    public void setPayment_details(String payment_details) {
        this.payment_details = payment_details;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getHttp_referer() {
        return http_referer;
    }

    public void setHttp_referer(String http_referer) {
        this.http_referer = http_referer;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderData> getProducts() {
        return products;
    }

    public void setProducts(List<OrderData> products) {
        this.products = products;
    }

    public List<String> getExpends() {
        return expends;
    }

    public void setExpends(List<String> expends) {
        this.expends = expends;
    }

    public Integer getMerchant_enum() {
        return merchant_enum;
    }

    public void setMerchant_enum(Integer merchant_enum) {
        this.merchant_enum = merchant_enum;
    }

    public String getCombo_id() {
        return combo_id;
    }

    public void setCombo_id(String combo_id) {
        this.combo_id = combo_id;
    }

    public String getCombo_name() {
        return combo_name;
    }

    public void setCombo_name(String combo_name) {
        this.combo_name = combo_name;
    }

    public int getOa_id_department() {
        return oa_id_department;
    }

    public void setOa_id_department(int oa_id_department) {
        this.oa_id_department = oa_id_department;
    }


    public static class OrderData {


        /**
         * ERP产品ID
         */
        private Integer id_product;

        /**
         * 建站产品ID
         */
        private int product_id;

        /**
         * 产品标题
         */
        private String product_title;

        /**
         * 销售标题
         */
        private String sale_title;

        /**
         *  产品价格
         */
        private double price;

        /**
         * 商品单价
         */
        private double promotion_price;

        /**
         * 带货币符号的总价
         */
        private String price_title;

        /**
         * 产品数量
         */
        private int qty;


        /**
         * 产品的外文名称
         */
        private String product_title_foregin;

        /**
         * 商品spu
         */
        private String spu;

        /**
         * 商品SKU
         */
        private String sku;

        /**
         * 活动ID
         */
        private String id_activity;

        // TODO: 2017/11/9  list 是String还是 Integer？
        /**
         * 产品数组属性
         */
        private List<Integer> attrs;


        /**
         * 属性名数组
         */
        private List<String> attrs_title;


        public Integer getId_product() {
            return id_product;
        }

        public void setId_product(Integer id_product) {
            this.id_product = id_product;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }

        public String getSale_title() {
            return sale_title;
        }

        public void setSale_title(String sale_title) {
            this.sale_title = sale_title;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(double promotion_price) {
            this.promotion_price = promotion_price;
        }

        public String getPrice_title() {
            return price_title;
        }

        public void setPrice_title(String price_title) {
            this.price_title = price_title;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getProduct_title_foregin() {
            return product_title_foregin;
        }

        public void setProduct_title_foregin(String product_title_foregin) {
            this.product_title_foregin = product_title_foregin;
        }

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

        public String getId_activity() {
            return id_activity;
        }

        public void setId_activity(String id_activity) {
            this.id_activity = id_activity;
        }

        public List<Integer> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<Integer> attrs) {
            this.attrs = attrs;
        }

        public List<String> getAttrs_title() {
            return attrs_title;
        }

        public void setAttrs_title(List<String> attrs_title) {
            this.attrs_title = attrs_title;
        }

        @Override
        public String toString() {
            return "OrderData{" +
                    "id_product=" + id_product +
                    ", product_id=" + product_id +
                    ", product_title='" + product_title + '\'' +
                    ", sale_title='" + sale_title + '\'' +
                    ", price=" + price +
                    ", promotion_price=" + promotion_price +
                    ", price_title='" + price_title + '\'' +
                    ", qty=" + qty +
                    ", product_title_foregin=" + product_title_foregin +
                    ", spu='" + spu + '\'' +
                    ", sku='" + sku + '\'' +
                    ", id_activity='" + id_activity + '\'' +
                    ", attrs=" + attrs +
                    ", attrs_title=" + attrs_title +
                    '}';
        }
    }

    public static class Web_Info{
        private String colorDepth;
        private String resolution;
        private Object timeZone;
        private String browserLan;
        private HttpHeads httpHeads;
        private int orderSubmitTimer;
        private int indexTimer;
        private int disableJs;
        private String device;
        private String mob_backup;
        private String website;
        private String vercode;
        private String incode;
        private int verify_status;

        public HttpHeads getHttpHeads() {
            return httpHeads;
        }

        public void setHttpHeads(HttpHeads httpHeads) {
            this.httpHeads = httpHeads;
        }

        public String getColorDepth() {
            return colorDepth;
        }

        public void setColorDepth(String colorDepth) {
            this.colorDepth = colorDepth;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public Object getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(Object timeZone) {
            this.timeZone = timeZone;
        }

        public String getBrowserLan() {
            return browserLan;
        }

        public void setBrowserLan(String browserLan) {
            this.browserLan = browserLan;
        }

        public int getOrderSubmitTimer() {
            return orderSubmitTimer;
        }

        public void setOrderSubmitTimer(int orderSubmitTimer) {
            this.orderSubmitTimer = orderSubmitTimer;
        }

        public int getIndexTimer() {
            return indexTimer;
        }

        public void setIndexTimer(int indexTimer) {
            this.indexTimer = indexTimer;
        }

        public int getDisableJs() {
            return disableJs;
        }

        public void setDisableJs(int disableJs) {
            this.disableJs = disableJs;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getMob_backup() {
            return mob_backup;
        }

        public void setMob_backup(String mob_backup) {
            this.mob_backup = mob_backup;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getVercode() {
            return vercode;
        }

        public void setVercode(String vercode) {
            this.vercode = vercode;
        }

        public String getIncode() {
            return incode;
        }

        public void setIncode(String incode) {
            this.incode = incode;
        }

        public int getVerify_status() {
            return verify_status;
        }

        public void setVerify_status(int verify_status) {
            this.verify_status = verify_status;
        }
    }

    public static class HttpHeads{

        @JsonProperty(value = "REFERER")
        private String referer;

        @JsonProperty(value = "HOST")
        private String host;

        public String getReferer() {
            return referer;
        }

        public void setReferer(String referer) {
            this.referer = referer;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }


    public Web_Info getWeb_info() {
        return web_info;
    }

    public void setWeb_info(Web_Info web_info) {
        this.web_info = web_info;
    }


}
