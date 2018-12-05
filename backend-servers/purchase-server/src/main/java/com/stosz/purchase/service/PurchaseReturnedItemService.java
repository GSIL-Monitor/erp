package com.stosz.purchase.service;

import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.purchase.ext.common.CreateReturnDetailDto;
import com.stosz.purchase.ext.common.CreateReturnDto;
import com.stosz.purchase.ext.enums.PurchaseReturnItemState;
import com.stosz.purchase.ext.enums.PurchaseReturnedItemEvent;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseReturned;
import com.stosz.purchase.ext.model.PurchaseReturnedItem;
import com.stosz.purchase.mapper.PurchaseReturnedItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 采退明细Service
 * @author feiheping
 * @version [1.0,2017年11月22日]
 */
@Service
public class PurchaseReturnedItemService {

    @Autowired
    private PurchaseReturnedItemMapper returnedItemMapper;

    @Autowired
    private PurchaseItemService purchaseItemService;

    @Autowired
    private FsmProxyService<PurchaseReturnedItem> fsmProxyService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 创建采退明细
     * @param returnedItems 
     * @return
     */
    public int addList(List<PurchaseReturnedItem> returnedItems) {
        int count = returnedItemMapper.addList(returnedItems);
        if (count > 0) {
            for (PurchaseReturnedItem purchaseReturnedItem : returnedItems) {
                fsmProxyService.processNew(purchaseReturnedItem, purchaseReturnedItem.getMemo());
                fsmProxyService.processEvent(purchaseReturnedItem, PurchaseReturnedItemEvent.create, purchaseReturnedItem.getMemo());
            }
        }
        return count;
    }

    /**
     * 取消采购退货明细
     * @param returnedItemId
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public PurchaseReturnedItem findById(Integer returnedItemId) {
        return returnedItemMapper.getById(returnedItemId);
    }

    /**
     * 取消采购退货明细
     * @param returnedItems
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public void cancelReturnedItems(List<PurchaseReturnedItem> returnedItems, String memo, String uid) {
        for (PurchaseReturnedItem returnedItem : returnedItems) {
            fsmProxyService.processEvent(returnedItem, PurchaseReturnedItemEvent.cancelRefund, memo, LocalDateTime.now(), uid);
        }
    }

    /**
     * 采购退货->财务确认退款->明细状态变更
     * @param returnedId  采退单ID
     * @param purchaseReturnedItemEvent
     */
    @Transactional(propagation = Propagation.REQUIRED, transactionManager = "purchaseTransactionManager", rollbackFor = Exception.class)
    public void confirmFinanceRefundItem(Integer returnedId, PurchaseReturnedItemEvent purchaseReturnedItemEvent) {
        List<PurchaseReturnedItem> returnedItems = returnedItemMapper.queryListByReturnId(returnedId);
        Assert.notEmpty(returnedItems, "采退明细不能为空");
        for (PurchaseReturnedItem returnedItem : returnedItems) {
            fsmProxyService.processEvent(returnedItem, purchaseReturnedItemEvent, returnedItem.getMemo());
        }
    }

    public List<PurchaseReturnedItem> transferItemList(PurchaseReturned purchaseReturned, CreateReturnDto createReturnDto) {
        List<PurchaseItem> purchaseItems = purchaseItemService.findByPurchaseId(createReturnDto.getPurchaseId());
        Map<Integer, PurchaseItem> purchaseItemMap = purchaseItems.stream().collect(Collectors.toMap(PurchaseItem::getId, Function.identity()));

        List<CreateReturnDetailDto> returnDetailDtos = createReturnDto.getReturnDetails();
        List<PurchaseReturnedItem> returnedItems = returnDetailDtos.stream().filter(item -> item.getReturnedQty() > 0).map(item -> {
            PurchaseReturnedItem returnedItem = new PurchaseReturnedItem();
            returnedItem.setReturnedId(purchaseReturned.getId());
            returnedItem.setPurchaseItemId(item.getPurchaseItemId());
            PurchaseItem purchaseItem = purchaseItemMap.get(item.getPurchaseItemId());

            returnedItem.setDeptId(purchaseItem.getDeptId());
            returnedItem.setDeptName(purchaseItem.getDeptName());
            returnedItem.setDeptNo(purchaseItem.getDeptNo());
            returnedItem.setSpu(purchaseItem.getSpu());
            returnedItem.setSku(purchaseItem.getSku());
            returnedItem.setPurchaseQty(purchaseItem.getQuantity());
            returnedItem.setPurchasePrice(purchaseItem.getPrice());
            returnedItem.setReturnedQty(item.getReturnedQty());
            returnedItem.setCreator(purchaseReturned.getCreator());
            returnedItem.setCreatorId(purchaseReturned.getCreatorId());
            returnedItem.setState(PurchaseReturnItemState.start.name());
            returnedItem.setStateTime(LocalDateTime.now());
            returnedItem.setMemo(createReturnDto.getMemo());
            return returnedItem;
        }).collect(Collectors.toList());
        return returnedItems;
    }

    /**
     * 获取采退明细
     * @param returnId 采退单ID
     * @return
     */
    public List<PurchaseReturnedItem> queryListByReturnId(Integer returnId) {
        return returnedItemMapper.queryListByReturnId(returnId);
    }

    public List<PurchaseReturnedItem> findByNoAndSku(Integer returnedId, String sku){
        Assert.notNull(returnedId,"采购退货单不允许为空！");
        Assert.hasLength(sku,"sku不允许为空！");
        return returnedItemMapper.findByNoAndSku(returnedId,sku);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processEvent(PurchaseReturnedItem purchaseReturnedItem, PurchaseReturnedItemEvent purchaseReturnedItemEvent, String memo, String uid) {
        Assert.notNull(purchaseReturnedItemEvent, "采购退货明细不允许为空！");
        if (StringUtils.isBlank(uid)) {
            uid = MBox.getSysUser();
        }
        this.fsmProxyService.processEvent(purchaseReturnedItem, purchaseReturnedItemEvent, memo, LocalDateTime.now(), uid);
    }

    public void update(PurchaseReturnedItem purchaseReturnedItem){
        Assert.notNull(purchaseReturnedItem,"要修改的采购退货明细不允许为空！");
        Integer id = purchaseReturnedItem.getId();
        PurchaseReturnedItem purchaseReturnedItemDB = returnedItemMapper.getById(id);
        Assert.notNull(purchaseReturnedItemDB,"要修改的采购退货明细在数据中不存在！");
        returnedItemMapper.update(purchaseReturnedItem);
        logger.info("将采购退货明细{}修改为{}成功！",purchaseReturnedItemDB,purchaseReturnedItem);
    }
}
