package com.stosz.purchase.service;

import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.enums.PurchaseReturnState;
import com.stosz.purchase.ext.enums.PurchaseReturnedEvent;
import com.stosz.purchase.ext.model.*;
import com.stosz.purchase.ext.model.finance.PayStateNotice;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.ext.model.finance.PayableItem;
import com.stosz.purchase.ext.service.IPurchaseReturnedService;
import com.stosz.purchase.mapper.PurchaseReturnedMapper;
import com.stosz.store.ext.dto.request.QueryQtyReq;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.StockRes;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购退货
 *
 * @author feiheping
 * @version [1.0, 2017年11月20日]
 */
@Service
public class PurchaseReturnedService implements IPurchaseReturnedService{

    @Resource
    private PurchaseReturnedMapper purchaseReturnedMapper;

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private IStockService iStockService;

    @Resource
    private PurchaseReturnedItemService returnedItemService;

    @Resource
    public PurchaseItemService purchaseItemService;

    @Resource
    private FsmProxyService<PurchaseReturned> fsmService;

    @Resource
    private IProductService iProductService;

    @Resource
    private FsmHistoryService fsmHistoryService;

    @Value("${songgang.wms.id}")
    private Integer wmsId;//仓库ID

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PurchaseReturned transferReturned(CreateReturnDto createReturnDto, Purchase purchase) {
        PurchaseReturned purchaseReturned = new PurchaseReturned();
        UserDto user = ThreadLocalUtils.getUser();
        purchaseReturned.setCreator(user.getLastName());
        purchaseReturned.setCreatorId(user.getId());
        purchaseReturned.setPurchaseNo(createReturnDto.getPurchaseId().toString());
        purchaseReturned.setBuDeptId(purchase.getBuDeptId());
        purchaseReturned.setBuDeptName(purchase.getBuDeptName());
        purchaseReturned.setBuDeptNo(purchase.getBuDeptNo());
        purchaseReturned.setWmsId(purchase.getWmsId());
        // 待财务退款
        purchaseReturned.setState(PurchaseReturnState.start.name());
        purchaseReturned.setStateTime(LocalDateTime.now());
        purchaseReturned.setType(createReturnDto.getReturnType());
        purchaseReturned.setAmount(createReturnDto.getRefundAmount());
        purchaseReturned.setPayAmount(createReturnDto.getPayAmount());
        purchaseReturned.setAddress(createReturnDto.getRefundAddress());
        // 物流公司信息
        purchaseReturned.setShippingId(createReturnDto.getShippingId());
        // purchaseReturned.setShippingName(shippingName);
        purchaseReturned.setMemo(createReturnDto.getMemo());
        return purchaseReturned;
    }


    public List<PurchaseReturned> queryList(PurchaseReturned purchaseReturned) {

        List<PurchaseReturned> purchaseReturneds = purchaseReturnedMapper.queryList(purchaseReturned);
        for (PurchaseReturned purchaseReturned1 : purchaseReturneds) {
            Purchase purchase = purchaseService.getByPurchaseNo(purchaseReturned1.getPurchaseNo());
            if(purchase !=null){
                purchaseReturned1.setPurchaseNo(purchase.getPurchaseNo());
                purchaseReturned1.setPlatOrdersNo(purchase.getPlatOrdersNo());
                purchaseReturned1.setPlatOrdersNo(purchase.getPlatOrdersNo());
                purchaseReturned1.setSupplierCode(purchase.getSupplierId().toString());
                purchaseReturned1.setBuyer(purchase.getBuyer());
                purchaseReturned1.setCreateAt(purchase.getCreateAt());
                purchaseReturned1.setState(PurchaseReturnState.fromName(purchaseReturned1.getState()).display());
                purchaseReturned1.setType(purchaseReturned1.getType());
            }
        }
        return purchaseReturneds;
    }

    public int queryCount(PurchaseReturned purchaseReturned) {
        return purchaseReturnedMapper.queryCount(purchaseReturned);
    }

    /**
     * 取消采退
     *
     * @param returnedId 采退单号
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public void cancelPurchaseReturned(Integer returnedId, String memo, String uid) {
        PurchaseReturned purchaseReturned = purchaseReturnedMapper.getById(returnedId);
        Assert.notNull(purchaseReturned, "采购单ID[" + returnedId + "]不存在");
        Assert.isTrue(!PurchaseReturnState.completed.name().equals(purchaseReturned.getState()), "已完成的采购退货单，不允许取消");
        Assert.isTrue(!PurchaseReturnState.cancel.name().equals(purchaseReturned.getState()), "已取消的采购退货单，不允许重复取消");

        List<PurchaseReturnedItem> returnedItems = returnedItemService.queryListByReturnId(returnedId);
        Assert.notEmpty(returnedItems, "采退明细不能为空");
        // 采购退货单状态变更
        fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.cancelRefund, memo, LocalDateTime.now(), uid);

    }



    public PurchaseReturned getByNo(String returnedNo) {
        Assert.hasLength(returnedNo, "采购退货单号不允许为空！");
        return purchaseReturnedMapper.getByNo(returnedNo);
    }

    public PurchaseReturned getById(Integer id) {
        return purchaseReturnedMapper.getById(id);
    }

    public List<PurchaseReturned> findReturnedByPurchaseNo(String purchaseNo){
        List<PurchaseReturned> purchaseReturnedList =  purchaseReturnedMapper.findByPurchaseNo(purchaseNo);
        List<PurchaseReturned> excutingPurchaseReturnedList = purchaseReturnedList.stream().filter(e->"returnByStorage".equals(e.getState())).collect(Collectors.toList());

        return excutingPurchaseReturnedList;
    }


    /**
     * 获取创建采退单页面数据
     *
     * @param purchaseId
     * @return
     */
    public List<PurchaseReturnSpu> find(Integer purchaseId) {

        List<PurchaseItem> purchaseItems = purchaseItemService.findByPurchaseId(purchaseId);
        Map<String, Map<String, Set<Integer>>> spuHasMap = new HashMap<>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            if (spuHasMap.containsKey(purchaseItem.getSpu())) {
                Map<String, Set<Integer>> stringSetMap = spuHasMap.get(purchaseItem.getSpu());
                if (stringSetMap.containsKey(purchaseItem.getSku())) {
                    Set<Integer> integers = stringSetMap.get(purchaseItem.getSku());
                    integers.add(purchaseItem.getId());
                } else {
                    Set<Integer> purIds = new HashSet<>();
                    purIds.add(purchaseItem.getId());
                    stringSetMap.put(purchaseItem.getSku(), purIds);
                }
            } else {
                Set<Integer> purIds = new HashSet<>();
                Map<String, Set<Integer>> skuMap = new HashMap<>();
                purIds.add(purchaseItem.getId());
                skuMap.put(purchaseItem.getSku(), purIds);
                spuHasMap.put(purchaseItem.getSpu(), skuMap);
            }
        }

        List<PurchaseReturnSpu> purchaseReturnSpus = new ArrayList<>();
        for (Map.Entry<String, Map<String, Set<Integer>>> entry : spuHasMap.entrySet()) {
            PurchaseReturnSpu purchaseReturnSpu = new PurchaseReturnSpu();
            String spu = entry.getKey();

            purchaseReturnSpu.setSpu(spu);
            Product product = iProductService.getBySpu(spu);
            purchaseReturnSpu.setProductId(product.getId());
            purchaseReturnSpu.setProductTitle(product.getTitle());
            purchaseReturnSpu.setMainImageUrl(product.getMainImageUrl());

            List<PurchaseReturnSku> purchaseReturnSkus = new ArrayList<>();
            Map<String, Set<Integer>> skus = entry.getValue();
            for (Map.Entry<String, Set<Integer>> sku : skus.entrySet()) {

                PurchaseReturnSku purchaseReturnSku = new PurchaseReturnSku();
                purchaseReturnSku.setSku(sku.getKey());

                List<DeptSkuQty> deptSkuQties = new ArrayList<>();
                Set<Integer> value = sku.getValue();
                for (Integer purId : value) {
                    PurchaseItem item = purchaseItemService.getById(purId);
                    DeptSkuQty deptSkuQty = new DeptSkuQty();
                    purchaseReturnSku.setSkuTitle(item.getSkuTitle());
                    purchaseReturnSku.setPrice(item.getPrice());
                    deptSkuQty.setPurchaseItemId(purId);
                    deptSkuQty.setDeptId(item.getDeptId());
                    deptSkuQty.setDeptName(item.getDeptName());
                    deptSkuQty.setInstockQty(item.getInstockQty());
                    deptSkuQty.setPurchaseQty(item.getPurchaseQty());
                    deptSkuQty.setIntransitQty(item.getQuantity() - item.getInstockQty());
                    deptSkuQty.setIntransitCancleQty(item.getIntransitCancleQty());
                    deptSkuQty.setQuantity(item.getQuantity());
                    //查询sku可用库存
                    QueryQtyReq queryQtyReq = new QueryQtyReq();
                    queryQtyReq.setSku(sku.getKey());
                    queryQtyReq.setWmsId(wmsId);
                    queryQtyReq.setDeptId(item.getDeptId());
                    List<StockRes> stockResList = iStockService.queryQty(queryQtyReq);
                    if(CollectionUtils.isNotNullAndEmpty(stockResList)){
                        deptSkuQty.setUseQty(stockResList.get(0).getUsableQty());
                    }
                    deptSkuQties.add(deptSkuQty);
                }
                purchaseReturnSku.setDeptSkuQties(deptSkuQties);
                purchaseReturnSkus.add(purchaseReturnSku);
            }
            purchaseReturnSpu.setPurchaseReturnSkus(purchaseReturnSkus);
            purchaseReturnSpus.add(purchaseReturnSpu);
        }
        return purchaseReturnSpus;
    }

    /**
     * 创建采退单
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public String createPurchaseReturn(CreateReturnDto createReturnDto) {

        Purchase purchase = purchaseService.getById(createReturnDto.getPurchaseId());
        PurchaseReturned purchaseReturned = transferReturned(createReturnDto, purchase);
        int count = purchaseReturnedMapper.addReturned(purchaseReturned);
        Assert.isTrue(count > 0, "采购退货单创建失败");

        List<PurchaseReturnedItem> returnedItemList = returnedItemService.transferItemList(purchaseReturned, createReturnDto);
        count = returnedItemService.addList(returnedItemList);
        Assert.isTrue(count > 0, "创建采购明细失败");
        fsmService.processNew(purchaseReturned, purchaseReturned.getMemo());
        if (purchaseReturned.getAmount() == purchaseReturned.getPayAmount()) {
            fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.submit, purchaseReturned.getMemo());
        } else
            fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.create, purchaseReturned.getMemo());
        return purchaseReturned.getId().toString();
    }

    /**
     * 业务审核通过
     * 采购退货单ID
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public void confirmFinanceRefund(Integer returnedId, String memo) {
        PurchaseReturned purchaseReturned = purchaseReturnedMapper.getById(returnedId);
        Assert.notNull(purchaseReturned, "采购退货单ID[" + returnedId + "]不存在");
        Assert.isTrue(!PurchaseReturnState.completed.name().equals(purchaseReturned.getState()), "已完成的采购退货单，不允许确认退款");
        Assert.isTrue(!PurchaseReturnState.cancel.name().equals(purchaseReturned.getState()), "已取消的采购退货单，不允许确认退款");
        Assert.isTrue(PurchaseReturnState.stayRefund.name().equals(purchaseReturned.getState()), "采购退货单单据状态异常");
        purchaseReturned.setMemo(memo);
        UserDto user = ThreadLocalUtils.getUser();
        purchaseReturned.setAuditor(user.getLastName());
        purchaseReturned.setAuditorId(user.getId());

        int count = purchaseReturnedMapper.updateReturned(purchaseReturned);
        Assert.isTrue(count > 0, "修改采购退货单失败");
        // 状态变更
        fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.agreeByBusiness, memo);
    }

    /**
     * 获取创建采退单页面数据
     *
     * @param purchaseReturnId
     * @return
     */
    public List<PurchaseReturnSpu> find1(Integer purchaseReturnId) {

        PurchaseReturned purchaseReturned = purchaseReturnedMapper.getById(purchaseReturnId);

        List<PurchaseReturnedItem> purchaseReturnedItems = returnedItemService.queryListByReturnId(purchaseReturnId);

        Map<String, Map<String, Set<Integer>>> spuHasMap = new HashMap<>();
        for (PurchaseReturnedItem purchaseReturnedItem : purchaseReturnedItems) {
            if (spuHasMap.containsKey(purchaseReturnedItem.getSpu())) {
                Map<String, Set<Integer>> stringSetMap = spuHasMap.get(purchaseReturnedItem.getSpu());
                if (stringSetMap.containsKey(purchaseReturnedItem.getSku())) {
                    Set<Integer> integers = stringSetMap.get(purchaseReturnedItem.getSku());
                    integers.add(purchaseReturnedItem.getId());
                } else {
                    Set<Integer> purIds = new HashSet<>();
                    purIds.add(purchaseReturnedItem.getId());
                    stringSetMap.put(purchaseReturnedItem.getSku(), purIds);
                }
            } else {
                Set<Integer> purIds = new HashSet<>();
                Map<String, Set<Integer>> skuMap = new HashMap<>();
                purIds.add(purchaseReturnedItem.getId());
                skuMap.put(purchaseReturnedItem.getSku(), purIds);
                spuHasMap.put(purchaseReturnedItem.getSpu(), skuMap);
            }
        }

        List<PurchaseReturnSpu> purchaseReturnSpus = new ArrayList<>();
        for (Map.Entry<String, Map<String, Set<Integer>>> entry : spuHasMap.entrySet()) {
            PurchaseReturnSpu purchaseReturnSpu = new PurchaseReturnSpu();
            String spu = entry.getKey();

            purchaseReturnSpu.setSpu(spu);
            Product product = iProductService.getBySpu(spu);
            purchaseReturnSpu.setProductId(product.getId());
            purchaseReturnSpu.setProductTitle(product.getTitle());
            purchaseReturnSpu.setMainImageUrl(product.getMainImageUrl());

            List<PurchaseReturnSku> purchaseReturnSkus = new ArrayList<>();
            Map<String, Set<Integer>> skus = entry.getValue();
            for (Map.Entry<String, Set<Integer>> sku : skus.entrySet()) {

                PurchaseReturnSku purchaseReturnSku = new PurchaseReturnSku();
                purchaseReturnSku.setSku(sku.getKey());

                List<DeptSkuQty> deptSkuQties = new ArrayList<>();
                Set<Integer> value = sku.getValue();
                for (Integer purId : value) {
                    PurchaseReturnedItem item = returnedItemService.findById(purId);
                    DeptSkuQty deptSkuQty = new DeptSkuQty();
                    purchaseReturnSku.setSkuTitle(item.getSkuTitle());
                    purchaseReturnSku.setPrice(item.getPurchasePrice());
                    deptSkuQty.setPurchaseItemId(purId);
                    deptSkuQty.setDeptId(item.getDeptId());
                    deptSkuQty.setDeptName(item.getDeptName());
                    deptSkuQty.setPurchaseQty(item.getPurchaseQty());

                    StockRes stock = findStock(purchaseReturned.getWmsId(), item.getDeptId(), item.getSku());
                    if (stock != null) {
                        deptSkuQty.setInstockQty(stock.getInstockQty());
                        deptSkuQty.setIntransitQty(stock.getIntransitQty());
                        deptSkuQty.setUseQty(stock.getUsableQty());
                    }
                    deptSkuQty.setReturnQty(item.getReturnedQty());
                    deptSkuQties.add(deptSkuQty);
                }
                purchaseReturnSku.setDeptSkuQties(deptSkuQties);
                purchaseReturnSkus.add(purchaseReturnSku);
            }
            purchaseReturnSpu.setPurchaseReturnSkus(purchaseReturnSkus);
            purchaseReturnSpus.add(purchaseReturnSpu);
        }
        return purchaseReturnSpus;
    }

    /**
     * 在库采退申请操作库存
     *
     * @param purchaseReturned
     **/
    public void changeReturnLockStock(PurchaseReturned purchaseReturned, List<PurchaseReturnedItem> purchaseReturnedItems, String purchaseHandleEnum) {
        Integer wmsId = purchaseReturned.getWmsId();
        List<StockChangeReq> stockChangeReqs = new ArrayList<>();
        for (PurchaseReturnedItem purchaseReturnedItem : purchaseReturnedItems) {
            StockChangeReq stockChangeReq = getStockChangeReq(purchaseReturnedItem);
            stockChangeReq.setWmsId(wmsId);
            stockChangeReq.setChangeQty(purchaseReturnedItem.getReturnedQty());
            stockChangeReq.setType(purchaseHandleEnum);
            stockChangeReqs.add(stockChangeReq);
        }
        iStockService.purchaseChangeStockQty(stockChangeReqs);
    }

    public StockChangeReq getStockChangeReq(PurchaseReturnedItem purchaseReturnedItem) {
        StockChangeReq stockChangeReq = new StockChangeReq();
        BeanUtil.copy(purchaseReturnedItem, stockChangeReq);
        return stockChangeReq;
    }

    private StockRes findStock(Integer wmsId, Integer deptId, String sku) {
        QueryQtyReq queryQtyReq = new QueryQtyReq();
        queryQtyReq.setWmsId(wmsId);
        queryQtyReq.setDeptId(deptId);
        queryQtyReq.setSku(sku);
        StockRes stock = new StockRes();
        List<StockRes> stockRes = iStockService.queryQty(queryQtyReq);
        if (stockRes.size() > 0) {
            stock = stockRes.get(0);
        }
        return stock;
    }


    /**
     * 根据采购单和采购单明细，获取到付款类型实体
     *
     * @param purchaseReturned 采购单
     * @param returnedItems    采购明细
     * @return 返回付款类型实体
     */
    public Payable getAddPayAble(PurchaseReturned purchaseReturned, List<PurchaseReturnedItem> returnedItems) {
        Payable addPayable = new Payable();
        Assert.notNull(purchaseReturned.getAmount(), "采退单的总金额不允许为空！");
        addPayable.setAmount(purchaseReturned.getAmount());
        Assert.hasLength(purchaseReturned.getPurchaseNo(), "采购单编号不允许为空！");
        addPayable.setChangeBillNo(purchaseReturned.getPurchaseNo());
        addPayable.setChangeBillType(BillTypeEnum.purchase.ordinal());
        addPayable.setGoalBillNo(purchaseReturned.getPurchaseNo());
        addPayable.setGoalBillType(BillTypeEnum.purchase.ordinal());

        Purchase purchase = purchaseService.getByPurchaseNo(purchaseReturned.getPurchaseNo());
        Assert.notNull(purchase.getSupplierId(), "采购单的供应商不允许为空！！！");
        Supplier supplier = supplierService.getById(purchase.getSupplierId());
        Assert.notNull(supplier, "找不到该采购单对应的供应商！！！");
        Assert.notNull(supplier.getPartnerId(), "找不到该采购单对应供应商的合作伙伴id！！！");
        addPayable.setPartnerId(supplier.getPartnerId());
        addPayable.setPayAmount(purchaseReturned.getAmount());
        Assert.notNull(purchase.getPlatId(), "找不到该采购单对应的采购渠道！！！");
        addPayable.setPlatId(purchase.getPlatId());
        addPayable.setPlatName(purchase.getPlatName());
        Assert.notNull(purchaseReturned.getPlatOrdersNo(), "找不到采购单的渠道订单号！！！");
        addPayable.setPlatOrdersNo(purchaseReturned.getPlatOrdersNo());
        Assert.notNull(purchaseReturned.getBuyer(), "找不到该采购单的采购员！！！");
        addPayable.setBuyerName(purchaseReturned.getBuyer());
        Assert.notNull(purchase.getBuyerId(), "找不到该采购单的采购员id！！！");
        addPayable.setBuyerId(purchase.getBuyerId());
        Assert.notNull(purchase.getBuyerAccount(), "找不到该采购单的买手账号！！！");
        addPayable.setBuyerAccount(purchase.getBuyerAccount());
        List<PayableItem> addPayableList = new ArrayList<>();
        for (PurchaseReturnedItem purchaseItem : returnedItems) {
            PayableItem item = new PayableItem();
            item.setAmount(purchaseItem.getPurchasePrice().multiply(new BigDecimal(purchaseItem.getReturnedQty())));
            item.setDeptId(purchaseItem.getDeptId());
            item.setDeptName(purchaseItem.getDeptName());
            item.setSku(purchaseItem.getSku());
            item.setQuantity(purchaseItem.getReturnedQty());
            addPayableList.add(item);
        }
        addPayable.setPayableItemList(addPayableList);
        return addPayable;
    }

    /**
     * @param payStateNotice 支付回调的消息实体
     * @return Boolean
     */
    @Override
    public Boolean payEventWriteBack(PayStateNotice payStateNotice) {
        try {
            String billNo = payStateNotice.getBillNo();
            PurchaseReturned purchaseReturned = getByNo(billNo);
            Assert.notNull(purchaseReturned,"该采购单编号在数据库中不存在！！！");
            String payUser = payStateNotice.getUser();
            Integer payUserId = payStateNotice.getUserId();
            Assert.isTrue(StringUtils.isNotBlank(payUser) && payUserId!=null,"付款人不允许为空！");

            BigDecimal amount = purchaseReturned.getAmount();
            Assert.notNull(amount,"支付金额不允许为空！");
            String type = payStateNotice.getType();
            LocalDateTime payTime = payStateNotice.getPayTime();
            String memo = payStateNotice.getMemo();
//            PayStateEnum.paid.name().equals(type)
            if(true){
                fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.financeAffirmRefund, memo);
            }else{
                fsmService.processEvent(purchaseReturned, PurchaseReturnedEvent.cancelRefund, memo);
            }
            return true;
        } catch (Exception e) {
            logger.error("财务系统回调------------财务系统支付回调出错{}",payStateNotice,e);
            throw new RuntimeException("财务系统回调------------财务系统支付回调出错" ,e );
        }
    }

    /**
     * 根据采购退货单查询状态机历史记录
     *
     * @param id
     * @return
     */
    public RestResult queryHistory(Integer id, Integer start, Integer limit) {
        return fsmHistoryService.queryHistory("PurchaseReturned", id, start, limit);
    }
}