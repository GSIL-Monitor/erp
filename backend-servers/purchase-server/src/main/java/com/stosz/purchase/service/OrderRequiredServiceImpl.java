package com.stosz.purchase.service;


import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.order.ext.dto.OrderRequiredResponse;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import com.stosz.purchase.ext.model.PurchaseRequired;
import com.stosz.purchase.ext.model.Supplier;
import com.stosz.purchase.ext.model.UserProductRel;
import com.stosz.purchase.ext.service.IOrderRequiredService;
import com.stosz.store.ext.dto.request.QueryQtyReq;
import com.stosz.store.ext.dto.response.StockRes;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author feiheping
 * @version [1.0,2017年11月10日]
 */
@Service
public class OrderRequiredServiceImpl implements IOrderRequiredService {

    private Logger logger = LoggerFactory.getLogger(OrderRequiredServiceImpl.class);

    @Resource
    private PurchaseRequiredService purchaseRequiredService;

    @Resource
    private IDepartmentService iDepartmentService;

    @Resource
    private SkuSupplierService skuSupplierService;

    @Resource
    private UserProductRelService userProductRelService;

    @Resource
    private IOrderService iOrderService;

    @Resource
    private IStockService iStockService;

    @Resource
    private IProductSkuService iProductSkuService;

    @Override
    public void notifyOrderRequired(OrderNotifyRequired orderRequired) {
        Assert.notNull(orderRequired, "实体不能为空");
        logger.info("OrderRequiredServiceImpl notifyOrderRequired() 发起采购需求计算  spu={},sku={},deptid={},type={}", orderRequired.getSpu(), orderRequired.getSku(), orderRequired.getDeptId(),orderRequired.getType());

        Integer deptId = orderRequired.getDeptId();
        String sku = orderRequired.getSku();
        String spu = orderRequired.getSpu();
        Assert.hasText(sku, "产品SKU不能为空");
        Assert.notNull(deptId, "部门ID不能为空");
        Assert.hasText(spu, "产品SPU不能为空");
        Assert.notNull(orderRequired.getType(), "Type不能为空");
        try {
            Department department = iDepartmentService.get(deptId);
            Assert.notNull(department, "部门ID[" + department.getId() + "]不存在");
            // 根据SKU+Deptid 查询采购需求
            PurchaseRequired purchaseRequired = new PurchaseRequired();
            purchaseRequired.setSku(sku);
            //保存一下sku的title，避免查询采购需求列表的时候要去批量查询
            String skuTitle = iProductSkuService.getAttrValueTitleBySku(sku);
            purchaseRequired.setSkuTitle(skuTitle);

            purchaseRequired.setDeptId(deptId);
            //查询数据库中是否已经存在了该采购需求
            List<PurchaseRequired> requireds = purchaseRequiredService.query(purchaseRequired);

            PurchaseRequired lastPurchaseRequired;

            PurchaseRequired updateRequired;

            if (CollectionUtils.isNullOrEmpty(requireds)) {
                updateRequired = createPurchaseRequired(deptId, sku, spu, department);
            } else {
                updateRequired = requireds.get(0);
                this.fillPurchaseRequired(updateRequired);
            }
            lastPurchaseRequired = updateRequired.cloneValue();
            logger.info("OrderRequiredServiceImpl 通过 deptId={},sku={} 获取订单需求数", deptId, sku);
            //   根 据SKU 部门ID 拉取订单需求数,接口还没有出
            OrderRequiredResponse response = iOrderService.pullOrderReuired(deptId, sku);
            logger.info("OrderRequiredServiceImpl 获取到orderRequiredQty={},avgSaleQty={},pendingOrderQty={}", response.getOrderRequiredQty(),
                    response.getAvgSaleQty(), response.getPendingOrderQty());

            //  查询SKU+部门的库存
            QueryQtyReq queryQtyReq = new QueryQtyReq();
            queryQtyReq.setDeptId(deptId);
            queryQtyReq.setSku(sku);
            queryQtyReq.setSpu(spu);
            queryQtyReq.setWmsId(1);// 需要添加福永仓的仓库id；
            List<StockRes> stockResList = iStockService.queryQty(queryQtyReq);
            StockRes stockRecord = new StockRes();
            if(CollectionUtils.isNotNullAndEmpty(stockResList)){
                stockRecord = stockResList.get(0);
            }else{
                stockRecord.setIntransitQty(0);
                stockRecord.setUsableQty(0);
            }

            // 订单需求数
            Integer orderRequiredQty = StringUtil.nvl(response.getOrderRequiredQty(), 0);
            Integer productTotality = stockRecord.getUsableQty() + stockRecord.getIntransitQty();
            // 采购需求数=订单需求数-(在库-在单)-在途数
            Integer purchaseQty = orderRequiredQty - productTotality;
            Integer planPurchaseQty = purchaseQty >= 0 ? purchaseQty : 0;
            // 可用库存数
            updateRequired.setStockQty(stockRecord.getUsableQty());
            // 订单需求数
            updateRequired.setOrderRequiredQty(orderRequiredQty);
            // 采购在途数
            updateRequired.setIntransitQty(stockRecord.getIntransitQty());
            // 采购需求数
            updateRequired.setPurchaseQty(purchaseQty);
            // 三日平均成交量
            updateRequired.setAvgSaleQty(response.getAvgSaleQty());
            // 待审单数
            updateRequired.setPendingOrderQty(response.getPendingOrderQty());
            //保存skuTitle
            updateRequired.setSkuTitle(skuTitle);

            if (lastPurchaseRequired.getPurchaseQty()<= 0 && purchaseQty > 0) {// 记录采购需求的时间 用于统计KPI
                updateRequired.setRequiredAppearedTime(new Date());
            }
            if (updateRequired.getId() == null) {
                // 计划采购数
                updateRequired.setPlanQty(planPurchaseQty);
                updateRequired.setChangeStatus(0);
                purchaseRequiredService.addRequired(updateRequired);
            } else {
                if (updateRequired.getPlanQty() == 0 && planPurchaseQty>0) {//如果计划采购数为0，那么默认将为采购需求数
                    updateRequired.setPlanQty(planPurchaseQty);
                }
                purchaseRequiredService.updateRequired(updateRequired);
            }
            logger.info("OrderRequiredServiceImpl notifyOrderRequired() 完成采购需求计算 sku={},dept={}", sku, deptId);
        } catch (Exception e) {
            logger.error("OrderRequiredServiceImpl notifyOrderRequired() sku={},dept={} 采购需求计算错误 Exception={}", sku, deptId, e);
        }
    }

    private PurchaseRequired createPurchaseRequired(Integer deptId, String sku, String spu, Department department) {
        PurchaseRequired updateRequired;
        // 查询事业部
        Department buDepartment = iDepartmentService.findParentByDeptId(deptId);
        updateRequired = new PurchaseRequired();
        UserProductRel productRel = null;
        if (buDepartment != null) {
            updateRequired.setBuDeptId(buDepartment.getId());
            updateRequired.setBuDeptName(buDepartment.getDepartmentName());
            updateRequired.setBuDeptNo(buDepartment.getDepartmentNo());
            // 根据SPU+事业部ID 查询绑定的采购员
            productRel = userProductRelService.getBySpuAndId(spu, buDepartment.getId());
        }
        updateRequired.setDeptId(department.getId());
        updateRequired.setDeptName(department.getDepartmentName());
        updateRequired.setDeptNo(department.getDepartmentNo());
        // 查询最后 一次采购SKU的供应商
        Supplier supplier = skuSupplierService.queryLastSupplierBySku(sku);
        if (supplier != null) {
            updateRequired.setSupplierId(supplier.getId());
            updateRequired.setSupplierName(supplier.getName());
        }
        if (productRel != null) {
            updateRequired.setBuyerId(productRel.getUserId());
            updateRequired.setBuyer(productRel.getUserName());
        }
        updateRequired.setSpu(spu);
        updateRequired.setSku(sku);
        // 默认福永仓
        updateRequired.setWmsId(1);
        updateRequired.setWmsSysCode("Y");
        updateRequired.setState(1);
        updateRequired.setCreateAt(new Date());
        updateRequired.setPurchaseQty(0);
        return updateRequired;
    }

    /**
     * 填充采购人员
     * 填充供应商
     */
    private void fillPurchaseRequired(PurchaseRequired purchaseRequired) {
        if (purchaseRequired.getBuDeptId() != null) {
            UserProductRel userProductRel = userProductRelService.getBySpuAndDeptId(purchaseRequired.getSpu(), purchaseRequired.getBuDeptId());
            if (userProductRel != null) {
                purchaseRequired.setBuyerId(userProductRel.getUserId());
                purchaseRequired.setBuyer(userProductRel.getUserName());
            }
        }
        Supplier supplier = skuSupplierService.queryLastSupplierBySku(purchaseRequired.getSku());
        if (supplier != null) {
            purchaseRequired.setSupplierId(supplier.getId());
        }
    }

}
