package com.stosz.product.sync.thread;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.olderp.ext.model.OldErpStock;
import com.stosz.olderp.ext.service.IOldErpStockService;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.product.service.ProductService;
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
 * @version [1.0 , 2017/9/8]
 */
@Component
public class OldErpQuantitySyncThread {

    @Resource
    private ProductService productService;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private IZoneService IZoneService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private IOldErpStockService iOldErpStockService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Async("quantityPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pullStock(List<ProductZone> productZoneList) {
        Integer i = 0;
        for (ProductZone productZone : productZoneList) {
            Integer departmentId = productZone.getDepartmentId();
            if (departmentId == 0) {
                continue;
            }
            Integer oldDepartmentId = iDepartmentService.getOldIdById(departmentId);
            Integer productId = productZone.getProductId();
            Integer zoneId = productZone.getZoneId();
            Integer quantity = iOldErpStockService.getStockByUnique(productId, oldDepartmentId, zoneId);
            if (quantity != null) {
                productZone.setStock(quantity);
                productZoneService.updateStock(productZone);
            }
            i++;
        }
        return new AsyncResult<>(i);
    }



    @Async("hasStockPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pullHasStock(List<OldErpStock> oldErpStockList) {
        Integer success = 0;
        for (OldErpStock oldStock : oldErpStockList) {
            Integer productId = oldStock.getProductId();
            Integer oldDepartmentId = oldStock.getDepartmentId();
            Integer departmentId = 0;
            Integer oldZoneId = oldStock.getZoneId();
            Integer stock = oldStock.getStock() == null ? 0 : oldStock.getStock();
            Integer qtyRoad = oldStock.getQtyRoad() == null ? 0 : oldStock.getQtyRoad();
            Integer qtyPreout = oldStock.getQtyPreout() == null ? 0 : oldStock.getQtyPreout();
            Department department = iDepartmentService.getByOldId(oldDepartmentId);
            if (department != null) {
                departmentId = department.getId();
            }
            Zone zone = IZoneService.getCacheById(oldZoneId);
            if (zone == null) {
                logger.info("zoneId=================>{}", oldZoneId);
            }
            Assert.notNull(zone, "同步库存时，没有找到对应的区域信息！");
            ProductZone productZone = productZoneService.getByUnique(zone.getCode(), productId, departmentId);
            if (productZone == null) {
                logger.error("库存信息{}在数据库中找不到对应的产品区域！", oldStock);
            } else {
                String state = productZone.getState();
                productZone.setStock(stock);
                productZone.setQtyPreout(qtyPreout);
                productZone.setQtyRoad(qtyRoad);
                if (ProductZoneState.disappeared.name().equals(state)) {
                    productZone.setState(ProductZoneState.offsale.name());
                    productZone.setStateTime(LocalDateTime.now());
                }
                productZoneService.updateStock(productZone);
            }
            Product product = productService.getByIdSync(productId);
            if (product == null) {
                continue;
            }
            String productState = product.getState();
            if (ProductState.disappeared.name().equals(productState)) {
                product.setState(ProductState.offsale.name());
                product.setStateTime(LocalDateTime.now());
            }
            product.setTotalStock(product.getTotalStock() + stock + qtyRoad);
            productService.updateTotalStock(product);
            success++;
        }
        return new AsyncResult<>(success);
    }

    @Async("hasStockPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pullStockByDate(List<OldErpStock> oldErpStockList) {
        Integer success = 0;
        for (OldErpStock oldStock : oldErpStockList) {
            Integer productId = oldStock.getProductId();
            Integer oldDepartmentId = oldStock.getProductId();
            Integer departmentId = 0;
            Integer oldZoneId = oldStock.getZoneId();
            Integer stock = oldStock.getStock();
            Integer stockDifference = 0;
            Department department = iDepartmentService.getByOldId(oldDepartmentId);
            if (department != null) {
                departmentId = department.getId();
            }
            Zone zone = IZoneService.getCacheById(oldZoneId);
            if (zone == null) {
                logger.info("zoneId=================>{}", oldZoneId);
            }
            Assert.notNull(zone, "同步库存时，没有找到对应的区域信息！");
            ProductZone productZone = productZoneService.getByUnique(zone.getCode(), productId, departmentId);
            if (productZone == null) {
                logger.error("库存信息{}在数据库中找不到对应的产品区域！", oldStock);
            } else {
                Integer oldQuantity = productZone.getStock();
                stockDifference = stock - oldQuantity;
                String state = productZone.getState();
                productZone.setStock(stock);
                if (ProductZoneState.disappeared.name().equals(state)) {
                    productZone.setState(ProductZoneState.offsale.name());
                    productZone.setStateTime(LocalDateTime.now());
                }
                productZoneService.updateStock(productZone);
            }
            Product product = productService.getByIdSync(productId);
            if (product == null) {
                continue;
            }
            Integer productTotalStock = product.getTotalStock() + stockDifference;
            String productState = product.getState();
            if (ProductState.disappeared.name().equals(productState) && productTotalStock > 0) {
                product.setState(ProductState.offsale.name());
                product.setStateTime(LocalDateTime.now());
            } else if (ProductState.offsale.name().equals(productState) && productTotalStock <= 0) {
                product.setState(ProductState.disappeared.name());
                product.setStateTime(LocalDateTime.now());
            }
            product.setTotalStock(productTotalStock);
            productService.updateTotalStock(product);
            success++;
        }
        return new AsyncResult<>(success);
    }

    @Async("quantityPool")
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Integer> pullHasLastOrderAt(List<OldErpStock> oldErpStockList) {
        Integer success = 0;
        List<Integer> productIds = new ArrayList<>();
        for (OldErpStock oldErpStock : oldErpStockList) {
            LocalDateTime createdAt = oldErpStock.getLastOrderAt();
            Integer productId = oldErpStock.getProductId();
            Product product = new Product();
            product.setId(productId);
            Integer oldDepartmentId = oldErpStock.getDepartmentId();
            Integer departmentId = 0;
            Integer oldZoneId = oldErpStock.getZoneId();
            Department department = null;
            try {
                department = iDepartmentService.getByOldId(oldDepartmentId);
            } catch (Exception e) {
                logger.error("出错部门: {} 根据老erp部门ID查询到多条记录或者其他系统异常,异常信息: {} ", oldDepartmentId, e.getMessage());
                continue;
            }
            if (department != null) {
                departmentId = department.getId();
            }
            Zone zone = IZoneService.getCacheById(oldZoneId);
            if (zone == null) {
                continue;
            }
            Assert.notNull(zone, "同步最后下单时间时，没有找到对应的区域信息！");
            ProductZone productZone = productZoneService.getByUnique(zone.getCode(), productId, departmentId);
            if (productZone == null) {
                logger.error("最后下单时间信息{}在数据库中找不到对应的产品区域！", oldErpStock);
            } else {
                productZone.setLastOrderAt(createdAt);
                Duration duration = Duration.between(createdAt, LocalDateTime.now());
                long difference = duration.toDays();
                if (difference < 7) {
                    productZone.setState(ProductZoneState.onsale.name());
                    productZone.setStateTime(LocalDateTime.now());
                    productIds.add(productId);
                }
                productZoneService.updateLastOrderAt(productZone);
            }
            success++;
        }
        if (!productIds.isEmpty()) {
            productService.updateStateByIds(productIds, ProductState.onsale.name());
        }
        return new AsyncResult<>(success);
    }


}
