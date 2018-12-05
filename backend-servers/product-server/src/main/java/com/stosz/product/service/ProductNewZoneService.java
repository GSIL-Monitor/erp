package com.stosz.product.service;


import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.product.ext.model.DepartmentZoneRel;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.mapper.ProductNewZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 新品国家信息Service
 *
 * @author Administrator
 */
@Service
public class ProductNewZoneService {

    @Resource
    private ProductNewZoneMapper mapper;

    @Resource
    private ZoneService zoneService;
    @Resource
    private CountryService contouryService;
    @Resource
    private DepartmentZoneRelService departmentZoneRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public List<ProductNewZone> findByProductNewIds(Collection<Integer> productNewIds) {
        return mapper.findByProductNewIds(productNewIds);
    }

    /**
     * 获取指定productNewId的国家信息
     *
     * @return
     */
    public List<ProductNewZone> findByProductNewId(Integer productNewId) {
        ProductNewZone productNewZone = new ProductNewZone();
        productNewZone.setProductNewId(productNewId);
        List<ProductNewZone> zones = mapper.findByProductNewId(productNewId);
        return zones;
    }

    /**
     * 新增新品
     *
     * @return
     */
    @Transactional(value = "pcTxManager", rollbackFor = Exception.class)
    public void insert(Integer productNewId, Integer zoneId) {
    	//部门区域关系限定
    	DepartmentZoneRel param = new DepartmentZoneRel();
    	param.setDepartmentId(ThreadLocalUtils.getUser().getDeptId());
    	param.setZoneId(zoneId);
    	param.setUsable(true);
    	int count = departmentZoneRelService.count(param);
    	Zone zon = zoneService.getById(zoneId);
    	Assert.isTrue(count > 0, "本部门[ID:"+ ThreadLocalUtils.getUser().getDeptId()+"]下没有绑定区域["+zon.getTitle()+"]");
    	/**
         * 如果传过来的是一个区域组， 增加区域组下面的区域；否则，增加自己即可；
         */

        List<Zone> insertZoneList = zoneService.findByParentId(zoneId);

        if(CollectionUtils.isNullOrEmpty(insertZoneList))
        {
            Zone zoneById = zoneService.getById(zoneId);

            insertZoneList = Arrays.asList(zoneById);
        }

        insertZoneList.stream().parallel().forEach(zone -> {

            ProductNewZone productNewZone = mapper.getByUnique(productNewId, zone.getId());
            if(null == productNewZone)
            {
                productNewZone = new  ProductNewZone();
                productNewZone.setProductNewId(productNewId);
                productNewZone.setZoneId(zone.getId());
                mapper.insert(productNewZone);
            }

        });


    }

    public void insertBatch(List<ProductNewZone> productNewZoneList) {
        Assert.notNull(productNewZoneList, "新增新品区域集合不允许为空！");
        Assert.isTrue(!productNewZoneList.isEmpty(), "新增新品区域集合不允许为空！");
        int i = mapper.insertBatch(0, productNewZoneList);
        logger.info("批量新增排重新品{}成功！", productNewZoneList);
    }

    /**
     * 新品区域设置删除方法
     *
     * @return
     */
    @Transactional(value = "pcTxManager", rollbackFor = Exception.class)
    public void delete(Integer id) {
        int effectRow = mapper.delete(id);
        Assert.isTrue(effectRow == 1, "删除新品区域信息失败!");
    }
    
    public int countByZoneId(Integer zoneId){
		return mapper.countByZoneId(zoneId);
	}
    
}
