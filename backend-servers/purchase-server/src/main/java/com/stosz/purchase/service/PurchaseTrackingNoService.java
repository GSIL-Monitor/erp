package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.PurchaseTrackingNo;
import com.stosz.purchase.mapper.PurchaseTrackingNoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 采购运单号的service
 * @author xiongchenyang
 * @version [1.0 , 2018/1/2]
 */
@Service
public class PurchaseTrackingNoService {

    @Resource
    private PurchaseTrackingNoMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void insert(PurchaseTrackingNo purchaseTrackingNo){
        Assert.notNull(purchaseTrackingNo,"不允许插入空的采购运单号！");
        int i = mapper.insert(purchaseTrackingNo);
        Assert.isTrue(i==1,"插入采购运单号失败！！！");
        logger.info("插入采购运单号["+purchaseTrackingNo+"]成功！");
    }

    public void delete(Integer id){
        Assert.notNull(id,"要删除的采购运单号ID不允许为空！！！");
        PurchaseTrackingNo purchaseTrackingNoDB = mapper.getById(id);
        Assert.notNull(purchaseTrackingNoDB,"要删除的采购运单号【"+id+"】在数据库中不存在！");
        int  i = mapper.delete(id);
        Assert.isTrue(i==1,"删除采购运单号失败！！！");
        logger.info("采购运单号{}删除成功!",purchaseTrackingNoDB);
    }

    public void update(PurchaseTrackingNo purchaseTrackingNo){
        Assert.notNull(purchaseTrackingNo,"要修改的采购运单号不允许为空！");
        PurchaseTrackingNo purchaseTrackingNoDB = mapper.getById(purchaseTrackingNo.getId());
        Assert.notNull(purchaseTrackingNoDB,"要修改的采购运单号【"+purchaseTrackingNo.getId()+"】在数据库中不存在！");
        int i = mapper.update(purchaseTrackingNo);
        Assert.isTrue(i==1,"修改采购运单号失败！！！");
        logger.info("将采购运单号{}修改为{}成功！！！",purchaseTrackingNoDB, purchaseTrackingNo);
    }

    public void updateSelect(PurchaseTrackingNo purchaseTrackingNo){
        Assert.notNull(purchaseTrackingNo,"要修改的采购运单号不允许为空！");
        int i = mapper.updateSelect(purchaseTrackingNo);
        Assert.isTrue(i==1,"修改采购运单号失败！！！");
        logger.info("将采购运单号修改为{}成功！！！", purchaseTrackingNo);
    }

    public PurchaseTrackingNo getById(Integer id){
        Assert.notNull(id,"要查询待采购运单号id不允许为空！");
        return mapper.getById(id);
    }

    public List<PurchaseTrackingNo>  findByPurchaseNo(String purchaseNo){
        Assert.hasLength(purchaseNo,"要查询的采购运单号不允许为空！");
        return mapper.findByPurchaseNo(purchaseNo);
    }

    public PurchaseTrackingNo getByTrackingNo(String trackingNo){
        Assert.hasLength(trackingNo,"要查询的运单号不允许为空！");
        return mapper.getByTrackingNo(trackingNo);
    }


}  
