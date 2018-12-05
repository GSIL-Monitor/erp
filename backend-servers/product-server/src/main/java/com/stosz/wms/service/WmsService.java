package com.stosz.wms.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.base.Strings;
import com.stosz.plat.utils.HttpClient;
import com.stosz.plat.wms.bean.GoodsCreateOrUpdateRequest;
import com.stosz.plat.wms.bean.SkuInfoBean;
import com.stosz.plat.wms.model.WmsConfig;
import com.stosz.plat.wms.model.WmsSender;
import com.stosz.product.ext.model.AttributeValue;
import com.stosz.product.ext.model.Category;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.service.IAttributeValueService;
import com.stosz.product.ext.service.IWmsService;
import com.stosz.product.service.CategoryService;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductSkuService;
import com.stosz.product.service.WmsPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class WmsService implements IWmsService {

    private static final Logger logger = LoggerFactory.getLogger(WmsService.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Value("${image.host}")
    private String  imageDomain;

    @Autowired
    private IAttributeValueService IAttributeValueService;


    private static ObjectMapper objectMapper;

    @Autowired
    private WmsConfig wmsConfig;

    @Autowired
    private WmsPushService wmsPushService;


    @Autowired
    private ProductSkuService productSkuService;

    /**
     * 每次60s重新推送100条没有成功的的sku；
     */
    @Scheduled(fixedRate = 60 * 1000)
    @Override
    public void repostFailWmsMessage() {

        //2,找到所有推送失败，次数少于三次的wms_push消息,进行重新推送；
        wmsPushService.getByStatus(-1).stream()
                .filter(item -> null != item.getObjectId() && item.getObjectId().longValue() > 0)
                .forEach(wmsPush -> {
                    String sku = productSkuService.getById(wmsPush.getObjectId().intValue()).getSku();

                    String responseString = new HttpClient().pub2(wmsPush.getPushUrl(), wmsPush.getPushParam());

                    wmsPushService.pushResult(Long.valueOf(wmsPush.getId()), responseString);

                    repostBySku(sku);
                });


        //1,查到所有的推送失败的sku，去重,进行重新推送
        Set<String> skuSet = productSkuService.findWmsPushErrSkus();
        Optional.ofNullable(skuSet)
                .ifPresent(skus -> skus.stream()
                        .forEach(sku -> repostBySku(sku)));


    }


    @Override
    public void repostBySku(String sku) {
        ProductSku bySku = productSkuService.getBySku(sku);
        this.insertOrUpdateProductMessage(bySku, false);
        try {
            TimeUnit.MILLISECONDS.sleep(30);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public  ObjectMapper getObjectMapper(){
    if(null == objectMapper )
    {
        objectMapper =  new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    return  objectMapper;
    }



    @Override
    public void insertOrUpdateProduct(ProductSku productSku) {

        insertOrUpdateProductMessage(productSku,true);

    }

    @Override
    @Async("wmsThreadPoolTaskExecutor")
    public void insertOrUpdateProductMessage(ProductSku productSku, boolean wait) {

        Assert.notNull(productSku,"productSku不能为空");

        GoodsCreateOrUpdateRequest goodsCreateOrUpdateRequest = new GoodsCreateOrUpdateRequest();

        SkuInfoBean skuInfoBean = new SkuInfoBean();

        Assert.isTrue(!Strings.isNullOrEmpty(productSku.getSku()),"sku不能为空");
        skuInfoBean.setSku(Optional.of(productSku.getSku()).map(item->{
            item = getTransValue(item);
            if (item.length() > 50) return item.substring(0, 49);
            return item;
        }).get());//商品编码最长50


        Product product =  productService.getById(productSku.getProductId());
        Assert.notNull(product,"product不存在");

        skuInfoBean.setGoodsName(Optional.of(product.getInnerName()).map(item->{
            item = getTransValue(item);
            if (item.length() > 200) return item.substring(0, 199);
            return item;
        }).get());//商品名称


        //品牌代码，品牌名没有 brandCode brandName
        Category category = categoryService.getById(product.getCategoryId());
        Assert.notNull(category,"数据库中不存在品类id:" + product.getCategoryId());
        skuInfoBean.setCategoryCode(String.valueOf(category.getId()));
        skuInfoBean.setCategoryName(getTransValue(category.getName()));

        Optional.ofNullable(product.getPurchasePrice()).ifPresent(price -> skuInfoBean.setPrice(getTransValue(price.toString())));
        Optional.ofNullable(productSku.getBarcode()).ifPresent(barcode -> skuInfoBean.setBrcode(getTransValue(barcode)));

        Optional.ofNullable(product.getInnerName()).ifPresent(goodsAbbreviation -> skuInfoBean.setGoodsAbbreviation(getTransValue(goodsAbbreviation)));
        Optional.ofNullable(product.getLongness()).ifPresent(length -> skuInfoBean.setLength(getTransValue(length.toString())));

        Optional.ofNullable(product.getWidth()).ifPresent(width -> skuInfoBean.setWide(getTransValue(width.toString())));
        Optional.ofNullable(product.getHeight()).ifPresent(width -> skuInfoBean.setHigh(getTransValue(width.toString())));

//        Optional.ofNullable(product.getMemo()).ifPresent(remark-> skuInfoBean.setRemark(remark));
        Optional.ofNullable(product.getMainImageUrl()).ifPresent(gwf2->  skuInfoBean.setGwf2(getURL(gwf2)));
//        Optional.ofNullable(product.getSourceUrl()).ifPresent(gwf3->  skuInfoBean.setGwf3(getURL(gwf3)));
//        Optional.ofNullable(product.getPurchaseUrl()).ifPresent(gwf4->  skuInfoBean.setGwf4(getURL(gwf4)));


        /**
         * 产品的属性值组合
         */

        if (wait) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
            }
        }

        List<AttributeValue> valueList = IAttributeValueService.getValueListBySku(productSku.getSku());

        skuInfoBean.setColor("没有属性值组合");
        Optional.ofNullable(valueList).ifPresent(valueListItem -> skuInfoBean.setColor(getTransValue(valueListItem.stream()
                .map(AttributeValue::getTitle).sorted(Comparator.naturalOrder()).collect(Collectors.joining("-")))));


        Optional.ofNullable(product.getHeight()).ifPresent(height -> skuInfoBean.setGrossWeight(getTransValue(String.valueOf(height))));
        skuInfoBean.setUpdateDate(Optional.ofNullable(product.getUpdateAt()).orElse(LocalDateTime.now()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        skuInfoBean.setState("1");

        goodsCreateOrUpdateRequest.setItems(Arrays.asList(skuInfoBean));

        Long wmsPushId = 0L;
        try {
            WmsSender wmsSender = new WmsSender(WmsConfig.Service_insertOrUpdateProduct, getObjectMapper().writeValueAsString(goodsCreateOrUpdateRequest), wmsConfig);
            String baseContent = wmsSender.getJsonContent();
            WmsConfig wmsConfig = wmsSender.getWmsConfig();
            String param = "appkey="+wmsConfig.getAPPKEY()+"&service="+wmsSender.getUrl()+"&format="+wmsConfig.getFORMAT()+"&encrypt="+wmsConfig.getENCRYPT()+"&content="+baseContent+"&secret="+null;
            logger.info("发送出去的参数："+param);
            wmsPushId = wmsPushService.insert(Long.valueOf(productSku.getId()), "ProductSku", wmsConfig.getSERVICE_URL(), param);
            String responseString = new HttpClient().pub2(wmsConfig.getSERVICE_URL(), param);

            wmsPushService.pushResult(wmsPushId,responseString);


        } catch (Exception e) {
            logger.error("推送wms接口信息异常",e);
            wmsPushService.pushResult(wmsPushId,e.getMessage());
        }

        return;

    }

    private String getTransValue(String item) {
        if (Strings.isNullOrEmpty(item)) return item;
//        & 转换成 %26  =转换成%3D   特殊处理一下； add by Carter20171113
        if (item.contains("=")) item = item.replaceAll("=", "%3D");
        if (item.contains("&")) item = item.replaceAll("&", "%26");
        return item;
    }

    private String getURL(String gwf2) {
        if(!gwf2.startsWith("http"))  return imageDomain+"/"+ gwf2;
        return gwf2;
    }

}
