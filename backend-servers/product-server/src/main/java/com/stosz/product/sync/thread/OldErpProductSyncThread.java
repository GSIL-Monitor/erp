package com.stosz.product.sync.thread;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.ImageDomain;
import com.stosz.olderp.ext.model.OldErpProduct;
import com.stosz.olderp.ext.model.OldErpProductZone;
import com.stosz.olderp.ext.service.IOldErpProductService;
import com.stosz.olderp.ext.service.IOldErpProductZoneDomainService;
import com.stosz.olderp.ext.service.IOldErpProductZoneService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.ClassifyEnum;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.*;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.product.service.CategoryService;
import com.stosz.product.service.ProductLangService;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductZoneDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
@Component
public class OldErpProductSyncThread {

    @Resource
    private IUserService iUserService;
    @Resource
    private ProductService productService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private IOldErpProductService iOldErpProductService;
    @Resource
    private IOldErpProductZoneService iOldErpProductZoneService;
    @Resource
    private IZoneService IZoneService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private IOldErpProductZoneDomainService iOldErpProductZoneDomainService;
    @Resource
    private ProductZoneDomainService productZoneDomainService;
    @Resource
    private ProductLangService productLangService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Async("productPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pullList(List<OldErpProduct> oldErpProductList) {
        Integer i = 0;
        List<Product> productList = new ArrayList<>();
        List<ProductLang> productLangList = new ArrayList<>();
        for (OldErpProduct oldErpProduct : oldErpProductList) {
            Product product = new Product();
            Integer productId = oldErpProduct.getId();
            Integer oldUserId = oldErpProduct.getUserId();
            User user = iUserService.getByOldId(oldUserId);
            product.setCreator(oldErpProduct.getUserName());
            if (user == null) {
                product.setCreatorId(0);
            } else {
                product.setCreatorId(MBox.getLoginUserId());
            }
            product.setId(productId);
            product.setCreateAt(oldErpProduct.getCreatedAt() == null ? LocalDateTime.now() : oldErpProduct.getCreatedAt());
            product.setUpdateAt(oldErpProduct.getUpdatedAt() == null ? LocalDateTime.now() : oldErpProduct.getUpdatedAt());
            Integer categoryId = oldErpProduct.getCategoryId();

            List<OldErpProduct> checks = iOldErpProductService.findCheckById(productId);
            if (checks == null || checks.isEmpty()) {
                categoryId = categoryService.getNewCategoryById(oldErpProduct.getCategoryId());
                if (categoryId == null) {
                    logger.error("=======================>产品id：" + productId + "===========>cateogoryId : " + oldErpProduct.getCategoryId());
                    categoryId = oldErpProduct.getCategoryId();
                }
            } else {
                OldErpProduct checkProduct = checks.get(0);
                product.setPurchaseUrl(checkProduct.getPurchaseUrl());
                product.setSourceUrl(checkProduct.getSaleUrl());
                Category category = categoryService.getById(categoryId);
                if (category == null) {
                    categoryId = categoryService.getNewCategoryById(oldErpProduct.getCategoryId());
                    if (categoryId == null) {
                        logger.error("=======================>产品id：" + productId + "===========>cateogoryId : " + oldErpProduct.getCategoryId());
                        categoryId = oldErpProduct.getCategoryId();
                    }
                }
            }
            product.setCategoryId(categoryId);
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
            productList.add(product);
            String lang = oldErpProduct.getForeignTitle();
            if (StringUtils.isNotBlank(lang)) {
                ProductLang productLang = new ProductLang();
                productLang.setProductId(oldErpProduct.getId());
                productLang.setName(lang);
                productLang.setLangCode("olderp");
                productLangList.add(productLang);
            }
            i++;
        }
        productService.insertList(productList);
        if (!productLangList.isEmpty()) {
            productLangService.insertList(productLangList);
        }
        return new AsyncResult<>(i);
    }

    @Async("productPool")
    public Future<Integer> push(List<Product> productList) {
        Integer i = 0;
        for (Product product : productList) {
            OldErpProduct oldErpProduct = new OldErpProduct();
            oldErpProduct.setCategoryId(product.getCategoryId());
            oldErpProduct.setClassify(product.getClassifyEnum().ordinal());
            Integer departmentId = oldErpProduct.getDepartmentId();
            Integer oldDepartmentId = 0;
            if (departmentId != 0) {
                oldDepartmentId = iDepartmentService.getOldIdById(departmentId);
            }
            oldErpProduct.setDepartmentId(oldDepartmentId);
            Integer userId = oldErpProduct.getUserId();
            Integer oldUserId = 0;
            if (userId != 0) {
                User user = iUserService.getById(userId);
                oldUserId = user.getOldId();
            }
            oldErpProduct.setUserId(oldUserId);
            oldErpProduct.setDesc(product.getMemo());
            oldErpProduct.setHeight(product.getHeight());
            oldErpProduct.setInnerName(product.getInnerName());
            oldErpProduct.setLength(product.getLongness());
            oldErpProduct.setPurchasePrice(product.getPurchasePrice());
            oldErpProduct.setModel(product.getSpu());
            oldErpProduct.setStatus(Boolean.parseBoolean(product.getState()));
            oldErpProduct.setThumbs(product.getMainImageUrl());
            oldErpProduct.setTitle(product.getTitle());
            oldErpProduct.setWeight(product.getWeight());
            iOldErpProductService.insert(oldErpProduct);
            i++;
        }
        return new AsyncResult<>(i);
    }

    private void insertOrUpdate(Product product, OldErpProduct oldErpProduct) {
        product.setId(oldErpProduct.getId());
        product.setCreateAt(oldErpProduct.getCreatedAt() == null ? LocalDateTime.now() : oldErpProduct.getCreatedAt());
        product.setUpdateAt(oldErpProduct.getUpdatedAt() == null ? LocalDateTime.now() : oldErpProduct.getUpdatedAt());
        product.setCategoryId(oldErpProduct.getCategoryId());
        product.setState(oldErpProduct.getInnerName() == null ? ProductState.archiving.name() : ProductState.onsale.name());
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
        try {
            productService.insertOld(product);
        } catch (DuplicateKeyException e) {
            Product productDB = productService.getByIdSync(product.getId());
            product.setStateTime(productDB.getStateTime());
            product.setSourceUrl(productDB.getSourceUrl());
            product.setSourceEnum(productDB.getSourceEnum());
            product.setChecker(productDB.getChecker());
            product.setCheckerId(productDB.getCheckerId());
            product.setProductNewId(productDB.getProductNewId());
            productService.updateOld(product);
            logger.info("产品同步时，将{}更新为{} 成功！", productDB, product);
        }


    }

    private Product pullProductZone(Product product) {
        Integer productId = product.getId();
        Integer totalStock = 0;
        List<OldErpProductZone> oldErpProductZoneList = iOldErpProductZoneService.findByProductId(productId);
        if (oldErpProductZoneList == null || oldErpProductZoneList.isEmpty()) {
            return product;
        }
        for (OldErpProductZone oldErpProductZone : oldErpProductZoneList) {
            ProductZone productZone = new ProductZone();
            productZone.setProductId(productId);
            productZone.setStateTime(LocalDateTime.now());
            productZone.setCreatorId(0);
            productZone.setCreator("系统");
            Integer oldDepartmentId = oldErpProductZone.getDepartmentId();
            Integer oldZoneId = oldErpProductZone.getZoneId();
            productZone.setState(product.getInnerName() == null ? ProductZoneState.archiving.name() : ProductZoneState.onsale.name());
            Department department = iDepartmentService.getByOldId(oldDepartmentId);
            if (department == null) {
                productZone.setDepartmentName("未匹配");
                productZone.setDepartmentNo("00");
                productZone.setDepartmentId(0);
            } else {
                productZone.setDepartmentName(department.getDepartmentName());
                productZone.setDepartmentId(department.getId());
                productZone.setDepartmentNo(department.getDepartmentNo());
            }
            Zone zone = IZoneService.getCacheById(oldZoneId);
            if (zone == null) {
                productZone.setZoneName("未匹配");
                productZone.setZoneCode("--");
                productZone.setCurrencyCode("--");
            } else {
                productZone.setCurrencyCode(zone.getCurrency());
                productZone.setZoneCode(zone.getCode());
                productZone.setZoneName(zone.getTitle());
            }
            productZone.setCreateAt(oldErpProductZone.getCreatedAt());
            //计算产品区域的状态
            ProductZoneState productZoneState = getState(productZone, product);
            productZone.setState(productZoneState.name());
            //只要有产品区域的状态不是销档，那么该产品不能销档，如果所有状态都是销档，那么产品也要销档
        }
        product.setTotalStock(totalStock);
        return product;
    }

    private ProductZoneState getState(ProductZone productZone, Product product) {
        ProductZoneState state = product.getInnerName() == null ? ProductZoneState.archiving : ProductZoneState.onsale;
        LocalDateTime createdAt = productZone.getCreateAt();
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        long difference = duration.toDays();
        LocalDateTime lastOrderAt = productZone.getLastOrderAt();
        Boolean archivedToCancel = ProductZoneState.onsale.equals(state) && difference >= 7 && lastOrderAt == null;
        Boolean archivingToCancel = ProductZoneState.archiving.equals(state) && difference >= 3;
        if (archivedToCancel || archivingToCancel) {
            state = ProductZoneState.disappeared;
        }
        return state;
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
                                url = value.substring(value.indexOf("/upload/") + 8, value.length());
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
