package com.stosz.product.sync.thread;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.olderp.ext.model.OldErpProductZone;
import com.stosz.olderp.ext.model.OldErpProductZoneDomain;
import com.stosz.olderp.ext.service.IOldErpProductZoneService;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.ext.model.ProductZoneDomain;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductZoneDomainService;
import com.stosz.product.service.ProductZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
@Component
public class OldErpProductZoneSyncThread {

    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private IZoneService IZoneService;
    @Resource
    private IOldErpProductZoneService iOldErpProductZoneService;
    @Resource
    private ProductService productService;
    @Resource
    private ProductZoneDomainService productZoneDomainService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async("productZonePool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pull(List<OldErpProductZone> oldErpProductZones) {
        Integer success = 0;
        List<ProductZone> productZoneList = new ArrayList<>();
        for (OldErpProductZone oldErpProductZone : oldErpProductZones) {
            ProductZone productZone = new ProductZone();
            productZone.setId(oldErpProductZone.getId());
            productZone.setProductId(oldErpProductZone.getProductId());
            productZone.setState(ProductZoneState.disappeared.name());
            productZone.setStateTime(LocalDateTime.now());
            productZone.setCreatorId(0);
            productZone.setCreator("系统");
            Integer oldDepartmentId = oldErpProductZone.getDepartmentId();

            if (oldDepartmentId == null) {
                productZone.setDepartmentId(null);
                productZone.setDepartmentName("");
                productZone.setDepartmentNo("");
            } else {
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
            }
            Integer oldZoneId = oldErpProductZone.getZoneId();
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
            productZone.setCreateAt(oldErpProductZone.getCreatedAt() == null ? LocalDateTime.now() : oldErpProductZone.getCreatedAt());
            productZoneList.add(productZone);
            success++;
        }
        if (!productZoneList.isEmpty()) {
            productZoneService.insertList(productZoneList);
        }
        return new AsyncResult<>(success);

    }

    @Async("syncPool")
    public Future<Integer> push(List<ProductZone> productZoneList) {
        Integer success = 0;
        for (ProductZone productZone : productZoneList) {
            OldErpProductZone oldErpProductZone = new OldErpProductZone();
            Integer departmentId = productZone.getDepartmentId();
            Integer oldId = iDepartmentService.getOldIdById(departmentId);
            oldErpProductZone.setDepartmentId(oldId);
            oldErpProductZone.setProductId(productZone.getProductId());
            oldErpProductZone.setZoneId(productZone.getZoneId());
            oldErpProductZone.setStatus(Boolean.parseBoolean(productZone.getState()));
            iOldErpProductZoneService.insert(oldErpProductZone);
            success++;
        }
        return new AsyncResult<>(success);
    }

    @Async("productZonePool")
    public Product pullProductZone(List<OldErpProductZone> oldErpProductZoneList, Product product) {
        String innerName = product.getInnerName();
        String productState = product.getState();
        List<ProductZone> productZoneList = new ArrayList<>();
        for (OldErpProductZone oldErpProductZone : oldErpProductZoneList) {
            ProductZone productZone = new ProductZone();
            productZone.setProductId(oldErpProductZone.getProductId());
            productZone.setState(ProductZoneState.disappeared.name());
            productZone.setStateTime(LocalDateTime.now());
            productZone.setCreatorId(0);
            productZone.setCreator("系统");
            Integer oldDepartmentId = oldErpProductZone.getDepartmentId();
            Integer oldZoneId = oldErpProductZone.getZoneId();
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
            productZoneList.add(productZone);

        }
        if (!productZoneList.isEmpty()) {
            productZoneService.insertList(productZoneList);
        }

        return product;
    }


    @Async("productZonePool")
    public Future<Integer> pullProductZoneDomain(List<OldErpProductZoneDomain> oldErpProductZoneDomainList) {
        List<ProductZoneDomain> productZoneDomainList = new ArrayList<>();
        List<Integer> productIdList = new ArrayList<>();
        List<Integer> productZoneIdList = new ArrayList<>();
        int success = 0;
        for (OldErpProductZoneDomain oldErpProductZoneDomain : oldErpProductZoneDomainList) {
            ProductZoneDomain productZoneDomain = new ProductZoneDomain();
            Integer productId = oldErpProductZoneDomain.getProductId();
            Integer zoneId = oldErpProductZoneDomain.getZoneId();
            if (zoneId == null) {
                continue;
            }
            String innerName = oldErpProductZoneDomain.getInnerName();
            String domain = oldErpProductZoneDomain.getDomain();
            Zone zone = IZoneService.getCacheById(zoneId);
            Assert.notNull(zone, "同步产品区域域名时，数据库中对应的区域找不到记录");
            Integer oldDepartmentId = oldErpProductZoneDomain.getDepartmentId();
            Integer departmentId = 0;
            Department department = iDepartmentService.getByOldId(oldDepartmentId);
            if (department != null) {
                departmentId = department.getId();
            }
            productZoneDomain.setZoneId(zoneId);
            productZoneDomain.setProductId(productId);
            productZoneDomain.setDepartmentId(departmentId);
            productZoneDomain.setWebDirectory(oldErpProductZoneDomain.getWebDirectory());
            productZoneDomain.setDomain(oldErpProductZoneDomain.getDomain());
            productZoneDomain.setLoginid(oldErpProductZoneDomain.getLoginid());
            productZoneDomain.setSeoLoginid("");
            productZoneDomainList.add(productZoneDomain);
            ProductZone productZone = productZoneService.getByUnique(zone.getCode(), productId, departmentId);
            if (productZone == null) {
                continue;
            } else {
                LocalDateTime zoneCreateAt = productZone.getCreateAt();
                Duration duration = Duration.between(zoneCreateAt, LocalDateTime.now());
                long difference = duration.toDays();
                if (domain == null || StringUtils.isNotBlank(innerName)) {
                    if (difference < 3) {
                        productIdList.add(productId);
                        productZoneIdList.add(productZone.getId());
                    }
                }
            }
            success++;
        }
        if (!productZoneDomainList.isEmpty()) {
            productZoneDomainService.insertList(productZoneDomainList);
        }
        if (!productIdList.isEmpty()) {
            productService.updateStateByIds(productIdList, ProductState.archiving.name());
        }
        if (!productZoneIdList.isEmpty()) {
            productZoneService.updateState(productZoneIdList, ProductZoneState.archiving.name());
        }
        return new AsyncResult<>(success);
    }


}  
