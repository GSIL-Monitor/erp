package com.stosz.purchase.service;

import com.stosz.plat.common.MBox;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseTrackingNo;
import com.stosz.purchase.ext.model.PurchaseTrackingNoRel;
import com.stosz.purchase.mapper.PurchaseTrackingNoMapper;
import com.stosz.purchase.mapper.PurchaseTrackingNoRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购运单号关联关系的service
 * @author xiongchenyang
 * @version [1.0 , 2018/1/2]
 */
@Service
public class PurchaseTrackingNoRelService {

    @Resource
    private PurchaseTrackingNoRelMapper mapper;
    @Resource
    private PurchaseTrackingNoService purchaseTrackingNoService;
    @Resource
    private PurchaseService purchaseService;
    @Resource
    private WmsPurchaseService wmsPurchaseService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 添加采购单与运单号的关联关系
     * @param purchaseTrackingNoRel 关联关系
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(PurchaseTrackingNoRel purchaseTrackingNoRel){
        Assert.notNull(purchaseTrackingNoRel,"不允许插入空的采购运单号关联关系！");
        Assert.hasLength(purchaseTrackingNoRel.getPurchaseNo(),"关联关系的采购单号不允许为空！");
        Assert.hasLength(purchaseTrackingNoRel.getTrackingNo(),"关联关系的运单号不允许为空!");
        int i = mapper.insert(purchaseTrackingNoRel);
        Assert.isTrue(i==1,"插入采购运单号关联关系失败！！！");
        pushWms(purchaseTrackingNoRel);
        logger.info("插入采购运单号关联关系["+purchaseTrackingNoRel+"]成功！");
    }

    /**
     * 通过运单号添加关联关系
     * @param purchaseTrackingNo 关联关系
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertByTrackingNo(PurchaseTrackingNo purchaseTrackingNo){
        PurchaseTrackingNoRel purchaseTrackingNoRelDB = getByUnique(purchaseTrackingNo);
        Assert.isNull(purchaseTrackingNoRelDB,"该采购单已经有了该运单号！");
        String trackingNo = purchaseTrackingNo.getTrackingNo();
        purchaseTrackingNo.setCreator(MBox.getLoginUserName());
        purchaseTrackingNo.setCreatorId(MBox.getLoginUserId());
        PurchaseTrackingNo purchaseTrackingNoDB = purchaseTrackingNoService.getByTrackingNo(trackingNo);
        if(purchaseTrackingNoDB != null){
            purchaseTrackingNo.setId(purchaseTrackingNoDB.getId());
            purchaseTrackingNoService.update(purchaseTrackingNo);
        }else{
            purchaseTrackingNoService.insert(purchaseTrackingNo);
        }

        PurchaseTrackingNoRel purchaseTrackingNoRel = new PurchaseTrackingNoRel();
        purchaseTrackingNoRel.setTrackingNo(trackingNo);
        purchaseTrackingNoRel.setPurchaseNo(purchaseTrackingNo.getPurchaseNo());
        purchaseTrackingNoRel.setCreatorId(MBox.getLoginUserId());
        purchaseTrackingNoRel.setCreator(MBox.getLoginUserName());
        insert(purchaseTrackingNoRel);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id){
        Assert.notNull(id,"要删除的采购运单号关联关系ID不允许为空！！！");
        PurchaseTrackingNoRel purchaseTrackingNoRelDB = mapper.getById(id);
        Assert.notNull(purchaseTrackingNoRelDB,"要删除的采购运单号关联关系【"+id+"】在数据库中不存在！");
        int  i = mapper.delete(id);
        Assert.isTrue(i==1,"删除采购运单号关联关系失败！！！");
        pushWms(purchaseTrackingNoRelDB);
        logger.info("采购运单号{}关联关系删除成功!",purchaseTrackingNoRelDB);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByUnique(PurchaseTrackingNo purchaseTrackingNo){
        Assert.notNull(purchaseTrackingNo,"要删除的关联关系不允许为空！");
        Assert.hasLength(purchaseTrackingNo.getPurchaseNo(),"要删除的关联关系的采购单号不允许为空！");
        Assert.hasLength(purchaseTrackingNo.getTrackingNo(),"要删除的关联关系的运单号不允许为空！");
        int i = mapper.deleteByUnique(purchaseTrackingNo);
        Assert.isTrue(i==1,"删除采购运单号关联关系失败！！！");
        logger.info("采购运单号{}关联关系删除成功!",purchaseTrackingNo);
        List<PurchaseTrackingNoRel> purchaseTrackingNoRelList = findByTrackingNo(purchaseTrackingNo.getTrackingNo());
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(purchaseTrackingNoRelList)&&purchaseTrackingNoRelList.size()>1,"不允许将采购单的运单号全部删除，如要删除该运单号，请先新增正确的运单号！");
        PurchaseTrackingNoRel purchaseTrackingNoRel = new PurchaseTrackingNoRel();
        purchaseTrackingNoRel.setPurchaseNo(purchaseTrackingNo.getPurchaseNo());
        purchaseTrackingNoRel.setTrackingNo(purchaseTrackingNo.getTrackingNo());
        pushWms(purchaseTrackingNoRel);
    }

    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(PurchaseTrackingNoRel purchaseTrackingNoRel){
        Assert.notNull(purchaseTrackingNoRel,"要修改的采购运单号关联关系不允许为空！");
        PurchaseTrackingNoRel purchaseTrackingNoRelDB = mapper.getById(purchaseTrackingNoRel.getId());
        Assert.notNull(purchaseTrackingNoRelDB,"要修改的采购运单号【"+purchaseTrackingNoRel.getId()+"】在数据库中不存在！");
        int i = mapper.update(purchaseTrackingNoRel);
        Assert.isTrue(i==1,"修改采购运单号失败！！！");
        pushWms(purchaseTrackingNoRel);
        logger.info("将采购运单号{}修改为{}成功！！！",purchaseTrackingNoRelDB, purchaseTrackingNoRel);
    }

    public PurchaseTrackingNoRel getById(Integer id){
        Assert.notNull(id,"要查询待采购运单号id不允许为空！");
        return mapper.getById(id);
    }

    public List<PurchaseTrackingNoRel>  findByPurchaseNo(String purchaseNo){
        Assert.hasLength(purchaseNo,"要查询的采购运单号不允许为空！");
        return mapper.findByPurchaseNo(purchaseNo);
    }

    public List<PurchaseTrackingNoRel> findByTrackingNo(String trackingNo){
        Assert.hasLength(trackingNo,"要查询的运单号不允许为空！");
        return mapper.findByTrackingNo(trackingNo);
    }

    public Map<String,String> getTrackingNoByPurchaseNo(List<Purchase> purchaseNos){
        List<PurchaseTrackingNoRel> purchaseTrackingNoRelList =  mapper.getTrackingNoByPurchaseNo(purchaseNos);
        Map<String,String> map = new HashMap<>();
        for(PurchaseTrackingNoRel purchaseTrackingNoRel : purchaseTrackingNoRelList){
            map.put(purchaseTrackingNoRel.getPurchaseNo(),purchaseTrackingNoRel.getTrackingNo());
        }
        return map;
    }

    public String getTrackingNo(String purchaseNo){
        return mapper.getTrackingNo(purchaseNo);
    }

    public PurchaseTrackingNoRel getByUnique(PurchaseTrackingNo purchaseTrackingNo){
        return mapper.getByUnique(purchaseTrackingNo);
    }

    private void pushWms(PurchaseTrackingNoRel purchaseTrackingNoRel){
        Assert.hasLength(purchaseTrackingNoRel.getPurchaseNo(),"关联关系的采购单号不允许为空！");
        String trackingNo = getTrackingNo(purchaseTrackingNoRel.getPurchaseNo());
        if(StringUtils.isBlank(trackingNo)){
            throw new RuntimeException("不允许将采购单的运单号全部删除！");
        }
        Purchase purchase = purchaseService.getByPurchaseNo(purchaseTrackingNoRel.getPurchaseNo());
        purchase.setTrackingNo(trackingNo);
        Assert.notNull(purchase,"找不到要修改的采购单！");
        Boolean isSuccess = wmsPurchaseService.subUpdatePurchaseExpress(purchase);
        if(!isSuccess){
            throw new RuntimeException("推送wms失败！");
        }

    }
}  
