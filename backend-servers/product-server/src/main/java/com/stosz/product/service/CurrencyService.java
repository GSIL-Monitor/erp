package com.stosz.product.service;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.model.CurrencyLog;
import com.stosz.product.ext.service.ICurrencyService;
import com.stosz.product.mapper.CurrencyMapper;
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
 * 币种设置
 * 
 * @author he_guitang
 * @version [版本号, 2017年8月23日]
 */
@Service
public class CurrencyService implements ICurrencyService{

	@Autowired
	private RabbitMQPublisher rabbitMQPublisher;

	@Resource
	private CurrencyMapper currencyMapper;
	@Resource
	private CurrencyLogService currencyLogService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 币种添加
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addCurrency(Currency currency, CurrencyLog currencyLog) {
		currency.setUsable(false);
		try{
			currencyMapper.insert(currency);
			logger.info("币种:{} 添加成功", currency);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("币种3字码["+currency.getCurrencyCode()+"]已经存在!");
		}
		if (currency.getUsable()) {
			currencyLogService.insertCurrencyLog(currencyLog);
			logger.info("币种:{} 日志添加成功", currency);
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(currency));

	}

	/**
	 * 币种删除
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id) {
		Currency currencyResult = currencyMapper.getById(id);
		Assert.notNull(currencyResult, "没有查询到符合删除的记录");
		Assert.isTrue(!currencyResult.getUsable(),"该币种["+id+"]已经启用,不能再删");
		currencyMapper.delete(id);
		logger.info("币种id:{} 删除成功");

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_delete).setData(currencyResult));
	}

	/**
	 * 币种修改
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCurrency(Currency currency, CurrencyLog currencyLog) {
		Currency currencyResult = currencyMapper.getById(currency.getId());
		Assert.notNull(currencyResult, "没有查询到需要修改的记录");
		if (!currency.getUsable()) {
			Assert.isTrue(!currencyResult.getUsable(), "该币种已经开启,不能再关闭");
		}
		try {
			currencyMapper.update(currency);
			logger.info("币种: {} 修改成功!", currencyResult.getName());
			rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(currency));

		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("币种3字码["+currency.getCurrencyCode()+"]已经存在!");
		}
		if (currency.getUsable()) {
			//开启状态记日志
			if(!currencyResult.getUsable()){
				currencyLogService.insertCurrencyLog(currencyLog);
				logger.info("币种: {} 日志添加成功!", currency);
			}
			// 修改后 保存到汇率历史(到人民币的汇率改变---记日志) 
			if (currencyResult.getRateCny().compareTo(currency.getRateCny()) != 0 ) {
				currencyLogService.insertCurrencyLog(currencyLog);
				logger.info("币种:{} 日志添加成功!", currency);
			}
		}

	}

	/**
	 * 币种列表查询
	 * 
	 */
	public RestResult findCurrency(Currency currency) {
		RestResult result = new RestResult();
		int count = currencyMapper.count(currency);
		result.setTotal(count);
		if (count == 0) {
			return result;
		}
		List<Currency> list = currencyMapper.findList(currency);
		result.setItem(list);
		result.setDesc("币种列表查询成功");
		return result;
	}

	public RestResult findAll(Currency param) {
		RestResult result = new RestResult();
		List<Currency> list = currencyMapper.findAllList(param);
		result.setItem(list);
		result.setDesc("币种列表查询成功");
		return result;
	}

	/**
	 * 根据币种id查询汇率历史
	 * 
	 */
	public List<CurrencyLog> historyCurrency(Currency currency) {
		List<CurrencyLog> list = currencyLogService.historyCurrency(currency);
		return list;
	}

	/**
	 * 根据币种代码查询当前汇率
	 *
	 */
	@Override
	public Currency getByCurrencyCode(String currencyCode) {
		return currencyMapper.getByCurrencyCode(currencyCode);
	}


	/**
	 * 查询汇率历史
	 * 
	 */
	public RestResult findHistory(CurrencyLog param) {
		return currencyLogService.findList(param);
	}


	public List<Currency> findAll() {
		 return currencyMapper.findAll();
	}


}
