package com.stosz.purchase.service;

import com.stosz.fsm.FsmProxyService;
import com.stosz.order.ext.dto.PurchaseItemDTO;
import com.stosz.order.ext.dto.PurchaseItemRq;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.purchase.ext.common.WmsWriteBackDto;
import com.stosz.purchase.ext.common.WriteBackItemDto;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.enums.PurchaseReturnedItemEvent;
import com.stosz.purchase.ext.model.*;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与wms 相关的回调service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/25]
 */
@Service
public class WmsApiService {

    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private PurchaseReturnedService purchaseReturnedService;
    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;
    @Resource
    private IOrderService iOrderService;
    @Resource
    private FsmProxyService<PurchaseItem> purchaseItemFsmProxyService;
    @Resource
    private IStockService iStockService;
    /*@Resource
    private IFinanceService iFinanceService;*/
    @Resource
    private SupplierService supplierService;
    @Resource
    private PurchaseInItemService purchaseInItemService;
    @Resource
    private PurchaseInService purchaseInService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 回写采购退货单的接口
     *
     * @param wmsWriteBackDto 回写单据的实体
     * @return
     */
    @Async
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void purchaseReturnWriteBack(WmsWriteBackDto wmsWriteBackDto, Integer returnedId) {
        List<WriteBackItemDto> writeBackItemDtoList = wmsWriteBackDto.getData();
        for (WriteBackItemDto writeBackItemDto : writeBackItemDtoList) {
            String sku = writeBackItemDto.getSku();
            List<PurchaseReturnedItem> purchaseReturnedItemList = purchaseReturnedItemService.findByNoAndSku(returnedId, sku);

            // 根据采购退货出库分配采购退货明细状态
            distributeReturnedItem(writeBackItemDto, purchaseReturnedItemList);

        }
    }


    private void distributeReturnedItem(WriteBackItemDto writeBackItemDto, List<PurchaseReturnedItem> purchaseReturnedItemList) {
        String skuReceived = writeBackItemDto.getSkuReceived();
        Integer skuTotalReceived = Integer.parseInt(skuReceived);
        for (PurchaseReturnedItem purchaseReturnedItem : purchaseReturnedItemList) {
            if (skuTotalReceived <= 0) {
                break;
            }
            Integer returnedQty = purchaseReturnedItem.getReturnedQty();
            Integer finishQty = purchaseReturnedItem.getFinishQty();
            Integer needQty = returnedQty - finishQty;
            if (needQty <= skuTotalReceived) {
                purchaseReturnedItem.setFinishQty(finishQty + needQty);
                skuTotalReceived = skuTotalReceived - needQty;
                purchaseReturnedItemService.update(purchaseReturnedItem);
                //调用采购退货的完成状态机
                purchaseReturnedItemService.processEvent(purchaseReturnedItem, PurchaseReturnedItemEvent.deliveryStock, "仓库完成出货", null);
            } else {
                purchaseReturnedItem.setFinishQty(finishQty + skuTotalReceived);
                purchaseReturnedItemService.update(purchaseReturnedItem);
            }
        }
        //TODO 调用仓储采购退货出库完成的接口
    }

    /**
     * 采购入库
     *
     * @param wmsWriteBackDto 回写单据的实体
     */
    @Async
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void purchaseInStock(WmsWriteBackDto wmsWriteBackDto) {

        logger.info("wms系统回写数据WmsWriteBackDto：{}", wmsWriteBackDto);
        //判断是否需要向订单模块获取明细 wms回写数与wms统计总数相等
        String purchaseNo = wmsWriteBackDto.getPurchaseNo();
        int purchaseId = Integer.parseInt(purchaseNo);
        List<PurchaseItem> doPurchaseItems = new ArrayList<>();
        List<WriteBackItemDto> data = wmsWriteBackDto.getData();
        //解析回写数据，并筛选出需要向订单系统请求部门分配的sku
        List<PurchaseItemRq> purchaseItemRqs = wmsInStockData(wmsWriteBackDto, purchaseId, doPurchaseItems);
        //向订单模块请求获分配流水
        List<PurchaseItem> purchaseItems = orderPurchaseItems(purchaseId, purchaseItemRqs, data);
        doPurchaseItems.addAll(purchaseItems);
        //添加采购入库及入库明细表记录，并更细采购表实际入库数及状态 整理向仓库模块请求数据
        List<StockChangeReq> stockChangeReqs = addPurchaseIn(doPurchaseItems, wmsWriteBackDto);
        //改变库存
        iStockService.purchaseChangeStockQty(stockChangeReqs);
    }

    /**
     * 解析wms回写数据
     *
     * @param purchaseId
     * @return purchaseItems
     */
    private List<PurchaseItemRq> wmsInStockData(WmsWriteBackDto wmsWriteBackDto, Integer purchaseId, List<PurchaseItem> doPurchaseItems) {
        List<WriteBackItemDto> data = wmsWriteBackDto.getData();
        List<PurchaseItemRq> purchaseItemRqs = new ArrayList<PurchaseItemRq>();
        for (WriteBackItemDto writeBackItemDto : data) {

            String sku = writeBackItemDto.getSku();
            List<PurchaseItem> purchaseItems = purchaseItemService.findByPurAndSku(purchaseId, sku);
            int skuCountQty = 0;
            for (PurchaseItem purchaseItem : purchaseItems) {
                Integer waitPurchaseQty = purchaseItem.getQuantity() - purchaseItem.getInstockQty();
                purchaseItem.setWaitInStockQty(waitPurchaseQty);
                skuCountQty = skuCountQty + waitPurchaseQty;
            }
            String skuReceived = writeBackItemDto.getSkuReceived();
            int wmsSkuQty = Integer.parseInt(skuReceived);
            if (skuCountQty == wmsSkuQty) {
                //不需要向订单获取部门分配明细直接按采购明细来入库
                doPurchaseItems.addAll(purchaseItems);
            } else {
                //需要向订单获取部门分配明细
                PurchaseItemRq purchaseItemR = new PurchaseItemRq();
                purchaseItemR.setWmsId(Integer.parseInt(wmsWriteBackDto.getWarehouseId()));
                purchaseItemR.setSku(sku);
                purchaseItemR.setPurchaseQty(wmsSkuQty);
                Map<Integer, Integer> detps = new HashMap<>();
                List<PurchaseItem> purchaseItems1 = purchaseItemService.findByPurAndSku(purchaseId, sku);
                for (PurchaseItem purchaseItem : purchaseItems1) {
                    Integer deptId = purchaseItem.getDeptId();
                    Integer avgSaleQty = purchaseItem.getWaitInStockQty();
                    detps.put(deptId, avgSaleQty);
                }
                purchaseItemR.setDeptMaxQty(detps);
                purchaseItemRqs.add(purchaseItemR);
            }
        }
        return purchaseItemRqs;
    }

    /**
     * 向訂單系統獲取部門分配明細
     *
     * @param purchaseId
     * @return purchaseItems
     */
    private List<PurchaseItem> orderPurchaseItems(Integer purchaseId, List<PurchaseItemRq> purchaseItemRqs, List<WriteBackItemDto> data) {
        logger.info("请求订单系统系统数据orderPurchaseItems：{}", purchaseItemRqs);
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        //向订单模块获取采购如何明细
        if (purchaseItemRqs.size() > 0) {
            List<PurchaseItemDTO> purchaseItemDTOs = iOrderService.getPurchaseItem(purchaseItemRqs);
            for (PurchaseItemDTO purchaseItemDTO : purchaseItemDTOs) {
                PurchaseItem purchaseItem = purchaseItemService.findOne(purchaseId, purchaseItemDTO.getSku(), purchaseItemDTO.getDeptId());
                purchaseItem.setWaitInStockQty(purchaseItemDTO.getQty());
                purchaseItems.add(purchaseItem);
            }

            //判断sku分配数量和wms回写数据是否一致
            for (WriteBackItemDto writeBackItemDto : data) {

                String sku = writeBackItemDto.getSku();
                int skuCountQty = 0;
                for (PurchaseItem purchaseItem : purchaseItems) {
                    if (sku.equals(purchaseItem.getSku())) {
                        skuCountQty = skuCountQty + purchaseItem.getWaitInStockQty();
                    }
                }
                String skuReceived = writeBackItemDto.getSkuReceived();
                int wmsSkuQty = Integer.parseInt(skuReceived);
                Assert.isTrue(skuCountQty == wmsSkuQty, sku + "调整后无法与wms会写接口数据一致");
            }
        }
        return purchaseItems;
    }

    /**
     * 添加采购入库及采购入库明细
     *
     * @param
     * @return purchaseItems
     */
    private List<StockChangeReq> addPurchaseIn(List<PurchaseItem> doPurchaseItems, WmsWriteBackDto wmsWriteBackDto) {
        //添加入库表数据
        PurchaseIn purchaseIn = new PurchaseIn();
        Purchase purchase = purchaseService.getById(Integer.parseInt(wmsWriteBackDto.getPurchaseNo()));
        purchaseIn.setBuDeptId(purchase.getBuDeptId());
        purchaseIn.setBuDeptNo(purchase.getBuDeptNo());
        purchaseIn.setBuDeptName(purchase.getBuDeptName());
        purchaseIn.setPurchaseId(purchase.getId());
        purchaseIn.setPurchaseNo(purchase.getPurchaseNo());
        purchaseIn.setWmsId(purchase.getWmsId());
        purchaseIn.setWmsName(purchase.getWmsName());
        purchaseIn.setWmsSysCode(purchase.getWmsSysCode());
        purchaseIn.setCreator("系统");
        purchaseIn.setCreatorId(0);
        purchaseIn.setPlatId(purchase.getPlatId());
        purchaseIn.setPlatName(purchase.getPlatName());
        purchaseIn.setPlatOrdersNo(purchase.getPlatOrdersNo());
        purchaseIn.setTrakingNo(purchase.getTrackingNo());
        purchaseIn.setQuantity(purchase.getQuantity());
        String x = wmsWriteBackDto.getInTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        purchaseIn.setInstockTime(LocalDateTime.parse(x,dtf));
        purchaseIn.setInstockQty(Integer.parseInt(wmsWriteBackDto.getTotalReceived()));
        purchaseIn.setInstockUserId(0);
        purchaseIn.setInstockUser("系统");
        purchaseInService.insert(purchaseIn);

        List<StockChangeReq> stockChangeReqs = new ArrayList<>();
        for (PurchaseItem purchaseItem : doPurchaseItems) {
            //添加入库明细表数据
            PurchaseInItem purchaseInItem = new PurchaseInItem();
            BeanUtil.copyProperties(purchaseItem, purchaseInItem);
            purchaseInItem.setPurchaseInId(purchaseIn.getId());
            purchaseInItem.setInstockQty(purchaseItem.getWaitInStockQty());
            purchaseInItemService.insert(purchaseInItem);
            //修改入库数及状态
            purchaseItemService.updateInstockQty(purchaseItem.getId(), purchaseItem.getWaitInStockQty());
            purchaseItemFsmProxyService.processEvent(purchaseItem, PurchaseItemEvent.inStock, "仓库入库完成", LocalDateTime.now(), null);
            //整理操作库存的数据
            StockChangeReq stockChangeReq = new StockChangeReq();
            BeanUtils.copyProperties(purchaseItem, stockChangeReq);
            stockChangeReq.setChangeQty(purchaseItem.getWaitInStockQty());
            stockChangeReq.setType("purchase_in_stock");
            stockChangeReq.setChangeAt(purchase.getCreateAt());
            stockChangeReq.setWmsId(purchase.getWmsId());
            stockChangeReq.setAmount(purchaseItem.getPrice().multiply(new BigDecimal(purchaseItem.getWaitInStockQty())));
            stockChangeReq.setVoucherNo(purchase.getId().toString());
            stockChangeReqs.add(stockChangeReq);
            logger.info("请求仓库系统数据stockChangeReq：{}", stockChangeReqs);
        }
        return stockChangeReqs;
    }

}