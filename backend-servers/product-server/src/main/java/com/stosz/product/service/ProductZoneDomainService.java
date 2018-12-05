package com.stosz.product.service;

import com.google.common.base.Strings;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.ProductZoneDomain;
import com.stosz.product.mapper.ProductZoneDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ProductZoneDomainService {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductZoneDomainMapper productZoneDomainMapper;


    public void insert(ProductZoneDomain param){
        logger.info("备案操作！{}" , param);
        productZoneDomainMapper.insert(param);
    }

    public List<ProductZoneDomain> find(ProductZoneDomain param){
        return productZoneDomainMapper.find(param);
    }

    public void insertList(List<ProductZoneDomain> productZoneDomainList) {
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(productZoneDomainList), "不允许插入空的产品区域域名集合");
        productZoneDomainMapper.insertList(0, productZoneDomainList);
    }

    /**
     * 按照建站端的产品id 来更新
     * @param siteProductId
     * @param domain
     * @param webDirectory
     */
    public void update(String siteProductId, String domain, String webDirectory) {

        Assert.isTrue(!Strings.isNullOrEmpty(siteProductId),"建站端的产品id不能为空");
        productZoneDomainMapper.updateBySiteProductId(siteProductId,domain,webDirectory);

    }
}
