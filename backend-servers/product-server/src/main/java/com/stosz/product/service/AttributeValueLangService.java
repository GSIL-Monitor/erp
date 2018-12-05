package com.stosz.product.service;

import com.stosz.product.ext.model.AttributeValueLang;
import com.stosz.product.mapper.AttributeValueLangMapper;
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
 * 属性值语言包
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class AttributeValueLangService {

	@Resource
    private AttributeValueLangMapper mapper;
	@Resource
    private LanguageService languageService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 属性值语言的添加
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addAttributeValueLang(AttributeValueLang param) {
        check(param);
        mapper.insert(param);
        logger.info("属性值语言包: {} 添加成功!", param);
    }

    /**
     * 属性值语言的删除
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAttributeValueLang(Integer attributeValueLangId) {
        AttributeValueLang attr = load(attributeValueLangId);
        mapper.delete(attributeValueLangId);
        logger.info("属性值语言包: {} 删除成功!", attr);
    }

    /**
     * 根据属性值id删除相对应的语言包
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByAttributeValueId(Integer attributeValueId) {
        mapper.deleteByAttributeValueId(attributeValueId);
        logger.info("根据属性值ID: {} 删除属性值语言包成功!",attributeValueId);
    }

    /**
     * 根据属性值id批量删除属性值语言包
     */
    public void deleteByAttrValIds(List<Integer> ids) {
        mapper.deleteByAttrValIds(ids);
        logger.info("根据属性值ID集合: {} 删除属性值语言包成功!", ids);
    }

    /**
     * 属性值语言的修改
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateAttributeValueLang(AttributeValueLang param) {
        AttributeValueLang attr = load(param.getId());
        int count = languageService.countByCode(param.getLangCode());
        Assert.isTrue(count == 1, "该语言编码有误,可以刷新试试!");
        check(param);
        mapper.update(param);
        logger.info("属性值语言包: {} 修改成: {} 成功!", attr, param);
    }

    /**
     * 根据语言编码修改语言编码
     */
    public void updateByLangCode(String langCode, String code) {
        mapper.updateByLangCode(langCode, code);
        logger.info("根据语言编码把: {} 修改成: {} 成功!",code,langCode);
    }

    /**
     * 通过属性值id获取语言包
     */
    public List<AttributeValueLang> findByAttributeValueId(Integer attributeValueId) {
        return mapper.findByAttributeValueId(attributeValueId);
    }
    public Map<Integer,List<AttributeValueLang>> findByAttributeValueId(List<Integer> attributeValueIds) {
    	Assert.notEmpty(attributeValueIds,"属性值ID集合为空,不能查询对应的语言包!");
        List<AttributeValueLang> list =  mapper.findByAttributeValueIds(attributeValueIds);
        return list.stream().collect(Collectors.groupingBy(AttributeValueLang::getAttributeValueId));
    }
    public List<AttributeValueLang> findByAttributeValueIds(List<Integer> attributeValueIds) {
    	Assert.notEmpty(attributeValueIds,"属性值ID集合为空,不能查询对应的语言包!");
        return mapper.findByAttributeValueIds(attributeValueIds);
    }


    private AttributeValueLang load(Integer id) {
        AttributeValueLang attr = mapper.getById(id);
        Assert.notNull(attr, "属性语言包id:" + id + "在数据库中不存在!");
        return attr;
    }


    /**
     * 通过name和langCode检查是否有重复的属性值语言包
     */
    private void check(AttributeValueLang param) {
        int number = mapper.countByNameCodeId(param);
        Assert.isTrue(number == 0, "属性值ID["+param.getAttributeValueId()+"]下已经存在该语言包");
    }
    
	/**
	 * 根据语言编码统计该属性值是否有该语言包
	 */
	public int countLangCode(String langCode){
		return mapper.countLangCode(langCode);
	}

}
