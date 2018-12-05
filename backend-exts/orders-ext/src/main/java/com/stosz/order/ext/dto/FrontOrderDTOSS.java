package com.stosz.order.ext.dto;

import java.util.List;

public class FrontOrderDTOSS {

    private String web_url;
    private String first_name;
    private String last_name;
    private String tel;
    private String email;
    private String address;
    private String remark;
    private String zipcode;
    private String country;
    private String province;
    private String city;
    private String area;
    private String id_zone;
    private String code_zone;
    private String id_department;
    private String id_users;
    private String identify;
    private int grand_total;
    private String currency_code;
    private String date_purchase;
    private String payment_method;
    private String payment_status;
    private String payment_details;
    private String created_at;
    private String ip;
    private String user_agent;
    private String http_referer;
    private int number;
    private WebInfoBean web_info;
    private String order_id;
    private String website;
    private String username;
    private List<ProductsBean> products;
    private List<?> expends;

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

    public String getId_zone() {
        return id_zone;
    }

    public void setId_zone(String id_zone) {
        this.id_zone = id_zone;
    }

    public String getCode_zone() {
        return code_zone;
    }

    public void setCode_zone(String code_zone) {
        this.code_zone = code_zone;
    }

    public String getId_department() {
        return id_department;
    }

    public void setId_department(String id_department) {
        this.id_department = id_department;
    }

    public String getId_users() {
        return id_users;
    }

    public void setId_users(String id_users) {
        this.id_users = id_users;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
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

    public String getDate_purchase() {
        return date_purchase;
    }

    public void setDate_purchase(String date_purchase) {
        this.date_purchase = date_purchase;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
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

    public WebInfoBean getWeb_info() {
        return web_info;
    }

    public void setWeb_info(WebInfoBean web_info) {
        this.web_info = web_info;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public List<?> getExpends() {
        return expends;
    }

    public void setExpends(List<?> expends) {
        this.expends = expends;
    }

    public static class WebInfoBean {
        private String colorDepth;
        private String resolution;
        private Object timeZone;
        private String browserLan;
        private HttpHeadsBean httpHeads;
        private int orderSubmitTimer;
        private int indexTimer;
        private int disableJs;
        private String device;
        private String mob_backup;
        private String website;
        private String vercode;
        private String incode;
        private int verify_status;

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

        public HttpHeadsBean getHttpHeads() {
            return httpHeads;
        }

        public void setHttpHeads(HttpHeadsBean httpHeads) {
            this.httpHeads = httpHeads;
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

        public static class HttpHeadsBean {
//            private String host; // FIXME check this code
//            private String connection;
//            @com.google.gson.annotations.SerializedName("USER-AGENT")
//            private String useragent;
//            private String ACCEPT;
//            private String REFERER;
//            @com.google.gson.annotations.SerializedName("ACCEPT-ENCODING")
//            private String ACCEPTENCODING;
//            @com.google.gson.annotations.SerializedName("ACCEPT-LANGUAGE")
//            private String ACCEPTLANGUAGE;
//            private String COOKIE;
//
//            public String get_$HOST197() {
//                return _$HOST197;
//            }
//
//            public void set_$HOST197(String _$HOST197) {
//                this._$HOST197 = _$HOST197;
//            }
//
//            public String getCONNECTION() {
//                return CONNECTION;
//            }
//
//            public void setCONNECTION(String CONNECTION) {
//                this.CONNECTION = CONNECTION;
//            }
//
//            public String getUSERAGENT() {
//                return USERAGENT;
//            }
//
//            public void setUSERAGENT(String USERAGENT) {
//                this.USERAGENT = USERAGENT;
//            }
//
//            public String getACCEPT() {
//                return ACCEPT;
//            }
//
//            public void setACCEPT(String ACCEPT) {
//                this.ACCEPT = ACCEPT;
//            }
//
//            public String getREFERER() {
//                return REFERER;
//            }
//
//            public void setREFERER(String REFERER) {
//                this.REFERER = REFERER;
//            }
//
//            public String getACCEPTENCODING() {
//                return ACCEPTENCODING;
//            }
//
//            public void setACCEPTENCODING(String ACCEPTENCODING) {
//                this.ACCEPTENCODING = ACCEPTENCODING;
//            }
//
//            public String getACCEPTLANGUAGE() {
//                return ACCEPTLANGUAGE;
//            }
//
//            public void setACCEPTLANGUAGE(String ACCEPTLANGUAGE) {
//                this.ACCEPTLANGUAGE = ACCEPTLANGUAGE;
//            }
//
//            public String getCOOKIE() {
//                return COOKIE;
//            }
//
//            public void setCOOKIE(String COOKIE) {
//                this.COOKIE = COOKIE;
//            }
        }
    }

    public static class ProductsBean {
        /**
         * id_product : 87859
         * product_id : 1019
         * product_title : 哥哥专辑红，送大幅海报
         * sale_title : 哥哥专辑红，送大幅海报
         * promotion_price : 300.00
         * price : 300
         * price_title : NT$300
         * qty : 1
         * attrs : ["230837","230835"]
         * attrs_title : ["礼物盒包装","唱片+海报"]
         * spu :
         * sku :
         * id_activity :
         * product_title_foregin :
         */

        private String id_product;
        private String product_id;
        private String product_title;
        private String sale_title;
        private String promotion_price;
        private int price;
        private String price_title;
        private int qty;
        private String spu;
        private String sku;
        private String id_activity;
        private String product_title_foregin;
        private List<String> attrs;
        private List<String> attrs_title;

        public String getId_product() {
            return id_product;
        }

        public void setId_product(String id_product) {
            this.id_product = id_product;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
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

        public String getPromotion_price() {
            return promotion_price;
        }

        public void setPromotion_price(String promotion_price) {
            this.promotion_price = promotion_price;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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

        public String getProduct_title_foregin() {
            return product_title_foregin;
        }

        public void setProduct_title_foregin(String product_title_foregin) {
            this.product_title_foregin = product_title_foregin;
        }

        public List<String> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<String> attrs) {
            this.attrs = attrs;
        }

        public List<String> getAttrs_title() {
            return attrs_title;
        }

        public void setAttrs_title(List<String> attrs_title) {
            this.attrs_title = attrs_title;
        }
    }
}
