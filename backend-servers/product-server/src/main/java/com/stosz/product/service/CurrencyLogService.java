package com.stosz.product.service;

import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.model.CurrencyLog;
import com.stosz.product.mapper.CurrencyLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 币种汇率历史
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@Service
public class CurrencyLogService {
	
	@Resource
	private CurrencyLogMapper currencyLogMapper;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 插入货币日志
	 * 
	 */
	public void insertCurrencyLog(CurrencyLog currencyLog){
		currencyLogMapper.insert(currencyLog);
		logger.info("币种:{} 历史日志保存成功", currencyLog.getName());
	}
	
	/**
	 * 根据汇率code查询相应的汇率历史
	 * 
	 */
	public List<CurrencyLog> historyCurrency(Currency currency) {
		List<CurrencyLog> logList = 
				currencyLogMapper.historyCurrency(currency);
		return logList;
	}
	
	/**
	 * 币种汇率历史列表
	 */
	public RestResult findList(CurrencyLog param){
		RestResult result = new RestResult();
		int count = currencyLogMapper.count(param);
		result.setTotal(count);
		if(count == 0){
			return result;
		}
		List<CurrencyLog> list = currencyLogMapper.find(param);
		result.setItem(list);
		result.setDesc("币种汇率列表查询成功!");
		return result;
	}
	
	
}
