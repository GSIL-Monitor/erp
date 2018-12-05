package com.stosz.plat.wms.model;

import org.springframework.beans.factory.annotation.Value;

public final class WmsConfig {


    @Value("${wms.host}")
    private String wmsHost;
    @Value("${wms.port}")
    private String wmsPort;

    @Value("${SERVICE_URL}")
    private String SERVICE_URL ;

    @Value("${APPKEY}")
    private String APPKEY ;
    @Value("${APPSECRET}")
    private String APPSECRET;
    @Value("${FORMAT}")
    private String FORMAT;
    //	public final static String FORMAT = "XML";
    @Value("${ENCRYPT}")
    private String ENCRYPT;


    /**
     * 1.新增修改商品档案服务名
     */
    public static final String  Service_insertOrUpdateProduct = "subGoodsCreateOrUpadte";

    /**
     * 2.新增修改供应商档案的服务名
     */
    public static final String  Service_subSupplierCreateOrUpdate = "subSupplierCreateOrUpdate";
    /**
     * 新增销售订单
     */
    public static final String  Service_subCreateSaleOrder = "subCreateSaleOrder";

    /**
     * 3.新增采购单的服务名
     */
    public static final String Service_subCreatePurchaseOrder = "subCreatePurchaseOrder";

    public static final String Service_subUpdatePurchaseExpress = "subUpdatePurchaseExpress";

    /**
     * 新增采购退货计划
     */
    public static final String Service_subCreatePurchaseReturnPlan="subCreatePurchaseReturnPlan";
    
    /**
     * 单据取消接口
     */
    public static final String Service_subCancelOrder="subCancelOrder";

    /**
     * 新增销售订单
     */
    public static final String  Service_subCreateTranPlan = "subCreateTranPlan";

    public String getSERVICE_URL() {
        return "http://" + wmsHost + ":"+wmsPort+SERVICE_URL;
    }

    public String getAPPKEY() {
        return APPKEY;
    }

    public String getAPPSECRET() {
        return APPSECRET;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public String getENCRYPT() {
        return ENCRYPT;
    }
}
