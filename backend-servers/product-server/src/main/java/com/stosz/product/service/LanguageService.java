package com.stosz.product.service;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.product.ext.model.Category;
import com.stosz.product.ext.model.Language;
import com.stosz.product.mapper.LanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 语言设置
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class LanguageService {

	@Autowired
	private RabbitMQPublisher rabbitMQPublisher;

	@Resource
	private LanguageMapper languageMapper;
	@Resource
	private AttributeLangService attributeLangService;
	@Resource
	private AttributeValueLangService attributeValueLangService;
	@Resource
	private ProductLangService productLangService;
	@Resource
	private CategoryLangService categoryLangService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 语言添加
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addLanguage(Language language) {
		try {
			languageMapper.insert(language);
			logger.info("添加语言成功! 添加内容:{} ", language);	
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("语言编码["+language.getLangCode()+"]已经存在!");
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(language));
	}

	/**
	 * 语言删除
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteLanguage(Integer languageId) {
		Language langResult = load(languageId);
		//判断该语言是否绑定的产品,属性,属性值
		int countProduct = productLangService.countLangCode(langResult.getLangCode());
		Assert.isTrue(countProduct == 0, "已经有产品使用该语言的语言包,不能再删除!");
		int countAttr = attributeLangService.countLangCode(langResult.getLangCode());
		Assert.isTrue(countAttr == 0, "已经有属性使用该语言的语言包,不能再删除!");
		int countAttrVal = attributeValueLangService.countLangCode(langResult.getLangCode());
		Assert.isTrue(countAttrVal == 0, "已经有属性值使用该语言的语言包,不能再删除!");
		int countCate = categoryLangService.countLangCode(langResult.getLangCode());
		Assert.isTrue(countCate == 0, "已经有品类使用该语言的语言包,不能再删除!");
		languageMapper.delete(languageId);
		logger.info("删除语言成功!:", langResult);

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_delete).setData(langResult));
	}

	public Language getById(Integer id) {
		return languageMapper.getById(id);
	}

	/**
	 * 语言修改
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateLanguage(Language language) {
		try {
			Language langResult = load(language.getId());
			languageMapper.update(language);
			logger.info("将语言[{}]修改成[{}]成功!", langResult.getName(),language.getName());
			//语言编码改变后,相应的语言包也要修改语言编码
			String oldCode = langResult.getLangCode();
			String freshCode = language.getLangCode();
			if(!oldCode.equals(freshCode)){
				productLangService.updateByLangCode(freshCode, oldCode);
				logger.info("产品语言编码:{} 修改成:{} 成功",oldCode,freshCode);
				attributeLangService.updateByLangCode(freshCode, oldCode);
				logger.info("属性语言编码:{} 修改成:{} 成功",oldCode,freshCode);
				attributeValueLangService.updateByLangCode(freshCode, oldCode);
				logger.info("属性值语言编码:{} 修改成:{} 成功",oldCode,freshCode);
				categoryLangService.updateByLangCode(freshCode, oldCode);
				logger.info("品类语言编码:{} 修改成:{} 成功",oldCode,freshCode);
			}
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("语言编码["+language.getLangCode()+"]已经存在!");
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(language));

	}
	
	/**
	 * 根据langCode查询是否有该语言编码
	 */
	public int countByCode(String langCode){
		return languageMapper.countByCode(langCode);
	}
	
	/**
	 * 语言列表
	 * 
	 */
	public RestResult findLanguage(Language language) {
		RestResult rs = new RestResult();
		int count = languageMapper.count(language);
		rs.setTotal(count);
		if (count == 0) {
			return rs;
		}
		List<Language> lst = languageMapper.find(language);
		rs.setItem(lst);
		rs.setDesc("语言列表查询成功");
		return rs;
	}

	public Language load(Integer id) {
		Language lang = languageMapper.getById(id);
		Assert.notNull(lang, "语言id:" + id + "在数据库中不存在！");
		return lang;
	}

}
