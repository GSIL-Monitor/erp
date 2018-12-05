package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpProductSkuMapper;
import com.stosz.olderp.ext.model.OldErpProductSku;
import com.stosz.olderp.ext.service.IOldErpProductSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class OldErpProductSkuService implements IOldErpProductSkuService {
	
	@Autowired
	private OldErpProductSkuMapper oldErpProductSkuMapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 产品sku表查询
	 * @return
	 */
	@Override
	public List<OldErpProductSku> findList(Integer limit, Integer offset) {
		List<OldErpProductSku> list = oldErpProductSkuMapper.findList(limit,offset);
		return list;
	}
	
	
	
	/**
	 * 根据属性值id查询属性名和属性值名称
	 * @return
	 */
	@Override
//    @Cacheable(value = "getOldSkuByAttValueId", unless = "#result == null")
    public List<OldErpProductSku> getByAttValueId(List<String> optionValues) {
        return oldErpProductSkuMapper.getByAttributeValueId(optionValues);
//		return null;
    }

	@Override
	public OldErpProductSku getBySku(String sku) {
		return oldErpProductSkuMapper.getBySku(sku);
	}

	@Override
	public void insert(OldErpProductSku oldErpProductSku) {
		Assert.notNull(oldErpProductSku, "新增老erp的产品SKU记录不能为空！");
		int i = oldErpProductSkuMapper.insert(oldErpProductSku);
		Assert.isTrue(i == 1, "插入老erp的产品SKU失败！");
		logger.info("新增老erp产品SKU{}成功！", oldErpProductSku);
	}

	@Override
	public void update(OldErpProductSku oldErpProductSku) {
		Assert.notNull(oldErpProductSku, "要修改的老erp的产品SKU不能为空！");
		OldErpProductSku oldErpProductSkuDB = oldErpProductSkuMapper.getById(oldErpProductSku.getId());
		Assert.notNull(oldErpProductSkuDB, "要修改的老erp的产品SKU在数据库中不存在！");
		int i = oldErpProductSkuMapper.update(oldErpProductSku);
		Assert.isTrue(i == 1, "修改老erp的产品SKU失败！");
		logger.info("将老erp的产品SKU记录{} 修改为 {} 成功！", oldErpProductSkuDB, oldErpProductSku);
	}

    @Override
    public List<OldErpProductSku> findByProductId(Integer productId) {
        return oldErpProductSkuMapper.findByProductId(productId);
    }

	@Override
	public Integer deleteByProductId(Integer productId) {
		return oldErpProductSkuMapper.deleteByProductId(productId);
	}


	@Override
	public int countList() {
		return oldErpProductSkuMapper.countList();
	}

	@Override
	public String getOptionValueByProduct(Integer productId){
		return oldErpProductSkuMapper.getOptionValueByProduct(productId);
	}
}





