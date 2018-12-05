package com.stosz.product.service;

import com.stosz.product.ext.model.CategoryLang;
import com.stosz.product.mapper.CategoryLangMapper;
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
 * 品类语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class CategoryLangService {
	@Resource
	private CategoryLangMapper mapper;
	@Resource 
	private LanguageService languageService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 品类语言的添加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(CategoryLang param) {
		check(param);
		mapper.insert(param);
		logger.info("品类语言包: {} 添加成功!", param);
	}

	/**
	 * 品类语言的删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer categoryLangId) {
		CategoryLang attr = load(categoryLangId);
		mapper.delete(categoryLangId);
		logger.info("品类语言包ID: {} 删除成功!", attr.getId());
	}

	/**
	 * 根据品类id删除相应的语言包
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByAttributeId(Integer categoryId) {
		mapper.deleteByCategoryId(categoryId);
		logger.info("根据品类ID: {} 删除品类语言包成功!", categoryId);
	}

	/**
	 * 品类语言的修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(CategoryLang CategoryLang) {
		CategoryLang attr = load(CategoryLang.getId());
		int count = languageService.countByCode(CategoryLang.getLangCode());
		Assert.isTrue(count == 1,"该语言编码有误,可以刷新试试!");
		check(CategoryLang);
		mapper.update(CategoryLang);
		logger.info("品类语言包: {} 修改成: {} 成功!", attr,CategoryLang);
	}
	
	/**
	 * 根据语言编码修改语言编码
	 */
	public void updateByLangCode(String langCode,String code){
		mapper.updateByLangCode(langCode, code);
		logger.info("根据品类语言包编码把: {} 修改成: {} 成功!",code,langCode);
	}
	
	/**
	 * 通过品类id获取语言包
	 */
	public List<CategoryLang> findByCategoryId(Integer attributeId) {
		return mapper.findByCategoryId(attributeId);
	}


	/**
	 * 通过品类id获取语言包
	 */
	public Map<Integer,List<CategoryLang>> findByCategoryId(List<Integer> attributeIds) {
		Assert.notEmpty(attributeIds,"品类ID集合为空,不能查询对应的语言包!");
		List<CategoryLang> list = mapper.findByCategoryIds(attributeIds);
		return list.stream().collect(Collectors.groupingBy(CategoryLang::getCategoryId));
	}
	
	public List<CategoryLang> findByCategoryIds(List<Integer> attributeIds) {
		Assert.notEmpty(attributeIds,"品类ID集合为空,不能查询对应的语言包!");
		return mapper.findByCategoryIds(attributeIds);
	}

	private CategoryLang load(Integer id){
		CategoryLang attr = mapper.getById(id);
		Assert.notNull(attr, "品类语言包id:" + id + "在数据库中不存在!");
		return attr;
	}
	
	
    /**
     * 通过name和langCode检查是否有重复的品类语言包
     */
	private void check(CategoryLang param) {
		int number = mapper.countByNameCodeId(param);
		Assert.isTrue(number == 0 ,"品类ID["+param.getCategoryId()+"]下已经存在该语言包");
	}
	
	/**
	 * 根据语言编码统计该品类是否有该语言包
	 */
	public int countLangCode(String langCode){
		return mapper.countLangCode(langCode);
	}

}
