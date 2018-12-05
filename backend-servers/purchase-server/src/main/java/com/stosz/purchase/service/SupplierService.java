package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.Supplier;
import com.stosz.purchase.mapper.SupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 供应商的service
 * @author xiongchenyang
 * @version [1.0 , 2017/11/27]
 */
@Service
public class SupplierService {

    @Resource
    private SupplierMapper mapper;
    @Resource
    private WmsSupplierService wmsSupplierService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新增供应商
     * @param supplier 供应商信息
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer insert(Supplier supplier){
        Assert.notNull(supplier,"新增的供应商不允许为空！");
        int i = mapper.add(supplier);
        Assert.isTrue(i==1,"新增供应商失败！");
        logger.info("新增供应商{}成功！",supplier);
        //将供应商的新增推送到wms
        wmsSupplierService.subSupplierCreateOrUpdate(supplier);
        return i;
    }

    /**
     * 删除供应商
     * @param id 供应商id
     */
    public void delete(Integer id){
        Assert.notNull(id,"要删除的供应商id不允许为空！");
        Supplier supplierDB = mapper.getById(id);
        int i = mapper.del(id);
        Assert.isTrue(i==1,"删除供应商失败！");
        logger.info("删除供应商{}成功！",supplierDB);
    }

    /**
     * 修改供应商
     * @param supplier 供应商
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Supplier supplier){
        Assert.notNull(supplier,"修改供应商不允许为空！");
        Supplier supplierDB = mapper.getById(supplier.getId());
        Assert.notNull(supplierDB,"要修改的供应商在数据库中不存在！");
        //将修改的供应商推送到wms
        int i = mapper.update(supplier);
        Assert.isTrue(i == 1,"修改供应商失败！");
        wmsSupplierService.subSupplierCreateOrUpdate(supplier);
        logger.info("将供应商{}修改为{}成功！",supplierDB,supplier);
    }

    /**
     * 根据id获取到供应商
     * @param id id
     * @return 供应商
     */
    public Supplier getById(Integer id){
        Assert.notNull(id,"要查询的供应商id不允许为空！");
        return mapper.getById(id);
    }

    public Supplier getByName(String name){
        Assert.hasLength(name,"要查询的供应商名称不允许为空！");
        return mapper.getByName(name);
    }

    /**
     * 获取到最后一次采购的供应商
     * @param idList id列表
     * @return 最后查询结果
     */
    public Supplier getLastSupplierByIds(Set<Integer> idList) {
        return mapper.getLastSupplierByIds(idList);
    }


    public List<Supplier> findBySearch(String search){
        return mapper.findBySearch(search);
    }

}
