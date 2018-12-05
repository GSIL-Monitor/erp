package com.stosz.product.sync.thread;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.ImageDomain;
import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.olderp.ext.model.OldErpProductZone;
import com.stosz.olderp.ext.service.IOldErpProductZoneService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.ClassifyEnum;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/20]
 */
@Component
public class OldErpProductRelevantSyncThread {
    @Resource
    private ProductService productService;
    @Resource
    private IOldErpProductZoneService iOldErpProductZoneService;
    @Resource
    private IUserService iUserService;
    @Resource
    private OldErpAttributeSyncThread oldErpAttributeSyncThread;
    @Resource
    private OldErpProductSkuSyncThread oldErpProductSkuSyncThread;
    @Resource
    private OldErpProductZoneSyncThread oldErpProductZoneSyncThread;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Async("productPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> syncOne(OldErpProduct oldErpProduct) throws ExecutionException, InterruptedException {
        Integer oldErpProductId = oldErpProduct.getId();
        Integer categoryId = oldErpProduct.getCategoryId();
        pullProduct(oldErpProduct);
        //同步属性相关
        Future<Integer> future = oldErpAttributeSyncThread.pullAttribute(oldErpProductId, categoryId);
        //同步产品，产品区域相关
        Integer attributeCount = future.get();
        logger.info("同步产品id为{}的属性信息成功，记录数{}", oldErpProductId, attributeCount);
        //同步SKU相关
        Future<Integer> future1 = oldErpProductSkuSyncThread.pullSku(oldErpProductId);
        Integer skuCount = future1.get();
        logger.info("同步产品id为{}的SKU成功，记录数{}", oldErpProductId, skuCount);
        return new AsyncResult<>(oldErpProductId);
    }

    private void pullProduct(OldErpProduct oldErpProduct) {
        Product product = new Product();
        Integer oldUserId = oldErpProduct.getUserId();
        User user = iUserService.getByOldId(oldUserId);
        if (user == null) {
            product.setCreator("系统");
            product.setCreatorId(0);
        } else {
            product.setCreator(MBox.getLoginUserName());
            product.setCreatorId(MBox.getLoginUserId());

        }
        product.setId(oldErpProduct.getId());
        product.setCreateAt(oldErpProduct.getCreatedAt() == null ? LocalDateTime.now() : oldErpProduct.getCreatedAt());
        product.setUpdateAt(oldErpProduct.getUpdatedAt() == null ? LocalDateTime.now() : oldErpProduct.getUpdatedAt());
        product.setCategoryId(oldErpProduct.getCategoryId());
        product.setState(ProductState.disappeared.name());
        product.setTitle(oldErpProduct.getTitle());
        product.setPurchasePrice(oldErpProduct.getPurchasePrice());
        product.setInnerName(oldErpProduct.getInnerName());
        Integer classify = oldErpProduct.getClassify();
        product.setClassifyEnum(transClassify(classify));
        product.setHeight(oldErpProduct.getHeight());
        product.setLongness(oldErpProduct.getLength());
        product.setWeight(oldErpProduct.getWeight());
        product.setWidth(oldErpProduct.getWidth());
        String thumbs = oldErpProduct.getThumbs();
        String productUrl = getProductUrl(thumbs);
        product.setMainImageUrl(productUrl);
        if (oldErpProduct.getDesc() == null) {
            product.setMemo("");
        } else {
            product.setMemo(oldErpProduct.getDesc());
        }
        product.setSpu(oldErpProduct.getModel());
        product.setStateTime(LocalDateTime.now());
        product.setChecker("系统");
        product.setCheckerId(0);
        product = pullProductZone(product);
        productService.insertOld(product);
    }

    private Product pullProductZone(Product product) {
        Integer productId = product.getId();
        List<OldErpProductZone> oldErpProductZoneList = iOldErpProductZoneService.findByProductId(productId);
        if (oldErpProductZoneList == null || oldErpProductZoneList.isEmpty()) {
            return product;
        }
        product = oldErpProductZoneSyncThread.pullProductZone(oldErpProductZoneList, product);
        return product;
    }


    private ClassifyEnum transClassify(Integer classify) {
        ClassifyEnum classifyEnum = null;
        switch (classify) {
            case 0:
                classifyEnum = ClassifyEnum.S;
                break;
            case 1:
                classifyEnum = ClassifyEnum.D;
                break;
            case 2:
                classifyEnum = ClassifyEnum.Y;
                break;
            case 3:
                classifyEnum = ClassifyEnum.S;
                break;
            default:
                classifyEnum = ClassifyEnum.S;
                break;
        }
        return classifyEnum;
    }

    private String getProductUrl(String thumbs) {
        String url = "";
        if (StringUtils.isNotBlank(thumbs)) {
            ImageDomain imageDomain = JsonUtil.readValue(thumbs, ImageDomain.class);
            if (imageDomain != null) {
                List<Map<String, String>> list = imageDomain.getPhoto();
                if (list != null && !list.isEmpty()) {
                    for (Map<String, String> map : list) {
                        String value = map.get("url");
                        if (StringUtils.isNotBlank(value)) {
                            if (value.contains("/data/upload")) {
                                url = value.substring(value.indexOf("/upload") + 7, value.length());
                            } else {
                                url = value;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return url;
    }
}  
