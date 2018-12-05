package com.stosz.product.sync.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.ImageDomain;
import com.stosz.olderp.ext.model.OldErpAttribute;
import com.stosz.olderp.ext.model.OldErpAttributeValue;
import com.stosz.olderp.ext.model.OldErpCheckProduct;
import com.stosz.olderp.ext.service.IOldErpAttributeService;
import com.stosz.olderp.ext.service.IOldErpAttributeValueService;
import com.stosz.olderp.ext.service.IOldErpCheckProductService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.ClassifyEnum;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.service.ProductNewService;
import com.stosz.product.service.ProductNewZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/11]
 */
@Service
public class OldErpCheckProductSyncService {

    @Resource
    private IOldErpCheckProductService iOldErpCheckProductService;
    @Resource
    private IOldErpAttributeService iOldErpAttributeService;
    @Resource
    private IOldErpAttributeValueService iOldErpAttributeValueService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private ProductNewService productNewService;
    @Resource
    private ProductNewZoneService productNewZoneService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullAll() {
        logger.info("=========================同步老erp查重产品开始===========================");
        List<OldErpCheckProduct> oldErpCheckProductList = iOldErpCheckProductService.findAll();
        List<ProductNew> productNewList = new ArrayList<>();
        List<ProductNewZone> productNewZoneList = new ArrayList<>();
        if (oldErpCheckProductList == null || oldErpCheckProductList.isEmpty()) {
            logger.info("没有要同步的查重产品！");
        } else {
            for (OldErpCheckProduct oldCheckProduct : oldErpCheckProductList) {
                Integer oldUserId = oldCheckProduct.getIdUsers();
                User user = iUserService.getByOldId(oldUserId);
                Integer oldDepartmentId = oldCheckProduct.getIdDepartment();
                ProductNew productNew = new ProductNew();
                if (user == null) {
                    productNew.setCreatorId(0);
                } else {
                    productNew.setCreatorId(MBox.getLoginUserId());
                }
                Integer departmentId = 0;
                String departmentNo = "";
                String departmentName = "未匹配";
                Department department = iDepartmentService.getByOldId(oldDepartmentId);
                if (department != null) {
                    departmentId = department.getId();
                    departmentNo = department.getDepartmentNo();
                    departmentName = department.getDepartmentName();
                }
                productNew.setTitle(oldCheckProduct.getTitle());
                Integer classify = oldCheckProduct.getIdClassify();
                productNew.setClassifyEnum(transClassify(classify));
                String thumbs = oldCheckProduct.getThumbs();
                String productUrl = getProductUrl(thumbs);
                productNew.setMainImageUrl(productUrl);
                productNew.setSourceUrl(oldCheckProduct.getSaleUrl());
                productNew.setPurchaseUrl(oldCheckProduct.getPurchaseUrl());
                productNew.setState(oldCheckProduct.getStatus() == 1 ? ProductNewState.waitAssign.name() : ProductNewState.draft.name());
                productNew.setStateTime(LocalDateTime.now());
                productNew.setMemo(oldCheckProduct.getDesc());
                productNew.setDepartmentId(departmentId);
                productNew.setDepartmentNo(departmentNo);
                productNew.setDepartmentName(departmentName);
                productNew.setId(oldCheckProduct.getId());
                productNew.setCategoryId(oldCheckProduct.getIdCategory());
                productNew.setCreator(oldCheckProduct.getUserName());
                productNew.setMemo(oldCheckProduct.getDesc());
                productNew.setCreateAt(oldCheckProduct.getCreatedAt());
                List<OldErpAttribute> oldErpAttributeList = iOldErpAttributeService.findByProductId(oldCheckProduct.getId());
                StringBuilder sb = new StringBuilder("");
                if (!oldErpAttributeList.isEmpty()) {
                    for (OldErpAttribute oldErpAttribute : oldErpAttributeList) {
                        List<OldErpAttributeValue> oldErpAttributeValueList = iOldErpAttributeValueService.findByAttributeId(oldCheckProduct.getId(), oldErpAttribute.getId());
                        sb.append(oldErpAttribute.getTitle()).append(":");
                        if (!oldErpAttributeValueList.isEmpty()) {
                            for (OldErpAttributeValue oldErpAttributeValue : oldErpAttributeValueList) {
                                sb.append(oldErpAttributeValue.getTitle()).append(" ");
                            }
                        }
                    }
                }
                String attributeDesc = sb.toString();
                productNew.setAttributeDesc(attributeDesc);
                productNewList.add(productNew);
                ProductNewZone productNewZone = new ProductNewZone();
                productNewZone.setProductNewId(oldCheckProduct.getId());
                productNewZone.setZoneId(oldCheckProduct.getIdZone());
                productNewZone.setCreateAt(oldCheckProduct.getCreatedAt());
                productNewZoneList.add(productNewZone);
            }
        }
        if (!productNewList.isEmpty()) {
            productNewService.insertBatch(productNewList);
        }
        if (!productNewZoneList.isEmpty()) {//老erp的查重表得到的新品区域表中应该是不存在，所以这里不用判断存在性？？？
            productNewZoneService.insertBatch(productNewZoneList);
        }
        logger.info("=========================同步老erp查重产品结束===========================");
        return new AsyncResult<>("同步老erp查重产品中待排重的产品成功！");


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
