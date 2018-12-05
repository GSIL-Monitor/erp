package com.stosz.purchase.service;

import com.stosz.plat.common.MBox;
import com.stosz.plat.utils.StringUtil;
import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.ext.service.IPartnerService;
import com.stosz.purchase.ext.model.SkuPurchaseInfo;
import com.stosz.purchase.ext.model.Supplier;
import com.stosz.purchase.mapper.SkuPurchaseInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author feiheping
 * @version [1.0,2017年11月8日]
 */
@Service
public class SkuSupplierService {

    @Resource
    private SkuPurchaseInfoMapper skuPurchaseInfoMapper;

    @Resource
    private SupplierService supplierService;

    @Resource
    private IPartnerService iPartnerService;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 根据SKU获取供应商
     */
    public List<SkuPurchaseInfo> querySupplierBySku(String sku) {
        List<SkuPurchaseInfo> skuPurchaseInfoList = skuPurchaseInfoMapper.getListBySku(sku);
        skuPurchaseInfoList = Optional.of(skuPurchaseInfoList).orElse(new ArrayList<>());
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

    /**
     * 新增供应商
     * 新增供应商与SKU关系
     * @param skuPurchaseInfo
     * @return
     */
    @Transactional(transactionManager = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addSupplier(SkuPurchaseInfo skuPurchaseInfo) {
        if(skuPurchaseInfo.getSupplierId()!=null){
            skuPurchaseInfo.setSupplierId(skuPurchaseInfo.getSupplierId());
            skuPurchaseInfo.setLastPurchaseAt(new Date());
            skuPurchaseInfo.setLastPurchasePrice(new BigDecimal(0));
            skuPurchaseInfo.setCreator(MBox.getLoginUserName());
            skuPurchaseInfo.setCreatorId(MBox.getLoginUserId());
            try {
                skuPurchaseInfoMapper.add(skuPurchaseInfo);
            } catch (DuplicateKeyException e) {
                logger.error("{}该数据已经存在！",skuPurchaseInfo,e);
                throw new RuntimeException("该供应商已经存在！");
            }
            return;
        }
        Supplier supplier = supplierService.getByName(skuPurchaseInfo.getSupplierName());
        Assert.isNull(supplier, String.format("%s已经存在", skuPurchaseInfo.getSupplierName()));
        Supplier addSupplier = new Supplier();
        addSupplier.setName(skuPurchaseInfo.getSupplierName());
        addSupplier.setCreator(MBox.getLoginUserName());
        addSupplier.setCreatorId(MBox.getLoginUserId());
        int count = supplierService.insert(addSupplier);
        if (count > 0) {
            skuPurchaseInfo.setSupplierId(addSupplier.getId());
            skuPurchaseInfo.setLastPurchaseAt(new Date());
            skuPurchaseInfo.setLastPurchasePrice(new BigDecimal(0));
            skuPurchaseInfo.setCreator(MBox.getLoginUserName());
            skuPurchaseInfo.setCreatorId(MBox.getLoginUserId());
            try {
                skuPurchaseInfoMapper.add(skuPurchaseInfo);
            } catch (DuplicateKeyException e) {
                logger.error("{}该数据已经存在！",skuPurchaseInfo,e);
                throw new RuntimeException("该供应商已经存在！");
            }

            //新增供应商保存到合作伙伴
            Partner partner = new Partner();
            partner.setNo(String.valueOf(addSupplier.getId()));
            partner.setTypeEnum(TypeEnum.supplier);
            partner.setName(addSupplier.getName());
            partner.setCreator(MBox.getLoginUserName());
            partner.setCreatorId(MBox.getLoginUserId());
            partner.setUsable(true);
            partner.setSettlementMethod(1);
            int partnerId = iPartnerService.insert(partner);
            if(partnerId > 0){
                Supplier updataSupplier = supplierService.getById(addSupplier.getId());
                updataSupplier.setPartnerId(partnerId);
                supplierService.update(updataSupplier);
            }else {
                throw  new RuntimeException("保存合作伙伴失败");
            }
        }
    }

    /**
     * 删除供应商与SKU关系
     * @param id
     */
    public void delSkuPurchaseInfo(Integer id) {
        skuPurchaseInfoMapper.del(id);
    }

    /**
     * 获取到最后一次采购的供应商
     * @param idList
     * @return
     */
    public Supplier getLastSupplierByIds(Set<Integer> idList) {
        return supplierService.getLastSupplierByIds(idList);
    }

    /**
     * 根据SKU查询最有一次采购的供应商
     * @param sku
     * @return
     */
    public Supplier queryLastSupplierBySku(String sku) {
        SkuPurchaseInfo skuPurchaseInfo = skuPurchaseInfoMapper.getLastBySku(sku);
        if (skuPurchaseInfo != null) {
            return supplierService.getById(skuPurchaseInfo.getSupplierId());
        }
        return null;
    }

    public List<SkuPurchaseInfo> querySkuSupplier(Set<String> set) {
        return skuPurchaseInfoMapper.querySkuSupplier(set);
    }


    public void delSkuPurchaseInfoByPurchaseId(Integer id ,Set<String> set){
        skuPurchaseInfoMapper.delSkuPurchaseInfoByPurchaseId(id, set);
    }


}
