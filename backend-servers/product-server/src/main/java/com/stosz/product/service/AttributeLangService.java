package com.stosz.product.service;

import com.stosz.product.ext.model.AttributeLang;
import com.stosz.product.mapper.AttributeLangMapper;
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
 * 属性语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class AttributeLangService {
	@Resource
	private AttributeLangMapper mapper;
	@Resource 
	private LanguageService languageService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 属性语言的添加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAttributeLang(AttributeLang param) {
		check(param);
		mapper.insert(param);
		logger.info("属性语言包: {} 添加成功!", param);
	}

	/**
	 * 属性语言的删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteAttributeLang(Integer attributeLangId) {
		AttributeLang attr = load(attributeLangId);
		mapper.delete(attributeLangId);
		logger.info("属性语言包ID: {} 删除成功!", attr.getId());
	}

	/**
	 * 根据属性id删除相应的语言包
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByAttributeId(Integer attributeId) {
		mapper.deleteByAttributeId(attributeId);
		logger.info("根据属性ID: {} 删除属性语言包成功!", attributeId);
	}

	/**
	 * 属性语言的修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAttributeLang(AttributeLang attributeLang) {
		AttributeLang attr = load(attributeLang.getId());
		int count = languageService.countByCode(attributeLang.getLangCode());
		Assert.isTrue(count == 1,"该语言编码有误,可以刷新试试!");
		check(attributeLang);
		mapper.update(attributeLang);
		logger.info("属性语言包: {} 修改成: {} 成功!", attr,attributeLang);
	}
	
	/**
	 * 根据语言编码修改语言编码
	 */
	public void updateByLangCode(String langCode,String code){
		mapper.updateByLangCode(langCode, code);
		logger.info("根据属性语言包编码把: {} 修改成: {} 成功!",code,langCode);
	}
	
	/**
	 * 通过属性id获取语言包
	 */
	public List<AttributeLang> findByAttributeId(Integer attributeId) {
		return mapper.findByAttributeId(attributeId);
	}


	/**
	 * 通过属性id获取语言包
	 */
	public Map<Integer,List<AttributeLang>> findByAttributeId(List<Integer> attributeIds) {
		Assert.notEmpty(attributeIds,"属性ID集合为空,不能查询对应的语言包!");
		List<AttributeLang> list = mapper.findByAttributeIds(attributeIds);
		return list.stream().collect(Collectors.groupingBy(AttributeLang::getAttributeId));
	}
	
	public List<AttributeLang> findByAttributeIds(List<Integer> attributeIds) {
		Assert.notEmpty(attributeIds,"属性ID集合为空,不能查询对应的语言包!");
		return mapper.findByAttributeIds(attributeIds);
	}

	private AttributeLang load(Integer id){
		AttributeLang attr = mapper.getById(id);
		Assert.notNull(attr, "属性语言包id:" + id + "在数据库中不存在!");
		return attr;
	}
	
	
    /**
     * 通过name和langCode检查是否有重复的属性语言包
     */
	private void check(AttributeLang param) {
		int number = mapper.countByNameCodeId(param);
		Assert.isTrue(number == 0 ,"属性ID["+param.getAttributeId()+"]下已经存在该语言包");
	}
	
	/**
	 * 根据语言编码统计该属性是否有该语言包
	 */
	public int countLangCode(String langCode){
		return mapper.countLangCode(langCode);
	}

}
