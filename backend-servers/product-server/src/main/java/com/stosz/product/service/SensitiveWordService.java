package com.stosz.product.service;

import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.SensitiveWord;
import com.stosz.product.mapper.SensitiveWordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 敏感词service
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@Service
public class SensitiveWordService {

    private  final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SensitiveWordMapper mapper;
    
    
	/**
	 * 敏感词添加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insert(SensitiveWord param){
		try {
			mapper.insert(param);
			logger.info("敏感词:{} 添加成功", param);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("敏感词["+param.getName()+"]已经存在!");
		}
	}
	
	/**
	 * 敏感词删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id){
		SensitiveWord sensitiveWord = load(id);
		mapper.delete(id);
		logger.info("敏感词:{} 删除成功",sensitiveWord);
	}
	
	
	
	/**
	 * 敏感词修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(SensitiveWord param){
		try{
			SensitiveWord rest = load(param.getId());
			mapper.update(param);
			logger.info("敏感词:{} 成功被修改成:{} ",rest,param);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("敏感词["+param.getName()+"]已经存在!");
		}
	}
	
	
	/**
	 * 敏感词单条查询
	 */
	public SensitiveWord getById(Integer id){
		return mapper.getById(id);
	}
	
	/**
	 * 敏感词列表
	 */
	public RestResult find(SensitiveWord param){
		param.setOrderBy("name");
		param.setOrder(false);
		RestResult result = new RestResult();
		int count = mapper.count(param);
		result.setTotal(count);
		if(count == 0){
			return result;
		}
		List<SensitiveWord> list = mapper.find(param);
		result.setItem(list);
		result.setDesc("敏感词列表查询成功");
		return result;
	}
	
	
	public SensitiveWord load(Integer id) {
		SensitiveWord sensitiveWord = getById(id);
		Assert.notNull(sensitiveWord, "敏感词id:" + id + "在数据库中不存在!");
		return sensitiveWord;
	}
	
    
    public List<String> findAllWord(){
           return mapper.findAllWord();
    }

    public String findSensitiveWordInString(String title ){
        List<String > allSensitiveWords = findAllWord();
        return this.findSensitiveWordInString(title, allSensitiveWords);
    }
    
    public String findSensitiveWordInString(String title , List<String > allSensitiveWords){
//        String w=null;
        if (title == null || title.trim().length()==0) {
            return null;
        }
        title = title.toUpperCase();
        for (String word : allSensitiveWords) {
            word = word.toUpperCase();
            if(title.indexOf(word)!=-1) {
                return word;
            }
        }
        return null;

    }
}
