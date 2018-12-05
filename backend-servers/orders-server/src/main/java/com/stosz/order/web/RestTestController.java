package com.stosz.order.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.stosz.order.ext.dto.FrontOrderDTO;
import com.stosz.order.ext.enums.ItemStatusEnum;
import com.stosz.order.ext.model.OrdersItem;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders/test")
public class RestTestController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductSkuService productSkuService;

    @Autowired
    private RabbitMQPublisher publisher;


    private static String orderContentJson = "{\"messageType\":\"frontOrder\",\"data\":{\"key\":\"d2e3d6b05e82822d8fb7f02b4a7d79aa\",\"web_url\":\"www.livestou.com\",\"first_name\":\"\\u738b\\u9a9e\",\"last_name\":\"\",\"tel\":\"0910000000\",\"email\":\"513888@qq.com\",\"address\":\"\\u6df1\\u5733\\u5357\\u5c71\\u52a8\\u6f2b\\u56ed\",\"remark\":\"SS\",\"zipcode\":\"518001\",\"country\":\"\\u4e2d\\u56fd\",\"province\":\"\\u9999\\u6e2f\",\"city\":\"\",\"area\":\"\",\"products\":[{\"id_product\":\"87788\",\"product_id\":\"937\",\"product_title\":\"\\u5468\\u516c\\u5b50\\u540c\\u6b3e\\u4fdd\\u6696\\u7f8a\\u76ae\\u9774 5CM\\u9ad8\\u8ddf\",\"sale_title\":\"shoes\",\"price\":23400,\"promotion_price\":7800,\"price_title\":\"HK$23400\",\"qty\":3,\"attrs\":[\"230632\",\"230636\"],\"attrs_title\":[\"wh\",\"36\"],\"spu\":\"\",\"sku\":\"\",\"id_activity\":\"\",\"product_title_foregin\":\"\"}],\"id_zone\":\"3\",\"code_zone\":\"HK\",\"id_department\":\"26\",\"id_users\":\"518\",\"identify\":\"518\",\"grand_total\":23400,\"currency_code\":\"HKD\",\"date_purchase\":\"2018-01-19 17:45:51\",\"payment_method\":\"0\",\"payment_status\":\"processing\",\"payment_details\":\"\",\"created_at\":\"2018-01-19 17:45:51\",\"ip\":\"192.168.100.103\",\"user_agent\":\"Mozilla\\/5.0 (Windows NT 10.0; WOW64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/62.0.3202.62 Safari\\/537.36\",\"http_referer\":\"http:\\/\\/luckydog-front-admin-dev.stosz.com\\/index.php\",\"number\":3,\"expends\":[],\"web_info\":{\"colorDepth\":\"24\",\"resolution\":\"1920x1080\",\"timeZone\":null,\"browserLan\":\"\\u7b80\\u4f53\\u4e2d\\u6587\",\"httpHeads\":{\"HOST\":\"www.livestou.com\",\"CONNECTION\":\"keep-alive\",\"USER-AGENT\":\"Mozilla\\/5.0 (Windows NT 10.0; WOW64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/62.0.3202.62 Safari\\/537.36\",\"ACCEPT\":\"*\\/*\",\"REFERER\":\"http:\\/\\/www.livestou.com\\/0hjujuyj\",\"ACCEPT-ENCODING\":\"gzip, deflate\",\"ACCEPT-LANGUAGE\":\"zh-CN,zh;q=0.9\",\"COOKIE\":\"_utm=973; google_js_st=1; POP800_VISIT_TIMES=3; PAGE_VIEW_TIMES=5; POP800_VISITOR_ID_L=6B753849E2544C48E2B02EE997DEC132; PHPSESSID=p4mdrkjkl0a437sfachlo6pe42; need_login=0; formToken=dc71ab8b9ddaf399799980ed6e8316a8; orderSubmitTimer=1516345155; indexTimer=1516345155; a2546_pages=1; a2546_times=1; __tins__19042546=%7B%22sid%22%3A%201516345150921%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201516346950921%7D; __51cke__=; __51laig__=1; _ga=GA1.2.1644331521.1513934603; _gid=GA1.2.546296740.1516345151; _gat=1; uuid=2e7e194f96314066ba1283b613518845; referer=http:\\/\\/luckydog-front-admin-dev.stosz.com\\/index.php\",\"CONTENT-LENGTH\":\"\",\"CONTENT-TYPE\":\"\"},\"orderSubmitTimer\":44,\"indexTimer\":53,\"disableJs\":0,\"device\":\"pc\",\"mob_backup\":\"\",\"website\":\"www.livestou.com\\/6547tyl\"},\"order_id\":\"1180119174551982622\",\"vercode\":\"\",\"website\":\"www.livestou.com\\/6547tyl\",\"combo_id\":\"0\",\"combo_name\":\"\",\"merchant_enum\":1,\"oa_id_department\":\"58\",\"username\":\"lidanna\"},\"from\":\"front\",\"method\":\"insert\"}";

    @RequestMapping("quickOrder")
    public RestResult quickOrder(@RequestBody OrderDataInfo orderInfo) throws IOException {
        Map<String,Object> messageMap = objectMapper.readValue(orderContentJson, new HashMap<>().getClass());
        Map<String,Object>  dataJsonMap = (Map<String, Object>) Optional.ofNullable(messageMap.get("data")).orElse(new HashMap());
        FrontOrderDTO dto = objectMapper.readValue(objectMapper.writeValueAsString(dataJsonMap),FrontOrderDTO.class);


        dto.setOa_id_department(orderInfo.getDeptId());
        dto.setId_zone(orderInfo.getZoneId());

        //更改产品信息
        List<FrontOrderDTO.OrderData> products = new ArrayList<>();
        orderInfo.productList.stream().forEach(e -> {
            List<Integer> attrs = Arrays.stream(e.getAttrIds().split(",")).map(attr -> Integer.valueOf(attr)).collect(Collectors.toList());
            Product product = productService.getProductInfoById(e.getProductId());
            String sku = productSkuService.getSkuByProductRelId(e.getProductId(),attrs);
            FrontOrderDTO.OrderData orderData = new FrontOrderDTO.OrderData();
            orderData.setId_product(e.getProductId());
            orderData.setProduct_id(e.getProductId());
            orderData.setProduct_title(product.getTitle());
            orderData.setSale_title(product.getTitle());
            orderData.setPrice(1000.0);
            orderData.setPromotion_price(1000.0D);
            orderData.setPrice_title("$1000.0");
            orderData.setQty(e.getCount());
            orderData.setProduct_title_foregin(product.getTitle());
            orderData.setSpu(product.getSpu());
            orderData.setSku(sku);
            orderData.setId_activity("");
            orderData.setAttrs(Lists.newArrayList());
            orderData.setAttrs_title(Lists.newArrayList());
            products.add(orderData);
        });
        dto.setProducts(products);

        publisher.saveMessage("frontOrder",dto);

        return new RestResult();
    }


    public static class OrderDataInfo{
        private Integer zoneId;
        private Integer deptId;

        List<ProductData> productList;

        public Integer getZoneId() {
            return zoneId;
        }

        public void setZoneId(Integer zoneId) {
            this.zoneId = zoneId;
        }

        public Integer getDeptId() {
            return deptId;
        }

        public void setDeptId(Integer deptId) {
            this.deptId = deptId;
        }

        public List<ProductData> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductData> productList) {
            this.productList = productList;
        }
    }

    public static class ProductData{
        private Integer productId;
        private String attrIds;
        private Integer count;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getAttrIds() {
            return attrIds;
        }

        public void setAttrIds(String attrIds) {
            this.attrIds = attrIds;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }





}
