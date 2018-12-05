package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.sync.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 老erp产品同步的controller
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/4]
 */
@Controller
@RequestMapping("/product/sync")
public class OldErpSyncController extends AbstractController {

    @Resource
    private OldErpProductSyncService oldErpProductSyncService;
    @Resource
    private OldErpAttributeSyncService oldErpAttributeSyncService;
    @Resource
    private OldErpAttributeValueSyncService oldErpAttributeValueSyncService;
    @Resource
    private OldErpCategorySyncService oldErpCategorySyncService;
    @Resource
    private OldErpProductSkuSyncService oldErpProductSkuSyncService;
    @Resource
    private OldErpProductZoneSyncService oldErpProductZoneSyncService;
    @Resource
    private OldErpCountrySyncService oldErpCountrySyncService;
    @Resource
    private OldErpZoneSyncService oldErpZoneSyncService;
    @Resource
    private OldErpQuantitySyncService oldErpQuantitySyncService;
    @Resource
    private CategoryAttributeRelSyncService categoryAttributeRelSyncService;
    @Resource
    private OldErpCheckProductSyncService oldErpCheckProductSyncService;
    @Resource
    private ProductPushService productPushService;

    @Value("${file.uploadDir}")
    private String filePath;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/product")
    @ResponseBody
    public RestResult productSync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpProductSyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp产品时出现异常", e);
            throw new RuntimeException("拉取老erp产品时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/attribute")
    @ResponseBody
    public RestResult attributeSync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpAttributeSyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp产品属性时出现异常", e);
            throw new RuntimeException("拉取老erp产品属性时出现异常", e);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/attributeValue")
    public RestResult attributeValueSync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpAttributeValueSyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp产品属性值时出现异常", e);
            throw new RuntimeException("拉取老erp产品属性值时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/category")
    @ResponseBody
    public RestResult categorySync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpCategorySyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp品类时出现异常", e);
            throw new RuntimeException("拉取老erp品类时出现异常", e);
        }
        return result;

    }

    @RequestMapping("/productSku")
    @ResponseBody
    public RestResult productSkuSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpProductSkuSyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp产品SKU时出现异常", e);
            throw new RuntimeException("拉取老erp产品SKU时出现异常", e);
        }
        return result;

    }

    @RequestMapping("/productZone")
    @ResponseBody
    public RestResult productZoneSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpProductZoneSyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp产品区域时出现异常", e);
            throw new RuntimeException("拉取老erp产品区域时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/productZoneDomain")
    @ResponseBody
    public RestResult productZoneDomainSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpProductZoneSyncService.pullDomain();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp产品区域域名时出现异常", e);
            throw new RuntimeException("拉取老erp产品区域域名时出现异常", e);
        }
        return result;
    }


    @RequestMapping("/country")
    @ResponseBody
    public RestResult countrySync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpCountrySyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp分类时出现异常", e);
            throw new RuntimeException("拉取老erp分类时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/zone")
    @ResponseBody
    public RestResult zoneSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpZoneSyncService.pull();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp区域时出现异常", e);
            throw new RuntimeException("拉取老erp区域时出现异常", e);
        }
        return result;
    }


    @RequestMapping("/stock")
    @ResponseBody
    public RestResult stockSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpQuantitySyncService.pullHasStock();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp库存时出现异常", e);
            throw new RuntimeException("拉取老erp库存时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/lastOrderAt")
    @ResponseBody
    public RestResult lastOrderAtSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = oldErpQuantitySyncService.pullHasLastOrderDate();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("拉取老erp最后下单时间时出现异常", e);
            throw new RuntimeException("拉取老erp最后下单时间时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/categoryAttribute")
    @ResponseBody
    public RestResult categoryAttributeSync() throws Exception {
        RestResult result = new RestResult();
        Future<Object> future = categoryAttributeRelSyncService.syncCategoryAttributeRel();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步品类属性关系时出现异常", e);
            throw new RuntimeException("同步品类关系时出现异常", e);
        }
        return result;
    }

    @RequestMapping("/createImageDesc")
    @ResponseBody
    public RestResult createImageDesc() {
        RestResult result = new RestResult();
    //    createMatchFileService.createAll(filePath);
        result.setDesc("生成图片比对文件成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @RequestMapping("/checkProductSync")
    @ResponseBody
    public RestResult checkProductSync() {
        RestResult result = new RestResult();
        Future<Object> future = oldErpCheckProductSyncService.pullAll();
        try {
            String object = (String) future.get();
            result.setCode(RestResult.NOTICE);
            result.setDesc(object);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步查重产品出现异常", e);
            throw new RuntimeException("同步查重产品出现异常", e);
        }
        return result;
    }

    /**
     * 产品推送失败,重新推送接口
     */
    @RequestMapping("/againPushProduct")
    @ResponseBody
    public RestResult againPushProduct(String productIds) {
        RestResult result = new RestResult();
        Assert.isTrue(productIds != null && !"".equals(productIds), "产品id参数有误,请重新输入!");
        String[] ids = productIds.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            try {
                productPushService.pushProductThing(Integer.valueOf(ids[i].trim()));
            } catch (Exception e) {
                list.add(ids[i]);
            }
        }
        if (list.size() == 0) {
            result.setCode(RestResult.NOTICE);
            result.setDesc("产品重新推送成功!");
        } else {
            result.setCode(RestResult.FAIL);
            result.setDesc("产品重新推送成功失败的产品ID为[" + list + "]!");
        }
        return result;
    }

    /**
     * 产品SKU推送失败,重新推送接口
     */
    @RequestMapping("/againPushProductSku")
    @ResponseBody
    public RestResult againPushProductSku(String productIds) {
        RestResult result = new RestResult();
        Assert.isTrue(productIds != null && !"".equals(productIds), "产品id参数有误,请重新输入!");
        String[] ids = productIds.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            try {
                productPushService.pushSkuThing(Integer.valueOf(ids[i].trim()));
            } catch (Exception e) {
                list.add(ids[i]);
            }
        }
        if (list.size() == 0) {
            result.setCode(RestResult.NOTICE);
            result.setDesc("产品SKU重新推送成功!");
        } else {
            result.setCode(RestResult.FAIL);
            result.setDesc("产品SKU重新推送成功失败的产品ID为[" + list + "]!");
        }
        return result;
    }

}  
