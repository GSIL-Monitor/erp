package com.stosz.product.service;

import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.model.ProductLang;
import com.stosz.product.mapper.ProductLangMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 产品语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class ProductLangService {

	@Resource
	private ProductLangMapper mapper;
	@Resource
	private ProductPushService pushService;
	@Resource
	private LanguageService languageService;
	
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 产品语言包的添加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ProductLang param) {
		check(param);
		mapper.insert(param);
		// 添加新的语言包时，重新推送产品到老erp
		pushService.pushProductThing(param.getProductId());
		logger.info("产品语言包: {} 添加成功", param);
    }


    public void insertList(List<ProductLang> productLangList) {
        Assert.notNull(productLangList, "产品语言包集合不允许为空！");
        mapper.insertList(0, productLangList);
        logger.info("批量插入产品语言包集合{}成功！", productLangList);
    }

	
	/**
	 * 产品语言包的删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id) {
		ProductLang result = load(id);
		mapper.delete(id);
		logger.info("产品语言包: {} 删除成功", result);
    }
	
	/**
	 * 产品语言的修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ProductLang param) {
		ProductLang result = load(param.getId());
		int count = languageService.countByCode(param.getLangCode());
		Assert.isTrue(count == 1,"该语言编码有误,可以刷新试试!");
		check(param);
		mapper.update(param);
		//修改语言包之后，将产品重新推送到老erp
		pushService.pushProductThing(param.getProductId());
		logger.info("产品语言包:{} 修改成:{} 成功", result,param);
    }
	
	/**
	 * 根据语言编码修改语言编码
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateByLangCode(String langCode,String code){
		mapper.updateByLangCode(langCode, code);
		logger.info("根据语言编码把: {} 修改成: {} 成功",code,langCode);
	}
	

	/**
	 * 根据产品id获取产品语言包
	 */
	public List<ProductLang> findProductLang(Integer productId) {
		return mapper.findProductLang(productId);
	}
	
	/**
	 * 根据产品id获取产品语言包
	 */
	public Map<Integer,List<ProductLang>> findProductLang(List<Integer> productIds) {
		Assert.notEmpty(productIds,"产品ID集合为空,不能查询对应的语言包!");
		List<ProductLang> productLangs = mapper.findProductLangs(productIds);
		return productLangs.stream().collect(Collectors.groupingBy(ProductLang::getProductId));
	}

    public ProductLang getLastByProductId(Integer productId) {
        return mapper.getLastByProductId(productId);
    }

    private ProductLang load(Integer id){
		ProductLang product = mapper.getById(id);
		Assert.notNull(product, "产品语言包id:" + id + "在数据库中不存在!");
		return product;
	}
	
	/**
     * 通过name和langCode检查是否有重复的属性语言包
     */
	private void check(ProductLang param) {
		int number = mapper.countByNameCodeId(param);
		Assert.isTrue(number == 0 ,"该产品下已经存在该语言包");
	}
	
	/**
	 * 根据语言编码统计该产品是否有该语言包
	 */
	public int countLangCode(String langCode){
		return mapper.countLangCode(langCode);
	}
	
}
