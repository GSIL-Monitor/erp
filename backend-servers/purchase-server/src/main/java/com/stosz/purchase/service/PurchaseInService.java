package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseIn;
import com.stosz.purchase.ext.model.PurchaseInItem;
import com.stosz.purchase.mapper.PurchaseInItemMapper;
import com.stosz.purchase.mapper.PurchaseInMapper;
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
public class PurchaseInService {

    @Resource
    private PurchaseInMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新增采购明细
     * @param purchaseIn 采购明细
     */
    public void insert(PurchaseIn purchaseIn) {
        Assert.notNull(purchaseIn, "采购明细不允许为空！");
        mapper.insert(purchaseIn);
        logger.info("添加采购入库明细{}成功！", purchaseIn);
    }


    public PurchaseIn getById(Integer id) {
        Assert.notNull(id, "查询采购入库明细，采购单id不允许为空！");
         return mapper.getById(id);
    }

}
