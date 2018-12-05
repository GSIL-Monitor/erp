package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.PurchaseInItem;
import com.stosz.purchase.mapper.PurchaseInItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 采购入库明细的service
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
@Service
public class PurchaseInItemService {

    @Resource
    private PurchaseInItemMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新增采购明细
     * @param purchaseInItem 采购明细
     */
    public void insert(PurchaseInItem purchaseInItem) {
        Assert.notNull(purchaseInItem, "采购明细不允许为空！");
        mapper.insert(purchaseInItem);
        logger.info("添加采购入库明细{}成功！", purchaseInItem);
    }

    /**
     * 删除采购明细
     * @param id id
     */
    public void delete(Integer id) {
        Assert.notNull(id, "要删除的采购明细id不允许为空！");
        PurchaseInItem purchaseInItem = getById(id);
        Assert.notNull(purchaseInItem, "要删除的采购明细在数据库中找不到！");
        mapper.delete(id);
        logger.info("删除采购明细{}成功！", purchaseInItem);
    }

    /**
     * 修改采购明细
     * @param purchaseInItem 修改明细
     */
    public void update(PurchaseInItem purchaseInItem) {
        Assert.notNull(purchaseInItem, "采购明细不允许为空！");
        Integer purchaseInItemId = purchaseInItem.getId();
        PurchaseInItem purchaseInItemDB = mapper.getById(purchaseInItemId);
        Assert.notNull(purchaseInItemDB, "采购明细【" + purchaseInItemId + "】在数据库中不存在！");
        mapper.update(purchaseInItem);
        logger.info("将采购明细{}修改为{}成功！", purchaseInItemDB, purchaseInItem);
    }

    public PurchaseInItem getById(Integer id) {
        Assert.notNull(id, "查询采购明细，采购单id不允许为空！");
        return mapper.getById(id);
    }

}
