package com.stosz.product.deamon;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.*;
import com.stosz.olderp.ext.service.*;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.product.ext.model.*;
import com.stosz.product.service.*;
import com.stosz.product.sync.service.OldErpCategorySyncService;
import com.stosz.product.sync.service.OldErpZoneSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/13]
 */
@Service
public class ProductPushService {

    @Resource
    private ProductService productService;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private IOldErpCategoryService iOldErpCategoryService;
    @Resource
    private OldErpCategorySyncService oldErpCategorySyncService;
    @Resource
    private ZoneService zoneService;
    @Resource
    private IOldErpProductZoneService iOldErpProductZoneService;
    @Resource
    private IOldErpZoneService iOldErpZoneService;
    @Resource
    private OldErpZoneSyncService oldErpZoneSyncService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IOldErpProductService iOldErpProductService;
    @Resource
    private ProductAttributeRelService productAttributeRelService;
    @Resource
    private IOldErpAttributeService iOldErpAttributeService;
    @Resource
    private ProductAttributeValueRelService productAttributeValueRelService;
    @Resource
    private IOldErpAttributeValueService iOldErpAttributeValueService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
    @Resource
    private IOldErpProductSkuService iOldErpProductSkuService;
    @Resource
    private ProductLangService productLangService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private CategoryService categoryService;

    @Value("${image.host}")
    private String httpPrefix;

    private final Logger logger = LoggerFactory.getLogger(getClass());



//    @Transactional(value = "oldErpTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pushProductThing(Integer productId) {
        pushProduct(productId);
        pushProductZone(productId);

    }


//    @Transactional(value = "oldErpTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pushSkuThing(Integer productId) {
        pushProductAttribute(productId);
        pushProductAttributeValue(productId);
        try {
            pushProductSku(productId);
        } catch (Exception e) {
            logger.error("推送产品id为{}的产品sku失败！",productId,e);
            throw new RuntimeException("推送产品id为"+productId+"的产品sku失败！",e);
        }
    }

    private void pushProduct(Integer productId) {
        logger.info("=====================开始推送产品{}到老erp================", productId);
        Product product = productService.getById(productId);
        ProductLang productLang = productLangService.getLastByProductId(productId);
        Assert.notNull(product, "数据库中找不到要push的产品！");
        logger.info("push产品{}开始！", product);
        Integer categoryId = product.getCategoryId();
        OldErpCategory oldErpCategory = iOldErpCategoryService.getById(categoryId);
        //老erp中没有查询到该品类，说明品类还没有同步过去，需要调用品类的推送
        if (oldErpCategory == null) {
            logger.info("老erp中没有该产品对应的分类信息，push一次品类到老erp");
            oldErpCategorySyncService.push();
        }
        OldErpProduct oldErpProduct = new OldErpProduct();
        // 如果存在最新的产品语言包，则需要将其设置为外文名称
        if (productLang != null) {
            oldErpProduct.setForeignTitle(productLang.getName());
        }
        oldErpProduct.setCategoryId(product.getCategoryId());
        //产品类别枚举的ordinal与老erp中的表对应关系需要加1
        oldErpProduct.setClassify(product.getClassifyEnum().ordinal() + 1);
        Integer oldDepartmentId = 0;
        Integer userId = product.getCreatorId();
        Integer oldUserId = 0;
        if (userId != 0) {
            User user = iUserService.getById(userId);
            Integer userOldId = user.getOldId();
            Integer departmentId = user.getDepartmentId();
            if (userOldId != null) {
                oldUserId = user.getOldId();
//                oldDepartmentId = departmentService.getOldIdById(departmentId);
            } else {
                logger.error("推送产品{}到老erp时，无法找到其对应的老erp用户！", product);
            }
        }
        List<ProductZone> pzList = productZoneService.findByProductId(productId);
        if (pzList != null && pzList.size() > 0) {
            Department department = iDepartmentService.get(pzList.get(0).getDepartmentId());
            if (department != null) {
                oldDepartmentId = department.getOldId();
            }
        }
        Category category = categoryService.getTopCategory(product.getCategoryId());
        oldErpProduct.setId(productId);
        oldErpProduct.setDepartmentId(oldDepartmentId);
        oldErpProduct.setUserId(oldUserId);
        oldErpProduct.setDesc(product.getMemo());
        oldErpProduct.setHeight(product.getHeight() == null ? BigDecimal.valueOf(0.00) : product.getHeight());
        oldErpProduct.setWeight(product.getWeight() == null ? BigDecimal.valueOf(0.00) : product.getWeight());
        oldErpProduct.setWidth(product.getWidth() == null ? BigDecimal.valueOf(0.00) : product.getWidth());
        oldErpProduct.setLength(product.getLongness() == null ? BigDecimal.valueOf(0.00) : product.getLongness());
        oldErpProduct.setInnerName(product.getInnerName());
        oldErpProduct.setPurchaseUrl(product.getSourceUrl());
        oldErpProduct.setPurchasePrice(product.getPurchasePrice() == null ? BigDecimal.valueOf(0.00) : product.getPurchasePrice());
        oldErpProduct.setModel(product.getSpu());
        oldErpProduct.setStatus(true);
        oldErpProduct.setCreatedAt(LocalDateTime.now());
        oldErpProduct.setUpdatedAt(LocalDateTime.now());
        oldErpProduct.setEnCategory(product.getEnCategory());
        if (category != null) {
            oldErpProduct.setTopCategory(category.getName());
            oldErpProduct.setTopCatagoryId(category.getId());
        } else {
            oldErpProduct.setTopCatagoryId(0);
        }
        ImageDomain imageDomain = new ImageDomain();
        List<Map<String, String>> photo = new ArrayList<>();
        Map<String, String> urlMap = new HashMap<>();
        //需要将图片url转换为实体，转json，同时需要将url变为绝对路径
        urlMap.put("url", httpPrefix + product.getMainImageUrl());
        photo.add(urlMap);
        imageDomain.setPhoto(photo);
        String thumbs = JsonUtil.toJson(imageDomain);
        oldErpProduct.setThumbs(thumbs);
        oldErpProduct.setTitle(product.getTitle());
        OldErpProduct oldErpProductDB = iOldErpProductService.getById(productId);
        //查询数据库中是否存在，有则更新，没有则插入
        if (oldErpProductDB == null) {
            iOldErpProductService.insert(oldErpProduct);
        } else {
            iOldErpProductService.update(oldErpProduct);
        }
        logger.info("=====================推送产品{}到老erp完成================", product);
    }

    private void pushProductZone(Integer productId) {
        List<ProductZone> productZoneList = productZoneService.findByProductId(productId);
        if (productZoneList == null || productZoneList.size() == 0) {
            logger.info("该产品没有要推送的产品区域信息！");
            return;
        }
        logger.info("产品区域信息同步push开始，同步集合{}", productZoneList);
        for (ProductZone productZone : productZoneList) {
            logger.info("=====================推送产品区域{}到老erp开始================", productZone);
            Integer departmentId = productZone.getDepartmentId();
            Integer oldDepartmentId = iDepartmentService.getOldIdById(departmentId);
            Assert.notNull(oldDepartmentId, "产品区域【" + productZone + "】在老erp找不到对应的部门");
            String zoneCode = productZone.getZoneCode();
            Zone zone = zoneService.getByCode(zoneCode);
            Assert.notNull(zone, "产品区域【" + productZone + "】对应的区域信息为空！");
            Integer zoneId = zone.getId();
            OldErpZone oldErpZone = iOldErpZoneService.getById(zoneId);
            //如果老erp找不到对应的区域，说明区域信息没有推送，调用区域的push方法
            if (oldErpZone == null) {
                oldErpZoneSyncService.push();
            }
            OldErpProductZone oldErpProductZone = new OldErpProductZone();
            oldErpProductZone.setId(productZone.getId());
            oldErpProductZone.setDepartmentId(oldDepartmentId);
            oldErpProductZone.setProductId(productZone.getProductId());
            oldErpProductZone.setZoneId(zoneId);
            oldErpProductZone.setStatus(Boolean.parseBoolean(productZone.getState()));
            iOldErpProductZoneService.insert(oldErpProductZone);
            logger.info("=====================推送产品区域{}到老erp结束================", productZone);
        }
        logger.info("产品区域信息同步push结束！");
    }

    private void pushProductAttribute(Integer productId) {
        List<ProductAttributeRel> productAttributeRelList = productAttributeRelService.findByProductId(productId);
        if (productAttributeRelList == null || productAttributeRelList.isEmpty()) {
            logger.info("产品id为【{}】没有对应的产品属性需要推送！", productId);
            return;
        }
        logger.info("产品id为【{}】的产品属性集合{}开始推送到老erp！", productId, productAttributeRelList);
        for (ProductAttributeRel productAttributeRel : productAttributeRelList) {
            OldErpAttribute oldErpAttribute = new OldErpAttribute();
            String title = productAttributeRel.getAttributeTitle();
            oldErpAttribute.setId(productAttributeRel.getId());
            oldErpAttribute.setTitle(title);
            oldErpAttribute.setIdProduct(productId);
            int count = iOldErpAttributeService.countByTitle(productId, title);
            if (count == 0) {
                iOldErpAttributeService.insert(oldErpAttribute);
            }
        }
        logger.info("产品id为【{}】的产品属性集合{} 推送到老erp完毕！", productId, productAttributeRelList);
    }

    private void pushProductAttributeValue(Integer productId) {
        List<ProductAttributeValueRel> productAttributeValueRelList = productAttributeValueRelService.findByProductId(productId);
        if (productAttributeValueRelList == null || productAttributeValueRelList.isEmpty()) {
            logger.info("产品id为【{}】的产品下没有对应的产品属性值需要推送！", productId);
            return;
        }
        logger.info("id为【{}】的产品对应的产品属性值集合{}推送到老erp开始！", productId, productAttributeValueRelList);
        for (ProductAttributeValueRel productAttributeValueRel : productAttributeValueRelList) {
            String optionTitle = productAttributeValueRel.getOptionTitle();
            String title = productAttributeValueRel.getTitle();
            OldErpAttribute oldErpAttribute = iOldErpAttributeService.getByUnique(productId, optionTitle);
            if (oldErpAttribute == null) {
                logger.info("老erp找不到与{}属性值匹配的属性，放弃该条记录！", productAttributeValueRel);
                continue;
            }
            OldErpAttributeValue oldErpAttributeValue = new OldErpAttributeValue();
            oldErpAttributeValue.setId(productAttributeValueRel.getId());
            oldErpAttributeValue.setProductId(productId);
            oldErpAttributeValue.setOptionId(oldErpAttribute.getId());
            oldErpAttributeValue.setTitle(title);
            OldErpAttributeValue oldErpAttributeValueDB = iOldErpAttributeValueService.getByUnique(productId, oldErpAttribute.getId(), title);
            if (oldErpAttributeValueDB == null) {
                iOldErpAttributeValueService.insert(oldErpAttributeValue);
            }
            logger.info("push属性值{}到老erp成功！", oldErpAttributeValue);
        }
    }

    private void pushProductSku(Integer productId) {
        List<ProductSku> productSkuList = productSkuService.findByProductId(productId);
        if (productSkuList == null || productSkuList.isEmpty()) {
            logger.info("产品id为【{}】的产品下没有对应的产品SKU需要推送！");
            return;
        }
        for (ProductSku productSku : productSkuList) {
            String sku = productSku.getSku();
            OldErpProductSku oldErpProductSku = new OldErpProductSku();
            oldErpProductSku.setIdProduct(productId);
            oldErpProductSku.setSku(sku);
            oldErpProductSku.setModel(productSku.getSpu());
            Assert.notNull(sku, "产品sku不能为空！");
            List<ProductSkuAttributeValueRel> attributeTitles = productSkuAttributeValueRelService.findBySku(sku);
            StringBuilder sb = new StringBuilder();
            StringBuilder titleSb = new StringBuilder();
            List<Integer> idList = new LinkedList<>();
            if (attributeTitles != null && !attributeTitles.isEmpty()) {
                for (ProductSkuAttributeValueRel productSkuAttributeValueRel : attributeTitles) {
                    String attributeTitle = productSkuAttributeValueRel.getAttributeTitle();
                    String attributeValueTitle = productSkuAttributeValueRel.getAttributeValueTitle();
                    titleSb = titleSb.append(attributeValueTitle).append("-");
                    OldErpAttribute oldErpAttribute = null;
                    try {
                        oldErpAttribute = iOldErpAttributeService.getByUnique(productId, attributeTitle);
                    } catch (Exception e) {
                        logger.error("查询老erp属性出错，出现多个，参数产品id{},属性title{},sku:{}",productId,attributeTitle,sku);
                        throw new RuntimeException(e);
                    }
                    if (oldErpAttribute != null) {
                        OldErpAttributeValue oldErpAttributeValue = null;
                        try {
                            oldErpAttributeValue = iOldErpAttributeValueService.getByUnique(productId, oldErpAttribute.getId(), attributeValueTitle);
                        } catch (Exception e) {
                            logger.error("查询老erp属性值出错，出现多个，参数产品id{},属性id{},属性值title{}，sku:{}",productId, oldErpAttribute.getId(), attributeValueTitle,sku);
                            throw new RuntimeException(e);
                        }
                        if (oldErpAttributeValue != null) {
                            idList.add(oldErpAttributeValue.getId());
                        }
                    }
                }
            }
            if (!idList.isEmpty()) {
                Collections.sort(idList);
                for (Integer id : idList) {
                    sb.append(id).append(",");
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                oldErpProductSku.setOptionValue(sb.toString());
            }
            if (titleSb.length() > 0) {
                titleSb.deleteCharAt(titleSb.length() - 1);
                oldErpProductSku.setTitle(titleSb.toString());
            }
            OldErpProductSku oldErpProductSkuDB = null;
            try {
                oldErpProductSkuDB = iOldErpProductSkuService.getBySku(sku);
            } catch (Exception e) {
                logger.error("查询老erp属性值出错，出现多个，sku:{}",sku);
                throw new RuntimeException(e);
            }
            if (oldErpProductSkuDB == null) {
                iOldErpProductSkuService.insert(oldErpProductSku);
            } else {
                oldErpProductSku.setIdProductSku(oldErpProductSkuDB.getIdProductSku());
                iOldErpProductSkuService.update(oldErpProductSku);
            }
        }
    }
}  
