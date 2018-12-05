package com.stosz.purchase.fsm.errorGoodsFsm;

import com.stosz.fsm.handle.IFsmHandler;
import com.stosz.fsm.model.EventModel;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.purchase.ext.common.CreateReturnDetailDto;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.enums.ErrorGoodsEvent;
import com.stosz.purchase.ext.enums.PurchaseItemState;
import com.stosz.purchase.ext.enums.PurchaseState;
import com.stosz.purchase.ext.enums.ReturnedTypeEnum;
import com.stosz.purchase.ext.model.*;
import com.stosz.purchase.ext.model.finance.Payable;
import com.stosz.purchase.service.*;
import com.stosz.purchase.service.finance.PayableService;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2018/1/8]
 */
@Component
public class AgreeErrorGoodsAfter extends IFsmHandler<ErrorGoods>{

    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private PurchaseReturnedService purchaseReturnedService;
    @Resource
    private PurchaseReturnedItemService purchaseReturnedItemService;
    @Resource
    private ErrorGoodsService errorGoodsService;
    @Resource
    private ErrorGoodsItemService errorGoodsItemService;
    @Resource
    private SupplierService supplierService;
    @Resource
    private PayableService payableService;
    @Resource
    private IStockService iStockService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void execute(ErrorGoods errorGoods, EventModel event) {
        // 生成采购单
        Integer errorId = errorGoods.getId();
        ErrorGoods errorGoodsDB = errorGoodsService.getById(errorId);
        Assert.notNull(errorGoodsDB,"数据库中没有"+errorGoods+"这个错货单！！！");
        PurchaseDto purchaseDto = getPurchase(errorGoodsDB);
        String purchaseNo = purchaseService.insert(purchaseDto);
        Purchase purchase = purchaseService.getByPurchaseNo(purchaseNo);
        Assert.notNull(purchase,"数据库中不存在该采购单！");
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchase.getId());
        Payable purchaseAddPayAble = purchaseService.getAddPayAble(purchase,purchaseItemList);
        List<Payable> addPayableList = new ArrayList<>();
        addPayableList.add(purchaseAddPayAble);
        logger.info("错货单生成此采购单成功！！！");
        // 生成采退单
        CreateReturnDto purchaseReturnDto = getPurchaseReturned(errorGoodsDB);
        String returnedNo = purchaseReturnedService.createPurchaseReturn(purchaseReturnDto);
        Assert.hasLength(purchaseNo,"生成的采购单号不允许为空！");
        Assert.hasLength(returnedNo,"生成的采退单号不允许为空！");
        PurchaseReturned purchaseReturned = purchaseReturnedService.getByNo(returnedNo);
        Assert.notNull(purchaseReturned,"数据库中不存在该采退单！");
        List<PurchaseReturnedItem> purchaseReturnedItemList = purchaseReturnedItemService.queryListByReturnId(purchaseReturned.getId());
        errorGoodsDB.setPurchaseReturnedNo(returnedNo);
        errorGoodsDB.setNewPurchaseNo(purchaseNo);


        //将采购单和采购退货单同时推送到财务系统
        Payable returnAddPayAble = purchaseReturnedService.getAddPayAble(purchaseReturned,purchaseReturnedItemList);
        addPayableList.add(returnAddPayAble);
        payableService.addPayableList(addPayableList);

        errorGoodsService.processEvent(errorGoodsDB, ErrorGoodsEvent.completing,"成功创建采购单和采购退货单，错货单完成！",null);
    }

    /**
     * 生成采购单
     * @param errorGoods 错货单
     * @return 采购单
     */
    private PurchaseDto getPurchase(ErrorGoods errorGoods){
        Purchase purchase = purchaseService.getByPurchaseNo(errorGoods.getOriginalPurchaseNo());
        PurchaseDto purchaseDto  = new PurchaseDto();
        purchaseDto.setBuDeptId(errorGoods.getBuDeptId());
        purchaseDto.setBuDeptNo(errorGoods.getBuDeptName());
        purchaseDto.setBuDeptNo(errorGoods.getBuDeptNo());
        purchaseDto.setWmsSysCode(errorGoods.getWmsSysCode());
        purchaseDto.setWmsId(errorGoods.getWmsId());
        purchaseDto.setWmsName(errorGoods.getWmsName());
        purchaseDto.setSupplierId(errorGoods.getSupplierId());
        purchaseDto.setBuyer(errorGoods.getCreator());
        purchaseDto.setBuyerId(errorGoods.getCreatorId());
        purchaseDto.setAmount(errorGoods.getAmount());
        purchaseDto.setQuantity(errorGoods.getQuantity());
        purchaseDto.setSourceType(1);
        Integer supplierId = errorGoods.getSupplierId();
        Supplier supplier = supplierService.getById(supplierId);
        purchaseDto.setPlatId(supplier.getPlatId());
        purchaseDto.setPlatName(supplier.getPlatName());
        purchaseDto.setCreator("System");
        purchaseDto.setCreatorId(0);
        purchaseDto.setState(PurchaseState.start.name());
        purchaseDto.setPendingOrderQty(purchase.getPendingOrderQty() == null? 0 : purchase.getPendingOrderQty());
        purchaseDto.setPurchaseQty(purchase.getPurchaseQty() == null ? 0 : purchase.getPurchaseQty());
        purchaseDto.setAvgSaleQty(purchase.getAvgSaleQty() == null ? 0 : purchase.getAvgSaleQty());
        purchaseDto.setMemo("该采购单由自动"+errorGoods.getId()+"错货单生成！！！");
        List<ErrorGoodsItem> errorGoodsItemList = errorGoodsItemService.findByErrorId(errorGoods.getId());
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(errorGoodsItemList),"错货单【"+errorGoods.getId()+"】没有错货明细，不合法！！！");
        List<PurchaseItem> purchaseItemList = new ArrayList<>();
        for(ErrorGoodsItem errorGoodsItem: errorGoodsItemList){
            String spu = errorGoodsItem.getSpu();
            String realSku = errorGoodsItem.getRealSku();
            String realSkuTitle = errorGoodsItem.getRealSkuTitle();
            BigDecimal price = errorGoodsItem.getPrice();
            Integer quantity = errorGoodsItem.getQuantity();
            BigDecimal amount = errorGoodsItem.getAmount();
            String productTitle = errorGoodsItem.getProductTitle();
            String deptNo = errorGoodsItem.getDeptNo();
            String deptName = errorGoodsItem.getDeptName();
            Integer deptId = errorGoodsItem.getDeptId();
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setSpu(spu);
            purchaseItem.setSku(realSku);
            purchaseItem.setProductTitle(productTitle);
            purchaseItem.setPurchaseQty(0);
            purchaseItem.setPendingOrderQty(0);
            purchaseItem.setAvgSaleQty(0);
            purchaseItem.setSkuTitle(realSkuTitle);
            purchaseItem.setQuantity(quantity);
            purchaseItem.setPrice(price);
            purchaseItem.setAmount(amount);
            purchaseItem.setDeptName(deptName);
            purchaseItem.setDeptId(deptId);
            purchaseItem.setDeptNo(deptNo);
            purchaseItem.setState(PurchaseItemState.start.name());
            purchaseItemList.add(purchaseItem);
        }
        purchaseDto.setItems(purchaseItemList);
        return purchaseDto;
    }

    private CreateReturnDto getPurchaseReturned(ErrorGoods errorGoods){
        Purchase purchase = purchaseService.getByPurchaseNo(errorGoods.getOriginalPurchaseNo());
        CreateReturnDto purchaseReturnDto = new CreateReturnDto();
        purchaseReturnDto.setRefundAddress(errorGoods.getSupplierName());
        purchaseReturnDto.setReturnType(ReturnedTypeEnum.WRONGGOODS.ordinal());
        purchaseReturnDto.setRefundAmount(errorGoods.getAmount());
        purchaseReturnDto.setPayAmount(errorGoods.getAmount());
        purchaseReturnDto.setPurchaseId(purchase.getId());
        purchaseReturnDto.setMemo("错货单自动生成的采购退货单！！！");
        List<CreateReturnDetailDto> createReturnDetailDtos = new ArrayList<>();
        List<ErrorGoodsItem> errorGoodsItemList = errorGoodsItemService.findByErrorId(errorGoods.getId());
        for(ErrorGoodsItem errorGoodsItem: errorGoodsItemList){
            CreateReturnDetailDto createReturnDetailDto = new CreateReturnDetailDto();
            createReturnDetailDto.setPurchaseItemId(errorGoodsItem.getOriginalPurchaseItemId());
            createReturnDetailDto.setReturnedQty(errorGoodsItem.getQuantity());
            createReturnDetailDtos.add(createReturnDetailDto);
        }
        purchaseReturnDto.setReturnDetails(createReturnDetailDtos);
        return purchaseReturnDto;
    }
}
