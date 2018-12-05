package com.stosz.purchase.service;


import com.stosz.admin.ext.model.Department;
import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.common.PurchaseSkuDto;
import com.stosz.purchase.ext.enums.PurchaseEvent;
import com.stosz.purchase.ext.enums.BillTypeEnum;
import com.stosz.purchase.ext.model.*;
import com.stosz.purchase.ext.model.finance.PayStateNotice;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.ext.model.finance.PayableItem;
import com.stosz.purchase.ext.service.IPurchaseService;
import com.stosz.purchase.mapper.PurchaseMapper;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.enums.PurchaseHandleEnum;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 采购单的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
@Service
public class PurchaseService implements IPurchaseService {

    @Resource
    private PurchaseMapper mapper;
    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private FsmProxyService<Purchase> purchaseFsmProxyService;
    @Resource
    private IProductService iProductService;
    @Resource
    private FsmHistoryService purchaseFsmHistoryService;
    @Resource
    private WmsPurchaseService wmsPurchaseService;
    @Resource
    private UserAuthorityService userAuthorityService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private SkuSupplierService skuSupplierService;
    @Resource
    private PurchaseTrackingNoService purchaseTrackingNoService;
    @Resource
    private IStockService iStockService;
    @Resource
    private PurchaseTrackingNoRelService purchaseTrackingNoRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加采购单
     *
     * @param purchaseDto 采购单和采购明细
     * @return 生成结果
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String insert(PurchaseDto purchaseDto) {
        Purchase purchase = purchaseDto.getPurchase();
        List<PurchaseItem> purchaseItemList = purchaseDto.getItems();
        //  根据采购单号生成规则生成对应的采购单号
        mapper.insert(purchase);
        Integer totalPurchaseQty = 0;
        Integer totalAvgSaleQty = 0;
        Integer totalPendingOrderQty = 0;
        // 创建采购明细单
        for (PurchaseItem purchaseItem : purchaseItemList) {
            purchaseItem.setPurchaseId(purchase.getId());
            Integer purchaseQty = purchaseItem.getPurchaseQty();
            Integer avgSaleQty = purchaseItem.getAvgSaleQty();
            Integer pendingOrderQty = purchaseItem.getPendingOrderQty();
            if (purchaseQty > 0) {
                totalPurchaseQty += purchaseQty;
            }
            if (avgSaleQty > 0) {
                totalAvgSaleQty += avgSaleQty;
            }
            if (pendingOrderQty > 0) {
                totalPendingOrderQty += pendingOrderQty;
            }
            purchaseItem.setPurchaseNo(purchase.getId()+"");
            purchaseItemService.insert(purchaseItem);
        }
        purchase.setPurchaseQty(totalPurchaseQty);
        purchase.setAvgSaleQty(totalAvgSaleQty);
        purchase.setPendingOrderQty(totalPendingOrderQty);
        //  根据采购单号生成规则生成对应的采购单号
        purchase.setPurchaseNo(purchase.getId() + "");
        purchaseFsmProxyService.processNew(purchase, purchase.getMemo());
        purchaseFsmProxyService.processEvent(purchase, PurchaseEvent.create, purchase.getMemo());
        mapper.update(purchase);
        logger.info("创建采购单{}成功！", purchase);
        return purchase.getPurchaseNo();
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Assert.notNull(id, "要删除的采购单id不允许为空！");
        Purchase purchase = getById(id);
        Assert.notNull(purchase, "要删除的采购单在数据库中不存在！");
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(id);
        Assert.notNull(purchaseItemList, "找不到该采购单对应的明细");
        for (PurchaseItem purchaseItem : purchaseItemList) {
            purchaseItemService.delete(purchaseItem.getId());
        }
        mapper.delete(id);
        logger.info("删除采购单{}成功！", purchase);
    }

    /**
     * 修改采购单
     *
     * @param purchaseDto 要修改的采购单
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDto(PurchaseDto purchaseDto) {
        Assert.notNull(purchaseDto, "采购单不允许为空！");
        Purchase purchase = purchaseDto.getPurchase();
        Integer purchaseId = purchase.getId();
        //获取到当前数据库的采购单
        Purchase purchaseDB = getById(purchaseId);
        Assert.notNull(purchaseDB, "该采购单在数据库中不存在！");
        List<PurchaseItem> purchaseItemDBList = purchaseItemService.findByPurchaseId(purchaseId);
        int i = mapper.update(purchase);
        Assert.isTrue(i == 1, "修改失败！");
        logger.info("修改采购单{}为{}成功！", purchaseDB, purchase);
        List<PurchaseItem> purchaseItemList = purchaseDto.getItems();
        Assert.notNull(purchaseItemList, "找不到该采购单对应的明细！");
        BigDecimal amount = purchaseDto.getAmount();
        BigDecimal updateAmount = BigDecimal.ZERO;
        for (PurchaseItem purchaseItem : purchaseItemList) {
            Integer quantity = purchaseItem.getQuantity();
            BigDecimal priceAmount = purchaseItem.getPrice();
            Assert.notNull(priceAmount,"采购单价不允许为空！");
            BigDecimal total = priceAmount.multiply(BigDecimal.valueOf(quantity));
            updateAmount = updateAmount.add(total);
            purchaseItemService.update(purchaseItem);
        }
        Assert.isTrue(Math.abs(updateAmount.doubleValue() - amount.doubleValue()) < 1,"单价*数量不等于总价，请检查！");
        //通知仓库修改在途数
        notifyStock(purchaseItemList, purchaseItemDBList, purchaseDB);
        logger.info("修改采购单明细成功！");
    }

    /**
     * 操作采购单的状态流转
     *
     * @param purchase      采购单
     * @param purchaseEvent 将触发的状态机时间
     * @param memo          备注
     * @param uid           用户id
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processEvent(Purchase purchase, PurchaseEvent purchaseEvent, String memo, String uid) {
        Assert.notNull(purchase, "传入的id不允许为空！");
        if (StringUtils.isBlank(uid)) {
            uid = MBox.getSysUser();
        }
        this.purchaseFsmProxyService.processEvent(purchase, purchaseEvent, uid, LocalDateTime.now(), memo);
    }

    /**
     * 修改采购单提交时间为当前时间
     *
     * @param purchase 要修改的采购单
     */
    public void updateSubmitTime(Purchase purchase) {
        Assert.notNull(purchase, "要修改的采购单不能为空！");
        Integer purchaseId = purchase.getId();
        Purchase purchaseDB = getById(purchaseId);
        Assert.notNull(purchaseDB, "要修改的采购单在数据库中不存在！");
        mapper.updateTime(purchase);
        logger.info("修改采购单{}的提交时间为当前时间！", purchase);
    }

    /**
     * 修改采购单的审核时间和审核人
     *
     * @param purchase 要修改的采购单
     */
    public void updateAuditTime(Purchase purchase) {
        Assert.notNull(purchase, "要修改的采购单不能为空！");
        Integer purchaseId = purchase.getId();
        Purchase purchaseDB = getById(purchaseId);
        Assert.notNull(purchaseDB, "要修改的采购单在数据库中不存在！");
        purchase.setAuditor(MBox.getLoginUserName());
        purchase.setAuditorId(MBox.getLoginUserId());
        mapper.updateAuditTime(purchase);
        logger.info("修改采购单{}的财务审核时间为当前时间！", purchase);
    }

    /**
     * 修改采购单的支付时间和支付人
     *
     * @param purchase 要修改的采购单
     */
    public void updatePayTime(Purchase purchase) {
        Assert.notNull(purchase, "要修改的采购单不能为空！");
        Integer purchaseId = purchase.getId();
        Purchase purchaseDB = getById(purchaseId);
        Assert.notNull(purchaseDB, "要修改的采购单在数据库中不存在！");
        mapper.updatePayTime(purchase);
        logger.info("修改采购单{}的财务付款时间为当前时间！", purchase);
    }

    /**
     * 根据采购单id获取到采购单信息
     *
     * @param id 采购单id
     * @return 采购单
     */
    public Purchase getById(Integer id) {
        Assert.notNull(id, "采购单id不允许为空！");
        return mapper.getDtoById(id);
    }

    /**
     * 根据采购单号获取采购单
     *
     * @param purchaseNo 采购单号
     * @return 采购单
     */
    public Purchase getByPurchaseNo(String purchaseNo) {
        Assert.hasLength(purchaseNo, "传入的采购单号不允许为空！");
        return mapper.getByPurchaseNo(purchaseNo);
    }

    /**
     * 根据查询条件获取到筛选后的采购单
     *
     * @param param 筛选条件
     * @return 筛选结果
     */
    public List<Purchase> find(Purchase param) {
        Assert.notNull(param, "查询条件不允许为null");
        List<Department> departmentList = userAuthorityService.findBuDeptList();
        //判断该用户是否有权限访问部门，如果获取的部门列表为空，那么直接抛错；
        if (CollectionUtils.isNullOrEmpty(departmentList)) {
            throw new RuntimeException("您没有配置任何访问采购单的数据权限，如要访问，请联系管理员！！！");
        }
        List<Integer> buDeptIdList = departmentList.stream().map(Department::getId).collect(Collectors.toList());
        Integer buDeptId = param.getBuDeptId();
        if (buDeptId != null && !buDeptIdList.contains(buDeptId)) {
            throw new RuntimeException("您目前没有权限查询该部门的信息，如要访问，请联系管理员！！！");
        }
        //如果搜索条件中带了部门，那么不带数据权限的搜索
        if (buDeptId != null) {
            buDeptIdList = new ArrayList<>();
        }
        List<Purchase> purchaseList = mapper.findByParam(param, buDeptIdList);
        Assert.notNull(purchaseList, "查询采购单列表出现错误！");
        Map<Integer, Product> purchaseSpu = purchaseItemService.findSpu(purchaseList);
        Map<String, String> purchaseTrackingNo = purchaseTrackingNoRelService.getTrackingNoByPurchaseNo(purchaseList);
        for (Purchase purchase : purchaseList) {
            Integer purchaseId = purchase.getId();
            String purchaseNo = purchase.getPurchaseNo();
            String trackingNo = purchaseTrackingNo.get(purchaseNo);
            purchase.setTrackingNo(trackingNo);
            Product product = purchaseSpu.get(purchaseId);
            if (product == null) {
                product = new Product();
                product.setTitle("未找到对应的产品信息！！！");
            }
            purchase.setProduct(product);
        }
        return purchaseList;
    }

    /**
     * 根据查询条件获取到筛选后的采购单数
     *
     * @param param 筛选条件
     * @return 筛选结果
     */
    public Integer count(Purchase param) {
        Assert.notNull(param, "查询条件不允许为null");
        List<Department> departmentList = userAuthorityService.findBuDeptList();
        //判断该用户是否有权限访问部门，如果获取的部门列表为空，那么直接抛错；
        if (CollectionUtils.isNullOrEmpty(departmentList)) {
            throw new RuntimeException("您没有配置任何访问采购单的数据权限，如要访问，请联系管理员！！！");
        }
        List<Integer> buDeptIdList = departmentList.stream().map(Department::getId).collect(Collectors.toList());
        Integer buDeptId = param.getBuDeptId();
        if (buDeptId != null && !buDeptIdList.contains(buDeptId)) {
            throw new RuntimeException("您目前没有权限查询该部门的信息，如要访问，请联系管理员！！！");
        }
        //如果搜索条件中带了部门，那么不带数据权限的搜索
        if (buDeptId != null) {
            buDeptIdList = new ArrayList<>();
        }
        return mapper.count(param, buDeptIdList);
    }

    public PurchaseDto getPurchaseDtoById(Integer purchaseId) {
        PurchaseDto purchaseDto = new PurchaseDto();
        Purchase purchase = getById(purchaseId);
        String spu = purchaseItemService.getPurchaseSpu(purchaseId);
        Assert.notNull(spu, "该采购单查询不到对应的spu！");
        Product product = iProductService.getBySpu(spu);
        Assert.notNull(product, "该采购单找不到对应的产品！");
        Assert.notNull(purchase, "该采购单在数据库中不存在！");
        purchaseDto.setPurchase(purchase);
        purchaseDto.setProduct(product);
        List<PurchaseSkuDto> purchaseSkuDtoList = purchaseItemService.findSkuDtoByPurchaseId(purchaseId);
        purchaseDto.setPurchaseSkuDtoList(purchaseSkuDtoList);
        return purchaseDto;
    }

    public PurchaseDto getPurchaseDtoByNo(String purchaseNo) {
        PurchaseDto purchaseDto = new PurchaseDto();
        Purchase purchase = getByPurchaseNo(purchaseNo);
        Assert.notNull(purchase, "未查询到对应的采购单！");
        String spu = purchaseItemService.getPurchaseSpu(purchase.getId());
        Assert.notNull(spu, "该采购单查询不到对应的spu！");
        Product product = iProductService.getBySpu(spu);
        Assert.notNull(product, "该采购单找不到对应的产品！");
        Assert.notNull(purchase, "该采购单在数据库中不存在！");
        purchaseDto.setPurchase(purchase);
        purchaseDto.setProduct(product);
        List<PurchaseSkuDto> purchaseSkuDtoList = purchaseItemService.findSkuDtoByPurchaseId(purchase.getId());
        purchaseDto.setPurchaseSkuDtoList(purchaseSkuDtoList);
        return purchaseDto;
    }

    /**
     * 根据采购单和采购单明细，获取到付款类型实体
     *
     * @param purchase      采购单
     * @param purchaseItems 采购明细
     * @return 返回付款类型实体
     */
    public Payable getAddPayAble(Purchase purchase, List<PurchaseItem> purchaseItems) {
        Payable addPayable = new Payable();
        Assert.notNull(purchase.getAmount(), "采购单的总金额不允许为空！");
        addPayable.setAmount(purchase.getAmount());
        Assert.hasLength(purchase.getPurchaseNo(), "采购单编号不允许为空！");
        addPayable.setChangeBillNo(purchase.getPurchaseNo());
        addPayable.setChangeBillType(BillTypeEnum.purchase.ordinal());
        addPayable.setGoalBillNo(purchase.getPurchaseNo());
        addPayable.setGoalBillType(BillTypeEnum.purchase.ordinal());
        Assert.notNull(purchase.getSupplierId(), "采购单的供应商不允许为空！！！");
        Supplier supplier = supplierService.getById(purchase.getSupplierId());
        Assert.notNull(supplier, "找不到该采购单对应的供应商！！！");
        Assert.notNull(supplier.getPartnerId(), "找不到该采购单对应供应商的合作伙伴id！！！");
        addPayable.setPartnerId(supplier.getPartnerId());
        addPayable.setPayAmount(purchase.getAmount());
        Assert.notNull(purchase.getPlatId(), "找不到该采购单对应的采购渠道！！！");
        addPayable.setPlatId(purchase.getPlatId());
        addPayable.setPlatName(purchase.getPlatName());
        Assert.notNull(purchase.getPlatOrdersNo(), "找不到采购单的渠道订单号！！！");
        addPayable.setPlatOrdersNo(purchase.getPlatOrdersNo());
        Assert.notNull(purchase.getBuyer(), "找不到该采购单的采购员！！！");
        addPayable.setBuyerName(purchase.getBuyer());
        Assert.notNull(purchase.getBuyerId(), "找不到该采购单的采购员id！！！");
        addPayable.setBuyerId(purchase.getBuyerId());
        Assert.notNull(purchase.getBuyerAccount(), "找不到该采购单的采购买手账号！！！");
        addPayable.setBuyerAccount(purchase.getBuyerAccount());
        addPayable.setQuantity(purchase.getQuantity());
        List<PayableItem> addPayableList = new ArrayList<>();
        for (PurchaseItem purchaseItem : purchaseItems) {
            PayableItem item = new PayableItem();
            item.setAmount(purchaseItem.getAmount());
            item.setDeptId(purchaseItem.getDeptId());
            item.setDeptName(purchaseItem.getDeptName());
            item.setSku(purchaseItem.getSku());
            item.setQuantity(purchaseItem.getQuantity());
            addPayableList.add(item);
        }
        addPayable.setPayableItemList(addPayableList);
        return addPayable;
    }


    @Override
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean payEventWriteBack(PayStateNotice payStateNotice) {
        try {
            String billNo = payStateNotice.getBillNo();
            Purchase purchaseDB = getByPurchaseNo(billNo);
            Assert.notNull(purchaseDB, "该采购单编号在数据库中不存在！！！");
            String payUser = payStateNotice.getUser();
            Integer payUserId = payStateNotice.getUserId();
            Assert.isTrue(StringUtils.isNotBlank(payUser) && payUserId != null, "付款人不允许为空！");
            BigDecimal amount = purchaseDB.getAmount();
            Assert.notNull(amount, "支付金额不允许为空！");
            String type = payStateNotice.getType();
            LocalDateTime payTime = payStateNotice.getPayTime();
            String memo = payStateNotice.getMemo();
            purchaseDB.setPayerId(payUserId);
            purchaseDB.setPayer(payUser);
            purchaseDB.setPaymentTime(payTime);
            PurchaseEvent purchaseEvent = null;
//            PayStateEnum.paid.name().equals(type);
            if (true) {
                purchaseEvent = PurchaseEvent.pay;
            } else {
                purchaseEvent = PurchaseEvent.refusePay;
            }
            this.purchaseFsmProxyService.processEvent(purchaseDB, purchaseEvent, memo, LocalDateTime.now(), null);
            return true;
        } catch (Exception e) {
            logger.error("财务系统回调------------财务系统支付回调出错{}", payStateNotice, e);
            throw new RuntimeException("财务系统回调------------财务系统支付回调出错", e);
        }
    }

    //采购单改变，通知仓库在途数改变
    private void notifyStock(List<PurchaseItem> purchaseItemList, List<PurchaseItem> purchaseItemDBList, Purchase purchase) {
        List<StockChangeReq> stockChangeReqList = new ArrayList<>();
        Map<Integer,PurchaseItem> purchaseItemMap = purchaseItemList.stream().collect(Collectors.toMap(PurchaseItem::getId,purchaseItem -> purchaseItem));
        for (PurchaseItem purchaseItem : purchaseItemDBList) {
            StockChangeReq stockChangeReq = new StockChangeReq();
            StockChangeReq updateStockChangeReq = new StockChangeReq();
            PurchaseItem updatePurchaseItem = purchaseItemMap.get(purchaseItem.getId());
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
            if(updatePurchaseItem !=null){
                updateStockChangeReq.setChangeQty(updatePurchaseItem.getQuantity());
                updateStockChangeReq.setType(PurchaseHandleEnum.intransit_add.name());
                updateStockChangeReq.setDeptId(purchaseItem.getDeptId());
                updateStockChangeReq.setWmsId(purchase.getWmsId());
                updateStockChangeReq.setSku(purchaseItem.getSku());
                updateStockChangeReq.setDeptName(purchaseItem.getDeptName());
                updateStockChangeReq.setSpu(purchaseItem.getSpu());
                updateStockChangeReq.setAmount(updatePurchaseItem.getAmount());
                updateStockChangeReq.setVoucherNo(purchase.getPurchaseNo());
                updateStockChangeReq.setChangeAt(LocalDateTime.now());
                stockChangeReqList.add(updateStockChangeReq);
            }
        }
        iStockService.purchaseChangeStockQty(stockChangeReqList);
    }

    /**
     * 根据采购单id获取其可以选择的供应商
     * @param id
     * @return
     */
    public List<SkuPurchaseInfo> querySupplierByPurchaseId(Integer id){
        Set<String> skuList = purchaseItemService.findSkuByPurchaseId(id);
        List<SkuPurchaseInfo> skuPurchaseInfoList = skuSupplierService.querySkuSupplier(skuList);
        // 找出最小的采购价格 设置标志
        skuPurchaseInfoList.stream().min((src, dest) -> {
            BigDecimal srcPurchasePrice = StringUtil.nvl(src.getLastPurchasePrice(), new BigDecimal(0));
            BigDecimal destPurchasePrice = StringUtil.nvl(dest.getLastPurchasePrice(), new BigDecimal(0));
            return srcPurchasePrice.compareTo(destPurchasePrice);
        }).ifPresent(target -> {
            target.setLowestFlag(1);
        });
        return skuPurchaseInfoList;
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSkuSupplier(Integer id ,Integer supplierId){
        Set<String> skuList = purchaseItemService.findSkuByPurchaseId(id);
        skuSupplierService.delSkuPurchaseInfoByPurchaseId(supplierId, skuList);
        logger.info("删除供应商{}与sku关联关系{}成功！",supplierId,skuList);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addSkuSupplier(Integer id , Integer supplierId, String supplierName){
        Assert.notNull(id,"采购单id不允许为空!");
        Assert.hasLength(supplierName,"供应商名称不允许为空！");
        Set<String> skuList = purchaseItemService.findSkuByPurchaseId(id);
        for (String sku: skuList
             ) {
            SkuPurchaseInfo skuPurchaseInfo = new SkuPurchaseInfo();
            skuPurchaseInfo.setSupplierId(supplierId);
            skuPurchaseInfo.setSupplierName(supplierName);
            skuPurchaseInfo.setSku(sku);
            skuSupplierService.addSupplier(skuPurchaseInfo);
            logger.info("新增sku与供应商绑定关系{}成功！",skuPurchaseInfo);
        }
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSupplier(Integer id , Integer supplierId){
        Assert.notNull(id,"采购单id不允许为空!");
        Assert.notNull(supplierId,"供应商id不允许为空!");
        Purchase purchaseDB = mapper.getById(id);
        Assert.notNull(purchaseDB,"数据库中找不到对应的采购单！");
        int i = mapper.updateSupplier(id,supplierId);
        Assert.isTrue(i==1,"修改采购单对应的供应商失败！");
        logger.info("将采购单{}供应商修改为{} 成功！",purchaseDB,supplierId);
    }


    /**
     * 根据采购单查询状态机历史记录
     *
     * @param id
     * @return
     */
    public RestResult queryHistory(Integer id, Integer start, Integer limit) {
        return purchaseFsmHistoryService.queryHistory("Purchase", id, start, limit);
    }

}

