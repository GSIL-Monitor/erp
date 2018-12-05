package com.stosz.purchase.service;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.purchase.ext.common.PurchaseSkuDto;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.enums.PurchaseItemState;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.mapper.PurchaseItemMapper;
import com.stosz.purchase.utils.ArrayUtils;
import com.stosz.store.ext.dto.request.QueryQtyReq;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.StockRes;
import com.stosz.store.ext.enums.PurchaseHandleEnum;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 采购明细的service
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
@Service
public class PurchaseItemService {

    @Resource
    private PurchaseItemMapper mapper;

    @Resource
    private IProductSkuService iProductSkuService;

    @Resource
    private IStockService iStockService;

    @Resource
    private FsmProxyService<PurchaseItem> purchaseItemFsmProxyService;

    @Resource
    private PurchaseService purchaseService;
    @Resource
    private IProductService iProductService;
    @Resource
    private FsmHistoryService purchaseItemFsmHistory;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新增采购明细
     * @param purchaseItem 采购明细
     */
    public void insert(PurchaseItem purchaseItem) {
        Assert.notNull(purchaseItem, "采购明细不允许为空！");
        mapper.insert(purchaseItem);
        purchaseItemFsmProxyService.processNew(purchaseItem, purchaseItem.getMemo());
        purchaseItemFsmProxyService.processEvent(purchaseItem, PurchaseItemEvent.create, purchaseItem.getMemo());
        logger.info("采购明细{}创建成功！", purchaseItem);
    }

    /**
     * 删除采购明细
     * @param id id
     */
    public void delete(Integer id) {
        Assert.notNull(id, "要删除的采购明细id不允许为空！");
        PurchaseItem purchaseItem = getById(id);
        Purchase purchase = purchaseService.getById(purchaseItem.getPurchaseId());
        Assert.notNull(purchaseItem, "要删除的采购明细在数据库中找不到！");
        mapper.delete(id);
        if(PurchaseItemState.cancel.equals(purchaseItem.getStateEnum())){
            List<StockChangeReq> stockChangeReqList = new ArrayList<>();
            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setChangeQty(purchaseItem.getQuantity());
            stockChangeReq.setType(PurchaseHandleEnum.intransit_minus.name());
            stockChangeReq.setDeptId(purchaseItem.getDeptId());
            stockChangeReq.setWmsId(purchase.getWmsId());
            stockChangeReq.setSku(purchaseItem.getSku());
            stockChangeReq.setDeptName(purchaseItem.getDeptName());
            stockChangeReq.setSpu(purchaseItem.getSpu());
            stockChangeReq.setAmount(purchaseItem.getAmount());
            stockChangeReq.setVoucherNo(purchase.getPurchaseNo());
            stockChangeReq.setChangeAt(LocalDateTime.now());
            stockChangeReqList.add(stockChangeReq);
            iStockService.purchaseChangeStockQty(stockChangeReqList);
            logger.info("删除采购明细{}，通知仓库修改库存成功！",purchaseItem);
        }
        logger.info("删除采购明细{}成功！", purchaseItem);
    }

    /**
     * 修改采购明细
     * @param purchaseItem 修改明细
     */
    public void update(PurchaseItem purchaseItem) {
        Assert.notNull(purchaseItem, "采购明细不允许为空！");
        Integer purchaseItemId = purchaseItem.getId();
        PurchaseItem purchaseItemDB = mapper.getById(purchaseItemId);
        Assert.notNull(purchaseItemDB, "采购明细【" + purchaseItemId + "】在数据库中不存在！");
        mapper.update(purchaseItem);
        logger.info("将采购明细{}修改为{}成功！", purchaseItemDB, purchaseItem);
    }

    public List<PurchaseItem> findByPurchaseId(Integer purchaseId) {
        Assert.notNull(purchaseId, "查询采购明细，采购单id不允许为空！");
        return mapper.findByPurchaseId(purchaseId);
    }

    public List<PurchaseItem> findByPurAndSku(Integer purchaseId,String sku) {
        Assert.notNull(purchaseId, "查询采购明细，采购单id不允许为空！");
        Assert.notNull(sku, "sku不能为空");
        return mapper.findByPurIdAndSku(purchaseId,sku);
    }

    public PurchaseItem findOne(Integer purchaseId,String sku,Integer deptId) {
        Assert.notNull(purchaseId, "查询采购明细，采购单id不允许为空！");
        Assert.notNull(sku, "sku不能为空");
        return mapper.findOne(purchaseId,sku,deptId);
    }

    public PurchaseItem getById(Integer id) {
        Assert.notNull(id, "查询采购明细，采购单id不允许为空！");
        return mapper.getById(id);
    }

    public String getPurchaseSpu(Integer purchaseId) {
        Assert.notNull(purchaseId, "查询采购单对应的产品，采购单id不允许为空！");
        List<String> spus = mapper.getPurchaseSpu(purchaseId);
        // 当前不考虑一个采购单对应多个spu的情况
        Assert.notNull(spus, "采购单id为【" + purchaseId + "】的采购单，未发现采购单明细，发生错误！");
        Assert.isTrue(spus.size() == 1, "id为【" + purchaseId + "】采购单发现了" + spus.size() + "个spu,不符合要求！");
        return spus.get(0);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processEvent(PurchaseItem purchaseItem, PurchaseItemEvent purchaseItemEvent, String memo, String uid) {
        Assert.notNull(purchaseItem, "采购明细不允许为空！");
        if (StringUtils.isBlank(uid)) {
            uid = MBox.getSysUser();
        }
        this.purchaseItemFsmProxyService.processEvent(purchaseItem, purchaseItemEvent,uid , LocalDateTime.now(), memo);
    }

    public List<PurchaseItem> findPurchaseItem(PurchaseItem purchaseItem) {
        List<PurchaseItem> purchaseItems = mapper.findPageList(purchaseItem);
        Set<String> skuSet = purchaseItems.stream().map(PurchaseItem::getSku).collect(Collectors.toSet());
        Map<String, String> skuTitleMap = iProductSkuService.getAttrValueCombinations(new ArrayList<>(skuSet));
        for (PurchaseItem item : purchaseItems) {
            if (ArrayUtils.isNotEmpty(skuTitleMap)) {
                String skuTitle = skuTitleMap.get(item.getSku());
                item.setSkuTitle(skuTitle);
            }
        }
        return purchaseItems;
    }

    public int findPurchaseItemCount(PurchaseItem purchaseItem) {
        return mapper.findPageListCount(purchaseItem);
    }

    /**
     * 根据采购单号获取到对应的采购sku实体
     * @param purchaseId 采购单号
     * @return 采购sku实体
     */
    public List<PurchaseSkuDto> findSkuDtoByPurchaseId(Integer purchaseId) {
        Assert.notNull(purchaseId, "传入的采购单id不允许为空！");
        List<PurchaseSkuDto> purchaseSkuDtoList = mapper.findSkuDtoByPurchaseId(purchaseId);
        Assert.notNull(purchaseSkuDtoList, "采购单【" + purchaseId + "】获取不到采购单明细的sku！");
        List<String> skuList = purchaseSkuDtoList.stream().map(PurchaseSkuDto::getSku).collect(Collectors.toList());
        Map<String, String> skuMap = iProductSkuService.getAttrValueCombinations(skuList);
        List<PurchaseItem> purchaseItemList = findByPurchaseId(purchaseId);

        for (PurchaseSkuDto purchaseSkuDto : purchaseSkuDtoList) {
            String sku = purchaseSkuDto.getSku();
            List<PurchaseItem> purchaseItems = purchaseItemList.stream().filter(purchaseItem -> sku.equals(purchaseItem.getSku())).collect(Collectors.toList());
            Integer totalUseAbleQty = 0;
            for(PurchaseItem purchaseItem : purchaseItems){
                QueryQtyReq queryQtyReq = new QueryQtyReq();
                queryQtyReq.setWmsId(1);// 福永仓
                queryQtyReq.setSku(purchaseItem.getSku());
                queryQtyReq.setDeptId(purchaseItem.getDeptId());
                List<StockRes> list = iStockService.queryQty(queryQtyReq);
                if(CollectionUtils.isNullOrEmpty(list)){
                    purchaseItem.setUsableQty(0);
                }else {
                    StockRes stockRes = list.get(0);
                    purchaseItem.setUsableQty(stockRes.getUsableQty());
                    totalUseAbleQty += stockRes.getUsableQty();
                }
            }
            purchaseSkuDto.setTotalUseAbleQty(totalUseAbleQty);

            String skuTitle = "";
            if(skuMap != null) {
                skuTitle = skuMap.get(sku);
            }
            purchaseSkuDto.setSkuTitle(skuTitle);
            purchaseSkuDto.setPurchaseItemList(purchaseItems);
        }
        return purchaseSkuDtoList;
    }


    public Set<String> findSkuByPurchaseId(Integer purchaseId){
        return mapper.findSkuByPurchaseId(purchaseId);
    }


    /**
     * 根据sku获取对应的采购明细
     * @param sku sku
     * @return 采购明细列表
     */
    public List<PurchaseItem> findBySku(String sku,Integer purchaseId) {
        Assert.hasLength(sku, "传入的sku不允许为空！");
        return mapper.findBySku(sku,purchaseId);
    }

    public List<PurchaseItem> getListByIds(List<Integer> ids){
        return mapper.getListByIds(ids);
    }

    /**
     * 根据采购明细 增加在途采退数
     * @param id
     * @param addCancleQty
     * @return
     */
    public int updateCancleQty(Integer id, Integer addCancleQty) {
        return mapper.updateCancleqtyById(id, addCancleQty);
    }

    /**
     * 根据采购明细 增加在入库数
     * @param id
     * @param addCancleQty
     * @return
     */
    public int updateInstockQty(Integer id, Integer addCancleQty) {
        return mapper.updateInstockQtyById(id, addCancleQty);
    }

    /**
     * 修改在途采退数
     * @param returnedItems
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public void modifyPurhcaseCancleQty(List<PurchaseReturnedItem> returnedItems) {
        Assert.notEmpty(returnedItems, "采退明细不能为空");
        // 采购明细的在途采退数需要减少取消数
        List<Integer> purchaseItemIds = returnedItems.stream().map(PurchaseReturnedItem::getPurchaseItemId).collect(Collectors.toList());
        List<PurchaseItem> purchaseItems = mapper.queryByIds(purchaseItemIds);
        Map<Integer, PurchaseItem> purchaseItemMap = purchaseItems.stream().collect(Collectors.toMap(PurchaseItem::getId, Function.identity()));
        for (PurchaseReturnedItem returnedItem : returnedItems) {
            PurchaseItem purchaseItem = purchaseItemMap.get(returnedItem.getPurchaseItemId());
            Assert.notNull(purchaseItem, "采退明细ID[" + returnedItem.getPurchaseItemId() + "]不存在");
            int returnedQty = 0 - returnedItem.getReturnedQty();
            mapper.updateCancleqtyById(purchaseItem.getId(), returnedQty);
        }
    }

    /**
     * 根据采购单集合获取对应的产品集合（包含产品spu和产品title）
     * @param purchases 采购单集合
     * @return 返回采购单对应的产品集合
     */
    public Map<Integer,Product> findSpu(List<Purchase> purchases){
        List<PurchaseItem> purchaseItemList = mapper.findSpu(purchases);
        Map<Integer,Product> purchaseMap = new HashMap<>();
        for(PurchaseItem purchaseItem: purchaseItemList){
            Product product = new Product();
            product.setSpu(purchaseItem.getSpu());
            product.setTitle(purchaseItem.getProductTitle());
            purchaseMap.put(purchaseItem.getPurchaseId(),product);
        }
        return purchaseMap;
    }

    /**
     * 根据采购单id ，获取采购明细中sku对应的采购单价
     * @param purchaseId  采购单id
     * @return sku,price
     */
    public Map<String,BigDecimal> findPrice(Integer purchaseId){
        List<PurchaseItem> purchaseItemList = mapper.findByPurchaseId(purchaseId);
        Map<String,BigDecimal> purchasePriceMap = new HashMap<>();
        for (PurchaseItem purchaseItem:purchaseItemList){
            purchasePriceMap.put(purchaseItem.getSku(),purchaseItem.getPrice());
        }
        return purchasePriceMap;
    }


    /**
     * 根据采购明细查询状态机历史记录
     *
     * @param id
     * @return
     */
    public RestResult queryHistory(Integer id, Integer start, Integer limit) {
        return purchaseItemFsmHistory.queryHistory("PurchaseItem", id, start, limit);
    }
}
